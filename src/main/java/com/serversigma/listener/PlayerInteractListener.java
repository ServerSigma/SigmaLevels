package com.serversigma.listener;

import com.serversigma.inventory.PickaxeInventory;
import com.serversigma.inventory.SwordInventory;
import com.serversigma.manager.EffectManager;
import com.serversigma.manager.ItemManager;
import com.serversigma.manager.LevelManager;
import com.serversigma.manager.LocationManager;
import de.tr7zw.nbtapi.NBTItem;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

@RequiredArgsConstructor
public class PlayerInteractListener implements Listener {

    private final LevelManager levelManager;
    private final EffectManager effectManager;
    private final ItemManager itemManager;
    private final LocationManager locationManager;

    @EventHandler
    public void onRightClick(PlayerInteractEvent e) {
        if (!(e.getAction() == Action.RIGHT_CLICK_BLOCK)) return;

        Player p = e.getPlayer();
        ItemStack item = e.getItem();
        if (item == null) return;

        if (!(
                item.getType().name().equals("DIAMOND_SWORD")
                        || item.getType().name().equals("BLAZE_ROD")
                        || item.getType().name().equals("DIAMOND_PICKAXE")
        )) return;

        if (item.getType().name().equals("BLAZE_ROD")
                && e.getPlayer().hasPermission("sigmaevolutions.define.table")
                && e.getClickedBlock().getType().name().equals("ENCHANTMENT_TABLE")
        ) {
            e.setCancelled(true);
            locationManager.setTableLocation(e.getClickedBlock().getLocation());
            effectManager.startTask();
        }

        ItemStack itemStack = p.getItemInHand();
        NBTItem nbtItem = new NBTItem(itemStack);
        if (!nbtItem.hasNBTData()) return;


        if (e.getItem().getType().name().equals("DIAMOND_SWORD")
                && e.getClickedBlock().getType().name().equalsIgnoreCase("ENCHANTMENT_TABLE")) {
            e.setCancelled(true);

            if (nbtItem.getInteger("entityKilled") >= 0) {
                SwordInventory swordInventory = new SwordInventory(levelManager, p, itemManager);
                swordInventory.openInventory(p);
                p.sendMessage("§7Abrindo menu...");
            }
        }

        if (e.getItem().getType().name().equals("DIAMOND_PICKAXE")
                && e.getClickedBlock().getType().name().equalsIgnoreCase("ENCHANTMENT_TABLE")) {
            e.setCancelled(true);

            if (nbtItem.getInteger("blocksBreaked") >= 0) {
                PickaxeInventory pickaxeInventory = new PickaxeInventory(levelManager, p, itemManager);
                pickaxeInventory.openInventory(p);
                p.sendMessage("§7Abrindo menu...");
            }
        }

    }

}