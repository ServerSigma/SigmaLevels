package com.serversigma.inventory;

import com.henryfabio.minecraft.inventoryapi.inventory.impl.paged.PagedInventory;
import com.henryfabio.minecraft.inventoryapi.item.InventoryItem;
import com.henryfabio.minecraft.inventoryapi.item.supplier.InventoryItemSupplier;
import com.henryfabio.minecraft.inventoryapi.viewer.impl.paged.PagedViewer;
import com.serversigma.manager.ItemManager;
import com.serversigma.manager.LevelManager;
import com.serversigma.model.SwordLevel;
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

public class SwordInventory extends PagedInventory {

    private final LevelManager levelManager;
    private final Player player;
    private final ItemManager itemManager;

    public SwordInventory(Player player, LevelManager levelManager, ItemManager itemManager) {
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
        Collection<SwordLevel> swordLevelCollection = levelManager.getSwordLevels();

        ItemStack emptySlot = new ItemStack(Material.AIR);
        itemSuppliers.add(() -> InventoryItem.of(emptySlot));

        for (SwordLevel level : swordLevelCollection) {

            ItemStack itemInHand = player.getItemInHand();
            NBTItem nbtItem = new NBTItem(itemInHand);

            String lore = (nbtItem.getInteger("entityKilled") >= level.getEntitys()
                    && itemInHand.getEnchantmentLevel(Enchantment.DAMAGE_ALL) < level.getSharpnessLevel()
                    ? "§7Clique §a§lAQUI §7para evoluir sua espada."
                    : "§cVocê não pode evoluir sua espada."
            );
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
                    .setNBT("entityKilled", level.getEntitys())
                    .build();
            itemSuppliers.add(() -> InventoryItem.of(itemStack).callback(
                    ClickType.UNKNOWN,
                    click -> {

                        if (nbtItem.getInteger("entityKilled") < level.getEntitys()) {
                            player.sendMessage("§copa");
                            return;
                        }

                        if (itemInHand.getEnchantmentLevel(Enchantment.DAMAGE_ALL) >= level.getSharpnessLevel()) {
                            player.sendMessage("§cVocê não pode evoluir sua espada, pois já está evoluida.");
                            return;
                        }

                        NBTItem nbtItem1 = new NBTItem(click.getItemStack());
                        SwordLevel swordLevel = levelManager.getSwordLevelByBlocks(nbtItem1.getInteger("entityKilled"));

                        itemManager.upgradeSword(itemInHand, swordLevel);
                        updateInventory(player);
                        player.sendMessage("§aVocê evoluiu sua espada para " + swordLevel);
                        player.playSound(player.getLocation(), Sound.LEVEL_UP, 1, 1);
                        player.sendTitle("§a§lLEVEL UP!", "");
                    })
            );
        }
        return itemSuppliers;
    }
}