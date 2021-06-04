package com.serversigma.sigmalevels.inventory;

import com.henryfabio.minecraft.inventoryapi.inventory.impl.paged.PagedInventory;
import com.henryfabio.minecraft.inventoryapi.item.InventoryItem;
import com.henryfabio.minecraft.inventoryapi.item.supplier.InventoryItemSupplier;
import com.henryfabio.minecraft.inventoryapi.viewer.impl.paged.PagedViewer;
import com.serversigma.sigmagems.api.SigmaGemsAPI;
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
public class ArmorInventory extends PagedInventory {

    private final Player player;
    private final ItemManager itemManager;
    private final LevelManager levelManager;

    public ArmorInventory(Player player, ItemManager itemManager, LevelManager levelManager) {
        super(
                "sigmalevels.armor",
                "§7Menu de armaduras",
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
        Collection<Level> armorLevels = new ArrayList<>();

        levelManager.getLevels()
                .stream()
                .filter(level -> level.getLevelType().equals(LevelType.ARMOR_ALL))
                .sorted(Comparator.comparingDouble(Level::getPrice))
                .forEach(armorLevels::add);

        for (Level level : armorLevels) {

            ItemStack itemInHand = player.getItemInHand();
            NBTItem nbtItem = new NBTItem(itemInHand);

            double gems = SigmaGemsAPI.getGems(player);
            double remaningGems = level.getPrice() - gems;

            String lore = (gems >= level.getPrice())
                    && itemInHand.getEnchantmentLevel(Enchantment.PROTECTION_ENVIRONMENTAL) < level.getProtection()
                    ? "§aClique aqui para evoluir sua armadura."
                    : "§cFaltam §7" + NumberFormat.format(remaningGems) + " §cgemas para evoluir sua armadura.";

            if (itemInHand.getEnchantmentLevel(Enchantment.PROTECTION_ENVIRONMENTAL) >= level.getProtection()) {
                lore = "§bVocê já evoluiu sua armadura para esse nível.";
            }

            if (!level.getPermission().isEmpty() && !player.hasPermission(level.getPermission())) {
                lore = "§cVocê não tem permissão para evoluir até esse nível.";
            }

            String finalLore = lore;

            ItemStack itemStack = new ItemComposer(itemInHand.getType())
                    .setName("§e" + level.getName())
                    .addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, level.getProtection())
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

                        if (gems < level.getPrice()) {
                            player.sendMessage("§cFaltam §7" + NumberFormat.format(remaningGems)
                                    + " §cgemas para evoluir sua armadura.");
                            return;
                        }

                        if (itemInHand.getEnchantmentLevel(Enchantment.PROTECTION_ENVIRONMENTAL)
                                >= level.getProtection()) {

                            player.playSound(player.getLocation(), Sound.NOTE_BASS, 1, 1);
                            player.sendMessage("§cOps, sua armadura já tem um encantamento superior.");
                            return;
                        }

                        Level armorLevel = levelManager.getNextLevel(level);

                        if (armorLevel == null) {
                            player.sendMessage("§cOcorreu um erro ao evoluir sua armadura.");
                            return;
                        }

                        player.getItemInHand().setItemMeta(itemManager.upgradeItem(itemInHand, armorLevel));

                        SigmaGemsAPI.removeGems(player, (int) Math.round(level.getPrice()));

                        //noinspection deprecation
                        player.sendTitle("§a§lLEVEL UP!", level.getName());
                        player.sendMessage("§aVocê evoluiu sua armadura para " + level.getName());
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