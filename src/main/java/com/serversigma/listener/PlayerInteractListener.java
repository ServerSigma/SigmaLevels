package com.serversigma.listener;

import com.serversigma.inventory.SwordInventory;
import com.serversigma.manager.ItemManager;
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

@RequiredArgsConstructor
public class PlayerInteractListener implements Listener {

    // private final Plugin plugin;
    private final LevelManager levelManager;
    private final ItemManager itemManager;

    @EventHandler
    public void onRightClick(PlayerInteractEvent e) {

        if (!(e.getAction() == Action.RIGHT_CLICK_BLOCK)) return;

        if (!(e.getItem().getType().name().equals("DIAMOND_SWORD"))) return;
        if (e.getItem().getType().equals(Material.AIR)) return;

        Player p = e.getPlayer();
        ItemStack itemStack = p.getItemInHand();
        NBTItem nbtItem = new NBTItem(itemStack);
        if (!nbtItem.hasNBTData()) return;

        if (nbtItem.getInteger("entityKilled") != null) {
            SwordInventory inventory = new SwordInventory(p, levelManager, itemManager);
            inventory.openInventory(p);
            p.sendMessage("§7Abrindo menu...");
        }
    }

}