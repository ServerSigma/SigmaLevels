package com.serversigma.listener;

import com.serversigma.manager.EffectManager;
import com.serversigma.manager.LocationManager;
import de.tr7zw.nbtapi.NBTItem;
import lombok.RequiredArgsConstructor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

@RequiredArgsConstructor
public class BlockBreakListener implements Listener {

    private final EffectManager effectManager;
    private final LocationManager locationManager;

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBreak(BlockBreakEvent e) {

        Player p = e.getPlayer();

        if (e.getBlock().getType().name().equals("ENCHANTMENT_TABLE")) {
            if (locationManager.getTableLocation().equals(e.getBlock().getLocation())) {
                effectManager.stopTask();
            }
        }

        if (p.getItemInHand() == null || p.getItemInHand().getType().equals(Material.AIR)) return;
        if (!(p.getWorld().getName().equals("Mundo"))) return;

        ItemStack itemStack = p.getItemInHand();
        NBTItem nbtItem = new NBTItem(itemStack);

        if (!(itemStack.getType().name().equals("DIAMOND_PICKAXE"))) return;
        if (!nbtItem.hasNBTData()) return;

        int blocks = nbtItem.getInteger("blocksBreaked");
        nbtItem.setInteger("blocksBreaked", blocks + 1);
        nbtItem.applyNBT(itemStack);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName("§eSuper Picareta §8» §7" + (blocks + 1));
        itemStack.setItemMeta(itemMeta);

        p.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 1, 60));
    }
}