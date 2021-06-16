package com.serversigma.sigmalevels;

import com.henryfabio.minecraft.inventoryapi.manager.InventoryManager;
import com.serversigma.sigmalevels.command.ArmorGiveCommand;
import com.serversigma.sigmalevels.command.PickaxeGiveCommand;
import com.serversigma.sigmalevels.command.SwordGiveCommand;
import com.serversigma.sigmalevels.listener.*;
import com.serversigma.sigmalevels.manager.*;
import me.bristermitten.pdm.PluginDependencyManager;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

@SuppressWarnings("unused")
public final class SigmaLevels extends JavaPlugin {

    ItemManager itemManager;
    LevelManager levelManager;
    AltarManager altarManager;
    ConfigurationManager configManager;

    @Override
    public void onEnable() {

        // Configuration
        saveDefaultConfig();

        // Dependencies
        PluginDependencyManager.of(this)
                .loadAllDependencies().exceptionally(throwable -> {
                    getLogger().severe(throwable.getMessage());
                    setEnabled(false);
                    return null;
                }).join();

        if (!InventoryManager.isEnabled()) {
            InventoryManager.enable(this);
        }

        // Managers
        configManager = new ConfigurationManager(this);
        levelManager = new LevelManager(this, configManager);
        altarManager = new AltarManager(this, configManager);
        itemManager = new ItemManager();
        levelManager.loadLevels();
        altarManager.startTask();

        // Listeners
        registerListeners(
                new EntityDeathListener(),
                new ChunkListener(altarManager),
                new BlockBreakListener(altarManager),
                new PlayerInteractListener(altarManager),
                new AltarOpenListener(itemManager, levelManager)
        );

        // Commands
        getCommand("sword").setExecutor(new SwordGiveCommand(itemManager));
        getCommand("armor").setExecutor(new ArmorGiveCommand(itemManager));
        getCommand("pickaxe").setExecutor(new PickaxeGiveCommand(itemManager));
    }

    private void registerListeners(Listener... listeners) {
        for (Listener listener : listeners) {
            Bukkit.getPluginManager().registerEvents(listener, this);
        }
    }

    @Override
    public void onDisable() {
        altarManager.stopTask();
    }

}