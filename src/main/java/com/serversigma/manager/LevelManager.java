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

    public void loadPickaxeLevels() {
        pickaxeLevels.add(new PickaxeLevel("§ePicareta: §6✯§f✯✯✯✯", 10, 6, 4, 4));
        pickaxeLevels.add(new PickaxeLevel("§ePicareta: §6✯✯§f✯✯✯", 20, 7, 5, 5));
        pickaxeLevels.add(new PickaxeLevel("§ePicareta: §6✯✯✯§f✯✯", 30, 8, 6, 6));
        pickaxeLevels.add(new PickaxeLevel("§ePicareta: §6✯✯✯✯§f✯", 40, 9, 7, 7));
        pickaxeLevels.add(new PickaxeLevel("§ePicareta: §6✯✯✯✯✯", 50, 10, 8, 8));
    }

    public void loadSwordLevels() {
        swordLevels.add(new SwordLevel("§eEspada: §6✯§f✯✯✯✯", 1, 6, 4, 4));
        swordLevels.add(new SwordLevel("§eEspada: §6✯✯§f✯✯✯", 2, 7, 5, 5));
        swordLevels.add(new SwordLevel("§eEspada: §6✯✯✯§f✯✯", 3, 8, 6, 6));
        swordLevels.add(new SwordLevel("§eEspada: §6✯✯✯✯§f✯", 4, 9, 7, 7));
        swordLevels.add(new SwordLevel("§eEspada: §6✯✯✯✯✯", 5, 10, 8, 8));
    }

    public PickaxeLevel getPickaxeNextLevel(int blocks) {
        for (PickaxeLevel level: pickaxeLevels) {
            if (blocks == level.getBlocks()) return level;
        }
        return null;
    }

    public SwordLevel getSwordNextLevel(int entitys) {
        for (SwordLevel level: swordLevels) {
            if (entitys == level.getEntitys()) return level;
        }
        return null;
    }
}