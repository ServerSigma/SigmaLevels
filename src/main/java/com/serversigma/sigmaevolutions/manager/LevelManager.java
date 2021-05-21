package com.serversigma.sigmaevolutions.manager;

import com.serversigma.sigmaevolutions.model.PickaxeLevel;
import com.serversigma.sigmaevolutions.model.SwordLevel;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;

@Data
@RequiredArgsConstructor
public class LevelManager {

    private final Plugin plugin;
    private final List<SwordLevel> swordLevels = new ArrayList<>();
    private final List<PickaxeLevel> pickaxeLevels = new ArrayList<>();

    public void loadPickaxeLevels() {
//        pickaxeLevels.add(new PickaxeLevel("§ePicareta: §6✯§f✯✯✯✯", 1000, 6, 4, 4));
//        pickaxeLevels.add(new PickaxeLevel("§ePicareta: §6✯✯§f✯✯✯", 3000, 7, 5, 5));
//        pickaxeLevels.add(new PickaxeLevel("§ePicareta: §6✯✯✯§f✯✯", 8000, 8, 6, 6));
//        pickaxeLevels.add(new PickaxeLevel("§ePicareta: §6✯✯✯✯§f✯", 18000, 9, 7, 7));
//        pickaxeLevels.add(new PickaxeLevel("§ePicareta: §6✯✯✯✯✯", 35000, 10, 8, 8));
        pickaxeLevels.add(new PickaxeLevel("§ePicareta: §6✯§f✯✯✯✯", 1000 / 5, 6, 4, 4));
        pickaxeLevels.add(new PickaxeLevel("§ePicareta: §6✯✯§f✯✯✯", 3000 / 5, 7, 5, 5));
        pickaxeLevels.add(new PickaxeLevel("§ePicareta: §6✯✯✯§f✯✯", 8000 / 5, 8, 6, 6));
        pickaxeLevels.add(new PickaxeLevel("§ePicareta: §6✯✯✯✯§f✯", 18000 / 5, 9, 7, 7));
        pickaxeLevels.add(new PickaxeLevel("§ePicareta: §6✯✯✯✯✯", 35000 / 5, 10, 8, 8));
    }

    public void loadSwordLevels() {
        swordLevels.add(new SwordLevel("§eEspada: §6✯§f✯✯✯✯", 500, 6, 4, 4));
        swordLevels.add(new SwordLevel("§eEspada: §6✯✯§f✯✯✯", 1600, 7, 5, 5));
        swordLevels.add(new SwordLevel("§eEspada: §6✯✯✯§f✯✯", 3400, 8, 6, 6));
        swordLevels.add(new SwordLevel("§eEspada: §6✯✯✯✯§f✯", 7000, 9, 7, 7));
        swordLevels.add(new SwordLevel("§eEspada: §6✯✯✯✯✯", 10900, 10, 8, 8));
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