package com.serversigma.listener;

import com.serversigma.manager.ItemManager;
import com.serversigma.manager.LevelManager;
import com.serversigma.model.SwordLevel;
import de.tr7zw.nbtapi.NBTItem;
import lombok.RequiredArgsConstructor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

@RequiredArgsConstructor
public class EntityDeathListener implements Listener {

    private final LevelManager levelManager;
    private final ItemManager itemManager;

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBreak(EntityDeathEvent e) {

        Player p = e.getEntity().getKiller();

        if (!(p.getWorld().getName().equals("Mundo"))) return;

        ItemStack itemStack = p.getItemInHand();

        if (!itemStack.getType().name().equals("DIAMOND_SWORD")) return;
        if (!itemStack.hasItemMeta()) return;
        if (!itemStack.getItemMeta().hasDisplayName()) return;

        NBTItem nbtItem = new NBTItem(itemStack);
        if (!nbtItem.hasNBTData()) return;

        int entitys = nbtItem.getInteger("entityKilled");
        nbtItem.setInteger("entityKilled", entitys + 1);
        nbtItem.applyNBT(itemStack);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName("§eSuper Espada §8» §7" + entitys);
        itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        itemStack.setItemMeta(itemMeta);
        p.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 10 * 20, 1));

        /*for (SwordLevel swordLevel : levelManager.getSwordLevels()) {
            if (entitys == swordLevel.getEntitys()) {
                itemManager.upgradeSword(itemStack, swordLevel);
                p.playSound(p.getLocation(), Sound.LEVEL_UP, 1, 1);
                p.sendMessage("§a§lLEVEL UP! §aSua espada evoluiu " + swordLevel.getName());
            }
        }*/
    }
}