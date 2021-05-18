package com.serversigma.inventory;

import com.henryfabio.minecraft.inventoryapi.inventory.impl.paged.PagedInventory;
import com.henryfabio.minecraft.inventoryapi.item.InventoryItem;
import com.henryfabio.minecraft.inventoryapi.item.supplier.InventoryItemSupplier;
import com.henryfabio.minecraft.inventoryapi.viewer.impl.paged.PagedViewer;
import com.serversigma.manager.ItemManager;
import com.serversigma.manager.LevelManager;
import com.serversigma.model.PickaxeLevel;
import com.serversigma.utilitie.ItemComposer;
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

public class PickaxeInventory extends PagedInventory {

    private final LevelManager levelManager;
    private final Player player;
    private final ItemManager itemManager;

    public PickaxeInventory(LevelManager levelManager, Player player, ItemManager itemManager) {
        super(
                "sigmaevolutions.sword",
                "§aMenu de Espadas",
                27
        );
        this.levelManager = levelManager;
        this.player = player;
        this.itemManager = itemManager;
    }

    @Override
    protected List<InventoryItemSupplier> createPageItems(PagedViewer viewer) {

        List<InventoryItemSupplier> itemSuppliers = new LinkedList<>();
        Collection<PickaxeLevel> pickaxeLevelCollection = levelManager.getPickaxeLevels();

        ItemStack emptySlot = new ItemStack(Material.AIR);
        itemSuppliers.add(() -> InventoryItem.of(emptySlot));

        for (PickaxeLevel level : pickaxeLevelCollection) {

            ItemStack itemInHand = player.getItemInHand();
            NBTItem nbtItem = new NBTItem(itemInHand);

            String lore = (nbtItem.getInteger("blocksBreaked") >= level.getBlocks()
                    && itemInHand.getEnchantmentLevel(Enchantment.DIG_SPEED) < level.getEfficiencyLevel()
                    ? "§7Clique §a§lAQUI §7para evoluir sua picareta."
                    : "§cFaltam " + (level.getBlocks() - nbtItem.getInteger("blocksBreaked")) + " §cblocos"
            );
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
                    .setNBT("blocksBreaked", level.getBlocks())
                    .build();

            itemSuppliers.add(() -> InventoryItem.of(itemStack).callback(
                    ClickType.LEFT,
                    click -> {
                        if (nbtItem.getInteger("blocksBreaked") < level.getBlocks()) {
//
                            player.sendMessage("§cAinda falta " + 1);
                            player.playSound(player.getLocation(), Sound.NOTE_BASS, 1, 1);
                            return;
                        }

                        if (itemInHand.getEnchantmentLevel(Enchantment.DIG_SPEED) >= level.getEfficiencyLevel()) {
                            player.playSound(player.getLocation(), Sound.NOTE_BASS, 1, 1);
                            player.sendMessage("§cVocê já evoluiu sua picareta para esse nível.");
                            return;
                        }

                        NBTItem nbtItem1 = new NBTItem(click.getItemStack());
                        PickaxeLevel pickaxeLevel = levelManager.getPickaxeLevelByBlocks(nbtItem1.getInteger("blocksBreaked"));

                        System.out.println(pickaxeLevel);
                        itemManager.upgradePickaxe(itemInHand, pickaxeLevel);
                        updateInventory(player);
                        player.sendMessage("§aVocê evoluiu sua picareta para " + pickaxeLevel);
                        player.playSound(player.getLocation(), Sound.LEVEL_UP, 1, 1);
                        player.sendTitle("§a§lLEVEL UP!", "");
                    })
            );
        }
        return itemSuppliers;
    }
}