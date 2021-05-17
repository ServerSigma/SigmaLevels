package com.serversigma.managers;

import com.serversigma.models.PickaxeLevel;
import com.serversigma.models.SwordLevel;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;

@Data
@RequiredArgsConstructor
public class LevelManager {

    private final Plugin plugin;
    private final List<PickaxeLevel> pickaxeLevels = new ArrayList<>();
    private final List<SwordLevel> swordLevels = new ArrayList<>();

    public void loadAllLevels() {
        FileConfiguration config = plugin.getConfig();
        ConfigurationSection section = config.getConfigurationSection("levels");

        for (String level : section.getKeys(false)) {
            String name = section.getString(level + ".name");
            int blocks = section.getInt(level + ".blocks");
            int efficiencyLevel = section.getInt(level + ".efficiency");
            int fortuneLevel = section.getInt(level + ".fortune");
            int unbreakingLevel = section.getInt(level + ".unbreaking");
            PickaxeLevel lvl = new PickaxeLevel(name, blocks, efficiencyLevel, fortuneLevel, unbreakingLevel);
            pickaxeLevels.add(lvl);
        }

        for (String level : section.getKeys(false)) {
            String name = section.getString(level + ".name");
            int entity = section.getInt(level + ".entitys");
            int sharpnessLevel = section.getInt(level + ".sharpness");
            int lootingLevel = section.getInt(level + ".looting");
            int unbreakingLevel = section.getInt(level + ".unbreaking");
            SwordLevel lvl = new SwordLevel(name, entity, sharpnessLevel, lootingLevel, unbreakingLevel);
            swordLevels.add(lvl);
        }
    }
}