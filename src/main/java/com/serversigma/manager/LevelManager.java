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
        pickaxeLevels.add(new PickaxeLevel("§ePicareta: §6✯✯§f✯✯✯", 10, 7, 5, 5));
        pickaxeLevels.add(new PickaxeLevel("§ePicareta: §6✯✯✯§f✯✯", 10, 8, 6, 6));
        pickaxeLevels.add(new PickaxeLevel("§ePicareta: §6✯✯✯✯§f✯", 10, 9, 7, 7));
        pickaxeLevels.add(new PickaxeLevel("§ePicareta: §6✯✯✯✯✯", 10, 10, 8, 8));
    }

    public void loadSwordLevels() {
        swordLevels.add(new SwordLevel("§eEspada: §6✯§f✯✯✯✯", 1, 6, 4, 4));
        swordLevels.add(new SwordLevel("§eEspada: §6✯✯§f✯✯✯", 2, 7, 5, 5));
        swordLevels.add(new SwordLevel("§eEspada: §6✯✯✯§f✯✯", 3, 8, 6, 6));
        swordLevels.add(new SwordLevel("§eEspada: §6✯✯✯✯§f✯", 4, 9, 7, 7));
        swordLevels.add(new SwordLevel("§eEspada: §6✯✯✯✯✯", 5, 10, 8, 8));
    }

    public PickaxeLevel getPickaxeLevelByBlocks(int blocks) {
        switch(blocks) {
            case 1:
                return pickaxeLevels.get(0);
            case 2:
                return pickaxeLevels.get(1);
            case 3:
                return pickaxeLevels.get(2);
            case 4:
                return pickaxeLevels.get(3);
            case 5:
                return pickaxeLevels.get(4);
        }
        return null;
    }

    public SwordLevel getSwordLevelByBlocks(int entitys) {
        switch(entitys) {
            case 1:
                return swordLevels.get(0);
            case 2:
                return swordLevels.get(1);
            case 3:
                return swordLevels.get(2);
            case 4:
                return swordLevels.get(3);
            case 5:
                return swordLevels.get(4);
        }
        return null;
    }
}