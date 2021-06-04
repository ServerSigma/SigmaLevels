package com.serversigma.sigmalevels.inventory;

import com.henryfabio.minecraft.inventoryapi.inventory.impl.paged.PagedInventory;
import com.henryfabio.minecraft.inventoryapi.item.InventoryItem;
import com.henryfabio.minecraft.inventoryapi.item.supplier.InventoryItemSupplier;
import com.henryfabio.minecraft.inventoryapi.viewer.impl.paged.PagedViewer;
import com.serversigma.sigmalevels.manager.ItemManager;
import com.serversigma.sigmalevels.manager.LevelManager;
import com.serversigma.sigmalevels.model.Level;
import com.serversigma.sigmalevels.model.LevelType;
import com.serversigma.sigmalevels.utilitie.ItemComposer;
import com.serversigma.sigmalevels.utilitie.NumberFormat;
import de.tr7zw.nbtapi.NBTItem;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import java.util.*;

@SuppressWarnings("unused")
public class PickaxeInventory extends PagedInventory {

    private final Player player;
    private final ItemManager itemManager;
    private final LevelManager levelManager;

    public PickaxeInventory(Player player, ItemManager itemManager, LevelManager levelManager) {
        super(
                "sigmalevels.pickaxe",
                "§7Menu de picaretas",
                9 * 3
        );
        this.player = player;
        this.itemManager = itemManager;
        this.levelManager = levelManager;
    }

    @Override
    protected List<InventoryItemSupplier> createPageItems(PagedViewer viewer) {

        List<InventoryItemSupplier> itemSuppliers = new LinkedList<>();
        Collection<Level> pickaxeLevels = new ArrayList<>();

        levelManager.getLevels()
                .stream()
                .filter(level -> level.getLevelType().equals(LevelType.TOOLS_PICKAXE))
                .sorted(Comparator.comparingDouble(Level::getPrice))
                .forEach(pickaxeLevels::add);

        for (Level level : pickaxeLevels) {

            ItemStack itemInHand = player.getItemInHand();
            NBTItem nbtItem = new NBTItem(itemInHand);

            double blocks = nbtItem.getDouble("minedBlocks");
            double remaningBlocks = level.getPrice() - blocks;

            String lore = (blocks >= level.getPrice())
                    && itemInHand.getEnchantmentLevel(Enchantment.DIG_SPEED) < level.getEfficiency()
                    ? "§aClique aqui para evoluir sua picareta."
                    : "§cFaltam §7" + NumberFormat.format(remaningBlocks) + " §cblocos para evoluir sua picareta.";

            if (itemInHand.getEnchantmentLevel(Enchantment.DIG_SPEED) >= level.getEfficiency()) {
                lore = "§bVocê já evoluiu sua picareta para esse nível.";
            }

            if (!level.getPermission().isEmpty() && !player.hasPermission(level.getPermission())) {
                lore = "§cVocê não tem permissão para evoluir até esse nível.";
            }

            String finalLore = lore;

            ItemStack itemStack = new ItemComposer(level.getLevelType().getType())
                    .setName("§e" + level.getName())
                    .addEnchantment(Enchantment.DIG_SPEED, level.getEfficiency())
                    .addEnchantment(Enchantment.LOOT_BONUS_BLOCKS, level.getFortune())
                    .addEnchantment(Enchantment.DURABILITY, level.getUnbreaking())
                    .addItemFlag(ItemFlag.HIDE_ATTRIBUTES)
                    .addItemFlag(ItemFlag.HIDE_ENCHANTS)
                    .composeMeta(itemMeta -> {
                        List<String> itemLore = itemManager.getLore(itemMeta, level.getLevelType());
                        itemLore.set(itemLore.size() - 1, finalLore);
                        itemMeta.setLore(itemLore);
                    })
                    .toItemStack();

            itemSuppliers.add(() -> InventoryItem.of(itemStack).callback(
                    ClickType.LEFT,
                    click -> {

                        if (!level.getPermission().isEmpty() && !player.hasPermission(level.getPermission())) {
                            player.sendMessage("§cVocê não tem permissão para evoluir até esse nível.");
                            return;
                        }

                        if (blocks < level.getPrice()) {
                            player.sendMessage("§cFaltam §7" + NumberFormat.format(remaningBlocks)
                                    + " §cblocos para evoluir sua picareta.");
                            return;
                        }

                        if (itemInHand.getEnchantmentLevel(Enchantment.DIG_SPEED) >= level.getEfficiency()) {
                            player.playSound(player.getLocation(), Sound.NOTE_BASS, 1, 1);
                            player.sendMessage("§cOps, sua picareta já tem um encantamento superior.");
                            return;
                        }

                        Level pickaxeLevel = levelManager.getNextLevel(level);

                        if (pickaxeLevel == null) {
                            player.sendMessage("§cOcorreu um erro ao evoluir sua ferramenta.");
                            return;
                        }

                        player.getItemInHand().setItemMeta(itemManager.upgradeItem(itemInHand, pickaxeLevel));

                        //noinspection deprecation
                        player.sendTitle("§a§lLEVEL UP!", level.getName());
                        player.sendMessage("§aVocê evoluiu sua picareta para " + level.getName());
                        player.playSound(player.getLocation(), Sound.LEVEL_UP, 1, 1);
                        player.closeInventory();
                    })
            );
        }
        return itemSuppliers;
    }

}