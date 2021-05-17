package com.serversigma.inventory;

import com.henryfabio.minecraft.inventoryapi.inventory.impl.paged.PagedInventory;
import com.henryfabio.minecraft.inventoryapi.item.InventoryItem;
import com.henryfabio.minecraft.inventoryapi.item.supplier.InventoryItemSupplier;
import com.henryfabio.minecraft.inventoryapi.viewer.impl.paged.PagedViewer;
import com.serversigma.manager.LevelManager;
import com.serversigma.model.PickaxeLevel;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class SwordInventory extends PagedInventory {

    private final LevelManager levelManager;

    public SwordInventory(LevelManager levelManager) {
        super(
                "sigmaevolutions.sword",
                "§aMenu de Espadas",
                27
        );
        this.levelManager = levelManager;
    }

    @Override
    protected List<InventoryItemSupplier> createPageItems(PagedViewer viewer) {

        List<InventoryItemSupplier> itemSuppliers = new LinkedList<>();
        Collection<PickaxeLevel> swordLevelCollection = levelManager.getPickaxeLevels();

        ItemStack emptySlot = new ItemStack(Material.AIR);
        itemSuppliers.add(() -> InventoryItem.of(emptySlot));

        return itemSuppliers;
    }
}