package com.serversigma.listener;

import com.serversigma.inventory.SwordInventory;
import com.serversigma.manager.EffectManager;
import com.serversigma.manager.ItemManager;
import com.serversigma.manager.LevelManager;
import de.tr7zw.nbtapi.NBTItem;
import lombok.RequiredArgsConstructor;
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
    private final EffectManager effectManager;
    private final ItemManager itemManager;

    @EventHandler
    public void onRightClick(PlayerInteractEvent e) {

        if (!(e.getAction() == Action.RIGHT_CLICK_BLOCK)) return;

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
            effectManager.startTask(e.getClickedBlock().getLocation()); //????????????????,
            // e pra startar a task, como tu vai puxar a loc, K,
            // agora faz isso lá no onEnable
        }

        if (item.getType().name().equals("DIAMOND_SWORD")) {

            Player p = e.getPlayer();

            ItemStack itemStack = p.getItemInHand();
            NBTItem nbtItem = new NBTItem(itemStack);
            if (!nbtItem.hasNBTData()) return;

            if (nbtItem.getInteger("entityKilled") != null) {
                SwordInventory swordInventory = new SwordInventory(p, levelManager, itemManager);
                swordInventory.openInventory(p);
                p.sendMessage("§7Abrindo menu...");
            }
        }
    }

}