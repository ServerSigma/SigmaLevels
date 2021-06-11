package com.serversigma.sigmalevels.manager;

import com.serversigma.sigmalevels.model.Level;
import com.serversigma.sigmalevels.model.LevelType;
import com.serversigma.sigmalevels.utilitie.ItemComposer;
import de.tr7zw.nbtapi.NBTItem;
import lombok.RequiredArgsConstructor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
public class ItemManager {

    public void giveItem(Player player, double defaultPrice, LevelType type) {

        if (type.equals(LevelType.TOOLS_PICKAXE)) {
            ItemStack itemStack = new ItemComposer(Material.DIAMOND_PICKAXE)
                    .setName("§bSuper Picareta")
                    .addEnchantment(Enchantment.DIG_SPEED, 5)
                    .addEnchantment(Enchantment.LOOT_BONUS_BLOCKS, 5)
                    .addEnchantment(Enchantment.DURABILITY, 5)
                    .addEnchantment(Enchantment.ARROW_INFINITE, 1)
                    .addItemFlag(ItemFlag.HIDE_ENCHANTS)
                    .addItemFlag(ItemFlag.HIDE_ATTRIBUTES)
                    .composeMeta(itemMeta -> itemMeta.setLore(getLore(itemMeta, type)))
                    .setNBT("minedBlocks", defaultPrice)
                    .build();
            player.getInventory().addItem(itemStack);
        }

        if (type.equals(LevelType.ARMOR_ALL)) {

            List<LevelType> armorTypes = Arrays.asList(
                    LevelType.ARMOR_BOOTS, LevelType.ARMOR_HELMET,
                    LevelType.ARMOR_LEGGINGS, LevelType.ARMOR_CHESTPLATE);

            armorTypes.forEach(armorType -> {
                ItemStack itemStack = new ItemComposer(armorType.getType())
                        .setName("§bSuper Armadura")
                        .addItemFlag(ItemFlag.HIDE_ENCHANTS)
                        .addItemFlag(ItemFlag.HIDE_ATTRIBUTES)
                        .addEnchantment(Enchantment.ARROW_INFINITE, 1)
                        .composeMeta(itemMeta -> itemMeta.setLore(getLore(itemMeta, type)))
                        .setNBT("spentGems", defaultPrice)
                        .build();
                player.getInventory().addItem(itemStack);
            });
        }

        if (type.equals(LevelType.TOOLS_SWORD)) {
            ItemStack itemStack = new ItemComposer(Material.DIAMOND_SWORD)
                    .setName("§bSuper Espada")
                    .addItemFlag(ItemFlag.HIDE_ENCHANTS)
                    .addItemFlag(ItemFlag.HIDE_ATTRIBUTES)
                    .addEnchantment(Enchantment.ARROW_INFINITE, 1)
                    .composeMeta(itemMeta -> itemMeta.setLore(getLore(itemMeta, type)))
                    .setNBT("killedEntities", defaultPrice)
                    .build();
            player.getInventory().addItem(itemStack);
        }

    }

    public ItemMeta upgradeItem(ItemStack itemStack, Level level) {

        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);

        if (level.getLevelType().equals(LevelType.TOOLS_PICKAXE)) {
            itemMeta.addEnchant(Enchantment.DIG_SPEED, level.getEfficiency(), true);
            itemMeta.addEnchant(Enchantment.LOOT_BONUS_BLOCKS, level.getFortune(), true);
            itemMeta.addEnchant(Enchantment.DURABILITY, level.getUnbreaking(), true);
            itemMeta.setLore(getLore(itemMeta, level.getLevelType()));
        }

        if (level.getLevelType().equals(LevelType.TOOLS_SWORD)) {
            itemMeta.addEnchant(Enchantment.DAMAGE_ALL, level.getSharpness(), true);
            itemMeta.addEnchant(Enchantment.LOOT_BONUS_MOBS, level.getLooting(), true);
            itemMeta.addEnchant(Enchantment.FIRE_ASPECT, level.getFireAspect(), true);
            itemMeta.addEnchant(Enchantment.DURABILITY, level.getUnbreaking(), true);
            itemMeta.setLore(getLore(itemMeta, level.getLevelType()));
        }

        if (level.getLevelType().name().startsWith("ARMOR")) {
            itemMeta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, level.getProtection(), true);
            itemMeta.addEnchant(Enchantment.DURABILITY, level.getUnbreaking(), true);
            itemMeta.setLore(getLore(itemMeta, level.getLevelType()));
        }

        return itemMeta;
    }

    public List<String> getLore(ItemMeta itemMeta, LevelType type) {
        if (type.equals(LevelType.ARMOR_ALL)) {
            return Arrays.asList(
                    "§7",
                    "§7Proteção: " + itemMeta.getEnchantLevel(Enchantment.PROTECTION_ENVIRONMENTAL),
                    "§7Inquebrável: " + itemMeta.getEnchantLevel(Enchantment.DURABILITY),
                    "§7",
                    "§7Você pode evoluir esse item utilizando o §daltar§7.");
        }
        if (type.equals(LevelType.TOOLS_SWORD)) {
            return Arrays.asList(
                    "§7",
                    "§7Afiação: " + itemMeta.getEnchantLevel(Enchantment.DAMAGE_ALL),
                    "§7Pilhagem: " + itemMeta.getEnchantLevel(Enchantment.LOOT_BONUS_MOBS),
                    "§7Inquebrável: " + itemMeta.getEnchantLevel(Enchantment.DURABILITY),
                    "§7Aspecto Flamejante: " + itemMeta.getEnchantLevel(Enchantment.FIRE_ASPECT),
                    "§7",
                    "§7Você pode evoluir esse item utilizando o §daltar§7.");
        }

        if (type.equals(LevelType.TOOLS_PICKAXE)) {
            return Arrays.asList(
                    "§7",
                    "§7Eficiência: " + itemMeta.getEnchantLevel(Enchantment.DIG_SPEED),
                    "§7Fortuna: " + itemMeta.getEnchantLevel(Enchantment.LOOT_BONUS_BLOCKS),
                    "§7Inquebrável: " + itemMeta.getEnchantLevel(Enchantment.DURABILITY),
                    "§7",
                    "§7Você pode evoluir esse item utilizando o §daltar§7.");
        }
        return null;
    }

}