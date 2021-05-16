package com.serversigma.manager;

import com.serversigma.utilitie.ItemComposer;
import lombok.RequiredArgsConstructor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.util.Arrays;

@RequiredArgsConstructor
public class ItemManager {

    private final Plugin plugin;

    public void giveSword(Player player) {

        ItemStack itemStack = new ItemComposer(Material.DIAMOND_SWORD)
                .setName("§eSuper Espada")
                .setNBT("entityKilled", 0)
                .addEnchantment(Enchantment.DAMAGE_ALL, 5)
                .addEnchantment(Enchantment.LOOT_BONUS_MOBS, 3)
                .addEnchantment(Enchantment.DURABILITY, 3)
                .composeMeta(itemMeta -> itemMeta.setLore(Arrays.asList(
                        "",
                        "§7Afiação: §b" + itemMeta.getEnchantLevel(Enchantment.DAMAGE_ALL),
                        "§7Pilhagem: §b" + itemMeta.getEnchantLevel(Enchantment.LOOT_BONUS_MOBS),
                        "§7Inquebrável: §b" + itemMeta.getEnchantLevel(Enchantment.DURABILITY),
                        ""
                )))
                .build();
        player.getInventory().addItem(itemStack);

    }

    public void givePickaxe(Player player) {

        ItemStack itemStack = new ItemComposer(Material.DIAMOND_PICKAXE)
                .setName("§eSuper Picareta")
                .setNBT("blocksBreaked", 0)
                .addEnchantment(Enchantment.DIG_SPEED, 5)
                .addEnchantment(Enchantment.LOOT_BONUS_BLOCKS, 3)
                .addEnchantment(Enchantment.DURABILITY, 3)
                .composeMeta(itemMeta -> itemMeta.setLore(Arrays.asList(
                        "",
                        "§7Eficiência: §b" + itemMeta.getEnchantLevel(Enchantment.DIG_SPEED),
                        "§7Fortuna: §b" + itemMeta.getEnchantLevel(Enchantment.LOOT_BONUS_BLOCKS),
                        "§7Inquebrável: §b" + itemMeta.getEnchantLevel(Enchantment.DURABILITY),
                        ""
                )))
                .build();
        player.getInventory().addItem(itemStack);
    }
}
