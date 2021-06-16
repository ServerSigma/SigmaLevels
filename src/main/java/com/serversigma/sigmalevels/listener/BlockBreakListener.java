package com.serversigma.sigmalevels.listener;

import com.serversigma.sigmagems.api.SigmaGemsAPI;
import com.serversigma.sigmalevels.manager.AltarManager;
import com.serversigma.sigmalevels.utilitie.NumberFormat;
import de.tr7zw.nbtapi.NBTItem;
import lombok.RequiredArgsConstructor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
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


    private final AltarManager altarManager;

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBreak(BlockBreakEvent e) {

        if (e.isCancelled()) return;

        Player p = e.getPlayer();

        if (p.hasPermission("sigmalevels.table.break")
                && altarManager.getAltarLocation().equals(e.getBlock().getLocation())) {

            altarManager.stopTask();
            altarManager.setAltarLocation(null);
        }

        if (!(p.getWorld().getName().equals("Mundo"))) return;
        if (p.getItemInHand() == null || p.getItemInHand().getType().equals(Material.AIR)) return;

        ItemStack itemStack = p.getItemInHand();
        NBTItem nbtItem = new NBTItem(itemStack);

        if (!(itemStack.getType().name().equals("DIAMOND_PICKAXE"))) return;
        if (!nbtItem.hasKey("minedBlocks")) return;

        e.getBlock().setType(Material.AIR);

        double blocks = nbtItem.getDouble("minedBlocks");
        int fortune = itemStack.getEnchantmentLevel(Enchantment.LOOT_BONUS_BLOCKS);

        if (fortune < 20) SigmaGemsAPI.addGems(p, fortune);

        if (blocks == Double.MAX_VALUE) return;

        nbtItem.setDouble("minedBlocks", blocks + 1);
        nbtItem.applyNBT(itemStack);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName("§bSuper Picareta §8» §7" + NumberFormat.format(blocks + 1));
        itemStack.setItemMeta(itemMeta);

        p.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 40, 1));
    }

}