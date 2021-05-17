package com.serversigma.listener;

import com.serversigma.manager.ItemManager;
import com.serversigma.manager.LevelManager;
import com.serversigma.model.PickaxeLevel;
import de.tr7zw.nbtapi.NBTItem;
import lombok.RequiredArgsConstructor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

@RequiredArgsConstructor
public class BlockBreakListener implements Listener {

    private final LevelManager levelManager;
    private final ItemManager itemManager;

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBreak(BlockBreakEvent e) {
        if(e.isCancelled()) return;
        Player p = e.getPlayer();

        if(!(p.getWorld().getName().equals("Mundo"))) return;

        ItemStack itemStack = p.getItemInHand();

        if (!itemStack.getType().name().endsWith("_PICKAXE")) return;
        if (!itemStack.hasItemMeta()) return;
        if (!itemStack.getItemMeta().hasDisplayName()) return;

        NBTItem nbtItem = new NBTItem(itemStack);
        if (!nbtItem.hasNBTData()) return;

        int blocks = (nbtItem.getInteger("blocksBreaked")
                == null ? 0 : nbtItem.getInteger("blocksBreaked"));

        String displayName = "§e
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(displayName
                .replace("&", "§")
                .replace("%blocos", "" + blocks));
        itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        itemStack.setItemMeta(itemMeta);

        itemManager.updatePickaxe(itemStack);
        p.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 10 * 20, 1));

        for (PickaxeLevel pickaxeLevel : levelManager.getPickaxeLevels()) {
            if (blocks == pickaxeLevel.getBlocks()) {
                itemManager.upgradePickaxe(itemStack, pickaxeLevel);
                p.playSound(p.getLocation(), Sound.LEVEL_UP, 1, 1);
                p.sendMessage("§a§lLEVEL UP! §aSua picareta evoluiu " + pickaxeLevel.getName().replace("&", "§"));
            }
        }
    }
}