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
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import java.util.*;

@SuppressWarnings("unused")
public class SwordInventory extends PagedInventory {

    private final Player player;
    private final ItemManager itemManager;
    private final LevelManager levelManager;

    public SwordInventory(Player player, ItemManager itemManager, LevelManager levelManager) {
        super(
                "sigmalevels.sword",
                "§7Menu de espadas",
                9 * 4
        );
        this.player = player;
        this.itemManager = itemManager;
        this.levelManager = levelManager;
    }

    @Override
    protected List<InventoryItemSupplier> createPageItems(PagedViewer viewer) {

        int slots = 0;
        List<InventoryItemSupplier> itemSuppliers = new LinkedList<>();
        Collection<Level> swordLevels = new ArrayList<>();

        levelManager.getLevels()
                .stream()
                .filter(level -> level.getLevelType().equals(LevelType.TOOLS_SWORD))
                .sorted(Comparator.comparingDouble(Level::getPrice))
                .forEach(swordLevels::add);

        for (Level level : swordLevels) {

            ItemStack itemInHand = player.getItemInHand();
            NBTItem nbtItem = new NBTItem(itemInHand);

            double entities = nbtItem.getDouble("killedEntities");
            double remaningEntities = level.getPrice() - entities;

            String lore = (entities >= level.getPrice())
                    && itemInHand.getEnchantmentLevel(Enchantment.DAMAGE_ALL) < level.getSharpness()
                    ? "§aClique aqui para evoluir sua espada."
                    : "§cFaltam §7" + NumberFormat.format(remaningEntities) + " §centidades para evoluir sua espada.";

            if (itemInHand.getEnchantmentLevel(Enchantment.DAMAGE_ALL) >= level.getSharpness()) {
                lore = "§bVocê já evoluiu sua espada para esse nível.";
            }

            if (!level.getPermission().isEmpty() && !player.hasPermission(level.getPermission())) {
                lore = "§cVocê não tem permissão para evoluir até esse nível.";
            }

            String finalLore = lore;

            ItemStack itemStack = new ItemComposer(level.getLevelType().getType())
                    .setName("§e" + level.getName())
                    .addEnchantment(Enchantment.DAMAGE_ALL, level.getSharpness())
                    .addEnchantment(Enchantment.LOOT_BONUS_MOBS, level.getLooting())
                    .addEnchantment(Enchantment.FIRE_ASPECT, level.getFireAspect())
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

                        if (entities < level.getPrice()) {
                            player.sendMessage("§cFaltam §7" + NumberFormat.format(remaningEntities)
                                    + " §centidades para evoluir sua espada.");
                            return;
                        }

                        if (itemInHand.getEnchantmentLevel(Enchantment.DAMAGE_ALL) >= level.getSharpness()) {
                            player.playSound(player.getLocation(), Sound.NOTE_BASS, 1, 1);
                            player.sendMessage("§cOps, sua espada já tem um encantamento superior.");
                            return;
                        }

                        Level swordLevel = levelManager.getNextLevel(level);

                        if (swordLevel == null) {
                            player.sendMessage("§cOcorreu um erro ao evoluir sua ferramenta.");
                            return;
                        }

                        player.getItemInHand().setItemMeta(itemManager.upgradeItem(itemInHand, swordLevel));

                        //noinspection deprecation
                        player.sendTitle("§a§lLEVEL UP!", level.getName());
                        player.sendMessage("§aVocê evoluiu sua espada para " + level.getName());
                        player.playSound(player.getLocation(), Sound.LEVEL_UP, 1, 1);
                        player.closeInventory();
                    })
            );
            slots++;
            if (slots % 7 == 0) {
                itemSuppliers.add(() -> InventoryItem.of(new ItemStack(Material.AIR)));
                itemSuppliers.add(() -> InventoryItem.of(new ItemStack(Material.AIR)));
            }
        }
        return itemSuppliers;
    }

}