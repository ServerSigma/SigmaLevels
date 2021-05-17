package com.serversigma.listener;

import com.serversigma.manager.LevelManager;
import de.tr7zw.nbtapi.NBTItem;
import lombok.RequiredArgsConstructor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

@RequiredArgsConstructor
public class PlayerInteractListener implements Listener {

    private final Plugin plugin;
    private final LevelManager levelManager;

    @EventHandler
    public void onRightClick(PlayerInteractEvent e) {

        if (!(e.getAction() == Action.RIGHT_CLICK_BLOCK)) return;

        if (!(e.getItem().getType().name().equals("DIAMOND_PICKAXE"))) return;
        if(e.getItem().getType().equals(Material.AIR)) return;

        Player p = e.getPlayer();
        ItemStack itemStack = p.getItemInHand();
        NBTItem nbtItem = new NBTItem(itemStack);
        if (!nbtItem.hasNBTData()) return;

        if (nbtItem.getString("blocksBreaked") != null) {
//            LevelInventory inventory = new LevelInventory(configManager, levelManager);
//            inventory.openInventory(p);
            p.sendMessage("§7Abrindo menu...");
        }
    }

}