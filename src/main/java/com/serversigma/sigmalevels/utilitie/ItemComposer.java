package com.serversigma.sigmalevels.utilitie;

import de.tr7zw.nbtapi.NBTItem;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.function.Consumer;

public class ItemComposer {

    private final ItemStack item;
    private NBTItem nbtItem;

    public ItemComposer(Material material) {
        this.item = new ItemStack(material);
    }

    public ItemComposer composeMeta(Consumer<ItemMeta> consumer) {
        ItemMeta meta = item.getItemMeta();
        consumer.accept(meta);
        item.setItemMeta(meta);
        return this;
    }

    public ItemComposer addItemFlag(ItemFlag... itemflag) {
        composeMeta(meta -> meta.addItemFlags(itemflag));
        return this;
    }


    public ItemComposer setName(String name) {
        if (name == null || name.equalsIgnoreCase("")) return this;
        composeMeta(meta -> meta.setDisplayName(name));
        return this;
    }

    public ItemComposer setNBT(String key, double value) {
        this.nbtItem = new NBTItem(item);
        nbtItem.setDouble(key, value);
        nbtItem.applyNBT(item);
        return this;
    }

    public void compose(Consumer<ItemStack> consumer) {
        consumer.accept(item);
    }

    public ItemComposer addEnchantment(Enchantment enchantment, int level) {
        compose(it -> it.addUnsafeEnchantment(enchantment, level));
        return this;
    }

    public ItemStack toItemStack() {
        return item;
    }

    public ItemStack build() {
        return nbtItem.getItem();
    }

}