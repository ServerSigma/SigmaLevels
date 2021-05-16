package com.serversigma.manager;

import lombok.RequiredArgsConstructor;
import org.bukkit.World;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

import java.util.List;

@RequiredArgsConstructor
public class ConfigManager {

    private final Plugin plugin;


    public List<String> getGuiLores() {
        return plugin.getConfig().getStringList("gui-configuration.item-lore");
    }

    public List<String> getSwordLore(ItemMeta itemMeta) {

        List<String> lore = plugin.getConfig().getStringList("configuration.sword-lore");
        int sharpnessLevel = itemMeta.getEnchantLevel(Enchantment.DAMAGE_ALL);
        int lootingLevel = itemMeta.getEnchantLevel(Enchantment.LOOT_BONUS_MOBS);
        int unbreakingLevel = itemMeta.getEnchantLevel(Enchantment.DURABILITY);

        lore.replaceAll(s -> s.replace("&", "§")
                .replace("%sharpness", sharpnessLevel + "")
                .replace("%looting", lootingLevel + "")
                .replace("%unbreaking", unbreakingLevel + "")
        );
        return lore;
    }
}