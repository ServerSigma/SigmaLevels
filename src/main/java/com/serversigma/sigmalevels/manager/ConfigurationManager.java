package com.serversigma.sigmalevels.manager;

import com.serversigma.sigmalevels.model.Level;
import com.serversigma.sigmalevels.model.LevelType;
import lombok.RequiredArgsConstructor;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class ConfigurationManager {

    private final Plugin plugin;

    public FileConfiguration getConfig() {
        return plugin.getConfig();
    }

    public List<Level> getLevels() {

        List<Level> levels = new ArrayList<>();

        try {
            ConfigurationSection pickaxeSection = getConfig().getConfigurationSection("pickaxes");
            pickaxeSection.getKeys(false).forEach(pickaxeLevel -> {

                Level level = new Level(
                        pickaxeSection.getInt(pickaxeLevel + ".price"),
                        pickaxeSection.getString(pickaxeLevel + ".name"),
                        pickaxeSection.getString(pickaxeLevel + ".permission"),
                        LevelType.TOOLS_PICKAXE);

                level.setFortune(pickaxeSection.getInt(pickaxeLevel + ".fortune"));
                level.setEfficiency(pickaxeSection.getInt(pickaxeLevel + ".efficiency"));
                level.setUnbreaking(pickaxeSection.getInt(pickaxeLevel + ".unbreaking"));
                levels.add(level);
            });

            ConfigurationSection swordSection = getConfig().getConfigurationSection("swords");
            swordSection.getKeys(false).forEach(swordLevel -> {

                Level level = new Level(
                        swordSection.getInt(swordLevel + ".price"),
                        swordSection.getString(swordLevel + ".name"),
                        swordSection.getString(swordLevel + ".permission"),
                        LevelType.TOOLS_SWORD);

                level.setSharpness(swordSection.getInt(swordLevel + ".sharpness"));
                level.setFireAspect(swordSection.getInt(swordLevel + ".fire-aspect"));
                level.setLooting(swordSection.getInt(swordLevel + ".looting"));
                level.setUnbreaking(swordSection.getInt(swordLevel + ".unbreaking"));
                levels.add(level);
            });

            ConfigurationSection armorSection = getConfig().getConfigurationSection("armors");
            armorSection.getKeys(false).forEach(armorLevel -> {

                    Level level = new Level(
                            armorSection.getInt(armorLevel + ".price"),
                            armorSection.getString(armorLevel + ".name"),
                            armorSection.getString(armorLevel + ".permission"),
                            LevelType.ARMOR_ALL);

                    level.setProtection(armorSection.getInt(armorLevel + ".protection"));
                    level.setUnbreaking(armorSection.getInt(armorLevel + ".unbreaking"));
                    levels.add(level);
            });

        } catch (NullPointerException exception) {
            plugin.getLogger().severe("Failed to load config (Bad configuration).");
            plugin.getLogger().severe(exception.getMessage());
        }
        return levels;
    }

    public Location getAltarLocation() {
        Location location = (Location) getConfig().get("altar.location");
        if (location == null) {
            plugin.getLogger().severe("Failed to load altar location (Null).");
        }
        return location;
    }

    public void setAltarLocation(Location location) {
        if (location != null) {
            getConfig().set("altar.location", location);
        } else {
            getConfig().set("altar", null);
        }
        plugin.saveConfig();
    }

}