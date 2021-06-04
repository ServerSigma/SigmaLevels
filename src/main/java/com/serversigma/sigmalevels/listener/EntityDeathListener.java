package com.serversigma.sigmalevels.listener;

import com.serversigma.sigmalevels.utilitie.NumberFormat;
import de.tr7zw.nbtapi.NBTItem;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class EntityDeathListener implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBreak(EntityDeathEvent e) {

        if (e.getEntity() instanceof Player) return;

        Player p = e.getEntity().getKiller();

        if (p.getItemInHand() == null || p.getItemInHand().getType().equals(Material.AIR)) return;
        if (!(p.getWorld().getName().equals("Mundo"))) return;

        ItemStack itemStack = p.getItemInHand();

        if (!itemStack.getType().name().equals("DIAMOND_SWORD")) return;
        if (!itemStack.hasItemMeta()) return;
        if (!itemStack.getItemMeta().hasDisplayName()) return;

        NBTItem nbtItem = new NBTItem(itemStack);
        if (!nbtItem.hasKey("killedEntities")) return;

        double entities = nbtItem.getInteger("killedEntities");

        if (entities == Double.MAX_VALUE) return;

        nbtItem.setDouble("killedEntities", entities + 1);
        nbtItem.applyNBT(itemStack);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName("§bSuper Espada §8» §7" + NumberFormat.format(entities + 1));
        itemStack.setItemMeta(itemMeta);
    }

}