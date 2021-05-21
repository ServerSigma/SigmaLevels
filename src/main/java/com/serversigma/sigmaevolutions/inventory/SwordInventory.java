package com.serversigma.sigmaevolutions.inventory;

import com.henryfabio.minecraft.inventoryapi.inventory.impl.paged.PagedInventory;
import com.henryfabio.minecraft.inventoryapi.item.InventoryItem;
import com.henryfabio.minecraft.inventoryapi.item.supplier.InventoryItemSupplier;
import com.henryfabio.minecraft.inventoryapi.viewer.impl.paged.PagedViewer;
import com.serversigma.sigmaevolutions.manager.ItemManager;
import com.serversigma.sigmaevolutions.manager.LevelManager;
import com.serversigma.sigmaevolutions.model.SwordLevel;
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

public class SwordInventory extends PagedInventory {

    private final LevelManager levelManager;
    private final Player player;
    private final ItemManager itemManager;

    public SwordInventory(LevelManager levelManager, Player player, ItemManager itemManager) {
        super(
                "sigmaevolutions.sword",
                "§7Menu de espadas",
                27
        );
        this.levelManager = levelManager;
        this.player = player;
        this.itemManager = itemManager;
    }

    @Override
    protected List<InventoryItemSupplier> createPageItems(PagedViewer viewer) {

        List<InventoryItemSupplier> itemSuppliers = new LinkedList<>();
        Collection<SwordLevel> swordLevels = levelManager.getSwordLevels();

        ItemStack emptySlot = new ItemStack(Material.AIR);
        itemSuppliers.add(() -> InventoryItem.of(emptySlot));

        for (SwordLevel level : swordLevels) {

            ItemStack itemInHand = player.getItemInHand();
            NBTItem nbtItem = new NBTItem(itemInHand);

            int entity = nbtItem.getInteger("entityKilled");
            int remaingEntitys = (level.getEntitys() - nbtItem.getInteger("entityKilled"));

            String lore = (entity >= level.getEntitys()
                    && itemInHand.getEnchantmentLevel(Enchantment.DAMAGE_ALL) < level.getSharpnessLevel()
                    ? "§aClique aqui para evoluir sua espada."
                    : "§cFaltam §7" + remaingEntitys + " §centidades para evoluir sua espada.");

            if (itemInHand.getEnchantmentLevel(Enchantment.DAMAGE_ALL) >= level.getSharpnessLevel()) {
                lore = "§bVocê já evoluiu sua espada para esse nível.";
            }

            ItemStack itemStack = new ItemComposer(Material.DIAMOND_SWORD)
                    .setName(level.getName())
                    .setLore(
                            "§r",
                            "§7Afiação: §b" + level.getSharpnessLevel(),
                            "§7Pilhagem: §b" + level.getLootingLevel(),
                            "§7Inquebrável: §b" + level.getUnbreakingLevel(),
                            "",
                            lore
                    )
                    .toItemStack();

            itemSuppliers.add(() -> InventoryItem.of(itemStack).callback(
                    ClickType.LEFT,
                    click -> {
                        if (remaingEntitys > 0) {
                            player.sendMessage("§cFalta §7" + remaingEntitys + " §cmonstros para evoluir até esse nível.");
                            player.playSound(player.getLocation(), Sound.NOTE_BASS, 1, 1);
                            return;
                        }

                        if (itemInHand.getEnchantmentLevel(Enchantment.DAMAGE_ALL) >= level.getSharpnessLevel()) {
                            player.playSound(player.getLocation(), Sound.NOTE_BASS, 1, 1);
                            player.sendMessage("§cOps, sua espada já tem um encantamento superior.");
                            return;
                        }

                        SwordLevel swordLevel = levelManager.getSwordNextLevel(level.getEntitys());
                        itemInHand.setItemMeta(itemManager.upgradeSword(itemInHand, swordLevel));

                        player.closeInventory();
                        player.sendMessage("§aVocê evoluiu sua espada para " + level.getName());
                        player.playSound(player.getLocation(), Sound.LEVEL_UP, 1, 1);
                        player.sendTitle("§a§lLEVEL UP!", level.getName());
                    })
            );
        }
        return itemSuppliers;
    }
}