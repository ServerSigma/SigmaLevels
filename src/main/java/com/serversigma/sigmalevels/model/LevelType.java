package com.serversigma.sigmalevels.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

@AllArgsConstructor
public enum LevelType {

    TOOLS_SWORD(Material.DIAMOND_SWORD),
    TOOLS_PICKAXE(Material.DIAMOND_PICKAXE),

    ARMOR_ALL(null),
    ARMOR_BOOTS(Material.DIAMOND_BOOTS),
    ARMOR_HELMET(Material.DIAMOND_HELMET),
    ARMOR_LEGGINGS(Material.DIAMOND_LEGGINGS),
    ARMOR_CHESTPLATE(Material.DIAMOND_CHESTPLATE);

    @Getter
    private final Material type;

    public static LevelType getByItemStack(ItemStack item) {
        for (LevelType levelType: LevelType.values()) {
            if (levelType.getType() == item.getType()) return levelType;
        }
        return null;
    }

}
