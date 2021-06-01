package com.serversigma.sigmaevolutions.inventory;

import com.henryfabio.minecraft.inventoryapi.inventory.impl.paged.PagedInventory;
import com.henryfabio.minecraft.inventoryapi.item.InventoryItem;
import com.henryfabio.minecraft.inventoryapi.item.supplier.InventoryItemSupplier;
import com.henryfabio.minecraft.inventoryapi.viewer.impl.paged.PagedViewer;
import com.serversigma.sigmaevolutions.manager.ItemManager;
import com.serversigma.sigmaevolutions.manager.LevelManager;
import com.serversigma.sigmaevolutions.model.ArmorLevel;
import com.serversigma.sigmaevolutions.model.PickaxeLevel;
import com.serversigma.sigmaevolutions.utilitie.ItemComposer;
import de.tr7zw.nbtapi.NBTItem;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class ArmorInventory extends PagedInventory {

    private final LevelManager levelManager;
    private final Player player;
    private final ItemManager itemManager;

    public ArmorInventory(LevelManager levelManager, Player player, ItemManager itemManager) {
        super(
                "sigmaevolutions.armor",
                "§7Menu de picaretas",
                27
        );
        this.levelManager = levelManager;
        this.player = player;
        this.itemManager = itemManager;
    }

    @Override
    protected List<InventoryItemSupplier> createPageItems(PagedViewer viewer) {

        List<InventoryItemSupplier> itemSuppliers = new LinkedList<>();
        Collection<ArmorLevel> armorLevels = levelManager.getArmorLevels();

        for (ArmorLevel armorLevel : armorLevels) {

            ItemStack itemInHand = player.getItemInHand();
            NBTItem nbtItem = new NBTItem(itemInHand);

            int blocks = nbtItem.getInteger("blocksBreaked");
            int remainingGems = level.getBlocks() - blocks;

            String lore = (blocks >= level.getBlocks()
                    && itemInHand.getEnchantmentLevel(Enchantment.DIG_SPEED) < level.getEfficiencyLevel()
                    ? "§aClique aqui para evoluir sua picareta."
                    : "§cFaltam §7" + remaningBlocks + " §cblocos para evoluir sua picareta.");

            if (itemInHand.getEnchantmentLevel(Enchantment.DIG_SPEED) >= level.getEfficiencyLevel()) {
                lore = "§bVocê já evoluiu sua picareta para esse nível.";
            }

            if (!level.getPermission().isEmpty() && !player.hasPermission(level.getPermission())) {
                lore = "§cVocê não tem permissão para evoluir até esse nível.";
            }

            ItemStack itemStack = new ItemComposer(Material.DIAMOND_PICKAXE)
                    .setName(level.getName())
                    .setLore(
                            "§r",
                            "§7Eficiência: §b" + level.getEfficiencyLevel(),
                            "§7Fortuna: §b" + level.getFortuneLevel(),
                            "§7Inquebrável: §b" + level.getUnbreakingLevel(),
                            "",
                            lore
                    )
                    .toItemStack();

            itemSuppliers.add(() -> InventoryItem.of(itemStack).callback(
                    ClickType.LEFT,
                    click -> {

                        if (!level.getPermission().isEmpty() && !player.hasPermission(level.getPermission())) {
                            player.sendMessage("§cVocê não tem permissão para evoluir até esse nível.");
                            return;
                        }

                        if (blocks < level.getBlocks()) {
                            player.sendMessage("§cFaltam §7" + remaningBlocks + " §cblocos para evoluir sua picareta.");
                            return;
                        }

                        if (itemInHand.getEnchantmentLevel(Enchantment.DIG_SPEED) >= level.getEfficiencyLevel()) {
                            player.playSound(player.getLocation(), Sound.NOTE_BASS, 1, 1);
                            player.sendMessage("§cOps, sua picareta já tem um encantamento superior.");
                            return;
                        }

                        PickaxeLevel pickaxeLevel = levelManager.getPickaxeNextLevel(level.getBlocks());
                        player.getItemInHand().setItemMeta(itemManager.upgradePickaxe(itemInHand, pickaxeLevel));

                        player.sendMessage("§aVocê evoluiu sua picareta para " + level.getName());
                        player.playSound(player.getLocation(), Sound.LEVEL_UP, 1, 1);
                        player.sendTitle("§a§lLEVEL UP!", level.getName());
                        player.closeInventory();
                    })
            );
        }
        return itemSuppliers;
    }
}