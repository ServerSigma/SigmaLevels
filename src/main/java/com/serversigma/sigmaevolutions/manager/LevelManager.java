package com.serversigma.sigmaevolutions.manager;

import com.serversigma.sigmaevolutions.model.ArmorLevel;
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
    private final List<ArmorLevel> armorLevels = new ArrayList<>();

    public void loadPickaxeLevels() {
        pickaxeLevels.add(new PickaxeLevel("§ePicareta: §6✯§f✯✯✯✯", 1000, 6, 4, 4, ""));
        pickaxeLevels.add(new PickaxeLevel("§ePicareta: §6✯✯§f✯✯✯", 3500, 7, 5, 5, ""));
        pickaxeLevels.add(new PickaxeLevel("§ePicareta: §6✯✯✯§f✯✯", 8800, 8, 6, 6, ""));
        pickaxeLevels.add(new PickaxeLevel("§ePicareta: §6✯✯✯✯§f✯", 18000, 9, 7, 7, ""));
        pickaxeLevels.add(new PickaxeLevel("§ePicareta: §6✯✯✯✯✯", 35000, 10, 8, 8, ""));
        pickaxeLevels.add(new PickaxeLevel("§ePicareta: §bVIP", 50000, 11, 9, 9, "pickaxe.vip"));
        pickaxeLevels.add(new PickaxeLevel("§ePicareta: §3Platina", 73000, 12, 10, 10, "pickaxe.platina"));
    }

    public void loadArmorLevels() {
        armorLevels.add(new ArmorLevel("Armadura I", 1, 1, 1, "armadura.perm"));
        armorLevels.add(new ArmorLevel("Armadura II", 2, 2, 2, "armadura.perm"));
        armorLevels.add(new ArmorLevel("Armadura III", 3, 3, 3, "armadura.perm"));
        armorLevels.add(new ArmorLevel("Armadura IV", 4, 4, 4, "armadura.perm"));
        armorLevels.add(new ArmorLevel("Armadura V", 5, 5, 5, "armadura.perm"));
        armorLevels.add(new ArmorLevel("Armadura VI", 6, 6, 6, "armadura.perm"));
        armorLevels.add(new ArmorLevel("Armadura VII", 7, 7, 7, "armadura.perm"));
        armorLevels.add(new ArmorLevel("Armadura VIII", 8, 8, 8, "armadura.perm"));
        armorLevels.add(new ArmorLevel("Armadura IX", 9, 9, 9, "armadura.perm"));
        armorLevels.add(new ArmorLevel("Armadura X", 10, 10, 10, "armadura.perm"));

    }

    public void loadSwordLevels() {
        swordLevels.add(new SwordLevel("§eEspada: §6✯§f✯✯✯✯", 15, 1, 1, 1, ""));
        swordLevels.add(new SwordLevel("§eEspada: §6✯✯§f✯✯✯", 50, 2, 2, 2, ""));
        swordLevels.add(new SwordLevel("§eEspada: §6✯✯✯§f✯✯", 100, 3, 3, 3, ""));
        swordLevels.add(new SwordLevel("§eEspada: §6✯✯✯✯§f✯", 200, 4, 4, 4, ""));
        swordLevels.add(new SwordLevel("§eEspada: §6✯✯✯✯✯§f", 500, 5, 5, 5, ""));
        swordLevels.add(new SwordLevel("§eEspada: §d✯§f✯✯", 1200, 6, 6, 6, ""));
        swordLevels.add(new SwordLevel("§eEspada: §d✯✯§f✯", 3600, 7, 7, 7, ""));
        swordLevels.add(new SwordLevel("§eEspada: §d✯✯✯", 7200, 8, 8, 8, ""));
        swordLevels.add(new SwordLevel("§eEspada: §bVIP", 9800, 9, 9, 9, "sword.vip"));
        swordLevels.add(new SwordLevel("§eEspada: §3Platina", 11900, 10, 10, 10, "sword.platina"));
    }

    public PickaxeLevel getPickaxeNextLevel(double blocks) {
        for (PickaxeLevel level: pickaxeLevels) {
            if (blocks == level.getBlocks()) return level;
        }
        return null;
    }

    public SwordLevel getSwordNextLevel(int entities) {
        for (SwordLevel level: swordLevels) {
            if (entities == level.getEntities()) return level;
        }
        return null;
    }
}