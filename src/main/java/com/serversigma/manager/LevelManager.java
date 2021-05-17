package com.serversigma.manager;

import com.serversigma.model.PickaxeLevel;
import com.serversigma.model.SwordLevel;
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

    public void defaultPickaxeLevels() {
    }
}