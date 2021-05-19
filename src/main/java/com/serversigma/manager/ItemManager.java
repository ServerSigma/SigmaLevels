package com.serversigma.manager;

import com.serversigma.model.PickaxeLevel;
import com.serversigma.model.SwordLevel;
import com.serversigma.utilitie.ItemComposer;
import lombok.RequiredArgsConstructor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

@RequiredArgsConstructor
public class ItemManager {

    public void giveSword(Player player) {
        ItemStack itemStack = new ItemComposer(Material.DIAMOND_SWORD)
                .setName("§eSuper Espada")
                .addEnchantment(Enchantment.DAMAGE_ALL, 5)
                .addEnchantment(Enchantment.LOOT_BONUS_MOBS, 3)
                .addEnchantment(Enchantment.DURABILITY, 3)
                .addItemFlag(ItemFlag.HIDE_ENCHANTS)
                .composeMeta(itemMeta -> itemMeta.setLore(Arrays.asList(
                        "",
                        "§7Afiação: §b" + itemMeta.getEnchantLevel(Enchantment.DAMAGE_ALL),
                        "§7Pilhagem: §b" + itemMeta.getEnchantLevel(Enchantment.LOOT_BONUS_MOBS),
                        "§7Inquebrável: §b" + itemMeta.getEnchantLevel(Enchantment.DURABILITY)
                )))
                .setNBT("entityKilled", 0)
                .build();
        player.getInventory().addItem(itemStack);
    }

    public void givePickaxe(Player player) {
        ItemStack itemStack = new ItemComposer(Material.DIAMOND_PICKAXE)
                .setName("§eSuper Picareta")
                .addEnchantment(Enchantment.DIG_SPEED, 5)
                .addEnchantment(Enchantment.LOOT_BONUS_BLOCKS, 3)
                .addEnchantment(Enchantment.DURABILITY, 3)
                .addItemFlag(ItemFlag.HIDE_ENCHANTS)
                .composeMeta(itemMeta -> itemMeta.setLore(Arrays.asList(
                        "",
                        "§7Eficiência: §b" + itemMeta.getEnchantLevel(Enchantment.DIG_SPEED),
                        "§7Fortuna: §b" + itemMeta.getEnchantLevel(Enchantment.LOOT_BONUS_BLOCKS),
                        "§7Inquebrável: §b" + itemMeta.getEnchantLevel(Enchantment.DURABILITY)
                )))
                .setNBT("blocksBreaked", 0)
                .build();
        player.getInventory().addItem(itemStack);
    }

    public ItemMeta upgradePickaxe(ItemStack itemStack, PickaxeLevel pickaxeLevel) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.addEnchant(Enchantment.DIG_SPEED, pickaxeLevel.getEfficiencyLevel(), true);
        itemMeta.addEnchant(Enchantment.LOOT_BONUS_BLOCKS, pickaxeLevel.getFortuneLevel(), true);
        itemMeta.addEnchant(Enchantment.DURABILITY, pickaxeLevel.getUnbreakingLevel(), true);
        itemMeta.setLore(Arrays.asList(
                "",
                "§7Eficiência: §b" + itemMeta.getEnchantLevel(Enchantment.DIG_SPEED),
                "§7Fortuna: §b" + itemMeta.getEnchantLevel(Enchantment.LOOT_BONUS_BLOCKS),
                "§7Inquebrável: §b" + itemMeta.getEnchantLevel(Enchantment.DURABILITY)
        ));
        itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
      return itemMeta;
    }

    public ItemMeta upgradeSword(ItemStack itemStack, SwordLevel swordLevel) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.addEnchant(Enchantment.DAMAGE_ALL, swordLevel.getSharpnessLevel(), true);
        itemMeta.addEnchant(Enchantment.LOOT_BONUS_MOBS, swordLevel.getLootingLevel(), true);
        itemMeta.addEnchant(Enchantment.DURABILITY, swordLevel.getUnbreakingLevel(), true);
        itemMeta.setLore(Arrays.asList(
                "",
                "§7Afiação: §b" + itemMeta.getEnchantLevel(Enchantment.DAMAGE_ALL),
                "§7Pilhagem: §b" + itemMeta.getEnchantLevel(Enchantment.LOOT_BONUS_MOBS),
                "§7Inquebrável: §b" + itemMeta.getEnchantLevel(Enchantment.DURABILITY)
        ));
        itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        return itemMeta;
    }
}