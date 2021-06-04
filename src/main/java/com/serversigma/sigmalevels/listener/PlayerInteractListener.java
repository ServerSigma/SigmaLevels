package com.serversigma.sigmalevels.listener;

import com.serversigma.sigmalevels.event.AltarOpenEvent;
import com.serversigma.sigmalevels.manager.AltarManager;
import com.serversigma.sigmalevels.model.LevelType;
import de.tr7zw.nbtapi.NBTItem;
import lombok.RequiredArgsConstructor;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

@RequiredArgsConstructor
public class PlayerInteractListener implements Listener {

    private final AltarManager altarManager;

    @EventHandler
    public void onRightClick(PlayerInteractEvent e) {

        if (e.getAction() != Action.RIGHT_CLICK_BLOCK) return;

        Block clickedBlock = e.getClickedBlock();

        if (!clickedBlock.getType().name().equals("ENCHANTMENT_TABLE")) return;

        e.setCancelled(true);

        Player p = e.getPlayer();
        ItemStack item = e.getItem();

        if (item == null) {
            if (clickedBlock.getLocation().equals(altarManager.getAltarLocation())) {
                p.sendMessage("§cVocê precisa abrir a mesa utilizando um item especial.");
            }
            return;
        }

        if (p.hasPermission("sigmalevels.define.table")
                && item.getType().name().equals("BLAZE_ROD")) {

            p.sendMessage("§aVocê definiu o novo local da mesa.");
            altarManager.setAltarLocation(clickedBlock.getLocation());
            altarManager.startTask();
            return;
        }

        if (!clickedBlock.getLocation().equals(altarManager.getAltarLocation())) return;

        NBTItem nbtItem = new NBTItem(item);

        if (!(nbtItem.hasKey("spentGems")
                || nbtItem.hasKey("minedBlocks")
                || nbtItem.hasKey("killedEntities"))) {

            p.sendMessage("§cVocê precisa abrir a mesa utilizando um item especial.");
            return;
        }

        LevelType levelType = LevelType.getByItemStack(item);

        if (levelType != null) {
            p.getServer().getPluginManager().callEvent(new AltarOpenEvent(p, levelType));
            return;
        }
        p.sendMessage("§cVocê precisa abrir a mesa utilizando um item especial.");
    }

}