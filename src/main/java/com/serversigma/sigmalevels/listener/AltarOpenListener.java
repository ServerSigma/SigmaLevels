package com.serversigma.sigmalevels.listener;

import com.serversigma.sigmalevels.event.AltarOpenEvent;
import com.serversigma.sigmalevels.inventory.ArmorInventory;
import com.serversigma.sigmalevels.inventory.PickaxeInventory;
import com.serversigma.sigmalevels.inventory.SwordInventory;
import com.serversigma.sigmalevels.manager.ItemManager;
import com.serversigma.sigmalevels.manager.LevelManager;
import com.serversigma.sigmalevels.model.LevelType;
import lombok.RequiredArgsConstructor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

@RequiredArgsConstructor
public class AltarOpenListener implements Listener {

    private final ItemManager itemManager;
    private final LevelManager levelManager;

    @EventHandler
    public void onTableOpen(AltarOpenEvent event) {

        Player player = event.getPlayer();
        LevelType levelType = event.getLevelType();

        if (levelType.equals(LevelType.TOOLS_PICKAXE)) {
            new PickaxeInventory(player, itemManager, levelManager).openInventory(player);
        }

        if (levelType.equals(LevelType.TOOLS_SWORD)) {
            new SwordInventory(player, itemManager, levelManager).openInventory(player);
        }

        if (levelType.name().startsWith("ARMOR")) {
            new ArmorInventory(player, itemManager, levelManager).openInventory(player);
        }

        player.playSound(player.getLocation(), Sound.CHEST_OPEN, 1, 1);
    }

}