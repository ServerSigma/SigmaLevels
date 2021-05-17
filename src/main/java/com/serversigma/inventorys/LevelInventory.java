package com.serversigma.inventorys;

import com.henryfabio.minecraft.inventoryapi.inventory.impl.paged.PagedInventory;
import com.henryfabio.minecraft.inventoryapi.item.InventoryItem;
import com.henryfabio.minecraft.inventoryapi.item.supplier.InventoryItemSupplier;
import com.henryfabio.minecraft.inventoryapi.viewer.impl.paged.PagedViewer;
import com.serversigma.managers.LevelManager;
import com.serversigma.models.PickaxeLevel;
import com.serversigma.utilities.ItemComposer;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class LevelInventory extends PagedInventory {

    private final LevelManager levelManager;

    public LevelInventory(LevelManager levelManager) {
        super(
                "sigmapickaxes.inventory",
                "§aMenu de evolução",
                27
        );
        this.levelManager = levelManager;
    }

    @Override
    protected List<InventoryItemSupplier> createPageItems(PagedViewer viewer) {

        List<InventoryItemSupplier> itemSuppliers = new LinkedList<>();
        Collection<PickaxeLevel> pickaxeLevelCollection = levelManager.getPickaxeLevels();

        ItemStack emptySlot = new ItemStack(Material.AIR);
        itemSuppliers.add(() -> InventoryItem.of(emptySlot));

        for (PickaxeLevel pickaxeLevel : pickaxeLevelCollection) {
//            List<String> itemLore = configManager.getGuiLores();
//
//            itemLore.replaceAll(s -> s.replace("%old-eff", pickaxeLevel.getEfficiencyLevel() - 1 + ""));
//            itemLore.replaceAll(s -> s.replace("%old-fort", pickaxeLevel.getFortuneLevel() - 1 + ""));
//            itemLore.replaceAll(s -> s.replace("%old-unb", pickaxeLevel.getUnbreakingLevel() - 1 + ""));
//            itemLore.replaceAll(s -> s.replace("%new-eff", pickaxeLevel.getEfficiencyLevel() + ""));
//            itemLore.replaceAll(s -> s.replace("%new-fort", pickaxeLevel.getFortuneLevel() + ""));
//            itemLore.replaceAll(s -> s.replace("%new-unb", pickaxeLevel.getUnbreakingLevel() + ""));
//            itemLore.replaceAll(s -> s.replace("%blocks", pickaxeLevel.getBlocks() + ""));

            ItemStack itemStack = new ItemComposer(Material.DIAMOND_PICKAXE)
                    .setName(pickaxeLevel.getName())
//                    .setLore(itemLore)
                    .build();
            itemSuppliers.add(() -> InventoryItem.of(itemStack));
        }
        return itemSuppliers;
    }
}
