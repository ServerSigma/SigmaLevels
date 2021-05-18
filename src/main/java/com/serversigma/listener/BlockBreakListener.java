package com.serversigma.listener;

import com.serversigma.manager.ItemManager;
import com.serversigma.manager.LevelManager;
import de.tr7zw.nbtapi.NBTItem;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

@RequiredArgsConstructor
public class BlockBreakListener implements Listener {

    private final ItemManager itemManager;

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBreak(BlockBreakEvent e) {

        Player p = e.getPlayer();

        if (!(p.getWorld().getName().equals("Mundo"))) return;

        ItemStack itemStack = p.getItemInHand();

        if (!(itemStack.getType().name().equals("DIAMOND_PICKAXE"))) return;
        if (!itemStack.hasItemMeta()) return;
        if (!itemStack.getItemMeta().hasDisplayName()) return;

        NBTItem nbtItem = new NBTItem(itemStack);
        if (!nbtItem.hasNBTData()) return;

        int blocks = nbtItem.getInteger("blocksBreaked");
        nbtItem.setInteger("blocksBreaked", blocks + 1);
        nbtItem.applyNBT(itemStack);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName("§eSuper Picareta §8» §7" + (blocks + 1));
        itemStack.setItemMeta(itemMeta);

    }
}