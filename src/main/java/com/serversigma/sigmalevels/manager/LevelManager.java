package com.serversigma.sigmalevels.manager;

import com.serversigma.sigmalevels.model.Level;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@RequiredArgsConstructor
public class LevelManager {

    private final Plugin plugin;
    private final ConfigurationManager configManager;

    @Getter
    private final List<Level> levels = new ArrayList<>();

    public void loadLevels() {
        levels.addAll(configManager.getLevels());
        levels.sort(Comparator.comparingDouble(Level::getPrice));
        plugin.getLogger().info("Loaded " + levels.size() + " levels.");
    }

    public Level getNextLevel(Level level) {
        for (Level nextLevel : levels) {
            if (nextLevel.getPrice() == level.getPrice() &&
                    nextLevel.getLevelType().equals(level.getLevelType())) {
                return nextLevel;
            }
        }
        return null;
    }

}
