package com.serversigma.inventory;

import com.henryfabio.minecraft.inventoryapi.inventory.impl.paged.PagedInventory;
import com.henryfabio.minecraft.inventoryapi.item.InventoryItem;
import com.henryfabio.minecraft.inventoryapi.item.supplier.InventoryItemSupplier;
import com.henryfabio.minecraft.inventoryapi.viewer.impl.paged.PagedViewer;
import com.serversigma.manager.ItemManager;
import com.serversigma.manager.LevelManager;
import com.serversigma.model.PickaxeLevel;
import com.serversigma.model.SwordLevel;
import com.serversigma.utilitie.ItemComposer;
import de.tr7zw.nbtapi.NBTItem;
import org.bukkit.Material;
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

            String lore = (player.getItemInHand().getEnchantmentLevel(Enchantment.DAMAGE_ALL) + 1 > level.getSharpnessLevel()
                    ? "§cVocê não pode evoluir sua espada."
                    : "§7Clique §a§lAQUI §7para evoluir sua espada.");

            ItemStack itemStack = new ItemComposer(Material.DIAMOND_SWORD)
                    .setName(level.getName())
                    .setNBT("entityKilled", level.getEntitys())
                    .setLore(
                            "§r",
                            lore
                    )
                    .build();
            itemSuppliers.add(() -> InventoryItem.of(itemStack).callback(
                    ClickType.LEFT,
                    click -> {
                        ItemStack itemInHand = player.getItemInHand();
                        NBTItem nbtItem = new NBTItem(itemInHand);
                        if(nbtItem.getInteger("entityKilled") < level.getEntitys()) {
                            player.sendMessage("§copa");
                        } else {
                            if(itemInHand.getEnchantmentLevel(Enchantment.DAMAGE_ALL) > level.getSharpnessLevel()) {
                                player.sendMessage("§cVocê não pode evoluir sua espada, pois já está evoluida.");
                            }
                            SwordLevel swordLevel = levelManager.getSwordLevelByBlocks(nbtItem.getInteger("entityKilled"));
                            itemManager.upgradeSword(itemStack, swordLevel);
                            player.sendMessage("upou");
                        }
                    }
            ));

        }

        return itemSuppliers;
    }
}