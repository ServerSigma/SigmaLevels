package com.serversigma;

import com.henryfabio.minecraft.inventoryapi.manager.InventoryManager;
import com.serversigma.command.PickaxeGiveCommand;
import com.serversigma.command.SwordGiveCommand;
import com.serversigma.listener.BlockBreakListener;
import com.serversigma.listener.EntityDeathListener;
import com.serversigma.listener.PlayerInteractListener;
import com.serversigma.manager.EffectManager;
import com.serversigma.manager.ItemManager;
import com.serversigma.manager.LevelManager;
import com.serversigma.manager.LocationManager;
import me.bristermitten.pdm.PluginDependencyManager;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public final class SigmaEvolutions extends JavaPlugin {

    ItemManager itemManager;
    LevelManager levelManager;
    EffectManager effectManager;
    LocationManager locationManager;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        loadDependencies();
        registerListeners();
        effectManager.startTask();
    }

    public void onDisable() {
        Bukkit.getServer().getScheduler().cancelAllTasks();
    }

    private void loadDependencies() {
        getLogger().info("Carregando dependências.");

        PluginDependencyManager.of(this)
                .loadAllDependencies()
                .exceptionally(throwable -> {
                    getLogger().severe(throwable.getMessage());
                    return null;
                }).join();

        InventoryManager.enable(this);
        getLogger().info("Dependências carregadas.");

        itemManager = new ItemManager(this);
        levelManager = new LevelManager(this);
        locationManager = new LocationManager(this, getConfig());
        effectManager = new EffectManager(this, locationManager);
        levelManager.loadPickaxeLevels();
        levelManager.loadSwordLevels();

        getCommand("pickaxe").setExecutor(new PickaxeGiveCommand(itemManager));
        getCommand("sword").setExecutor(new SwordGiveCommand(itemManager));
        registerListeners(
                new BlockBreakListener(levelManager, this, itemManager),
                new EntityDeathListener(levelManager, itemManager),
                new PlayerInteractListener(levelManager, effectManager, itemManager, locationManager)
        );

    }

    private void registerListeners(Listener... listeners) {
        for (Listener listener : listeners) {
            Bukkit.getPluginManager().registerEvents(listener, this);
        }
    }

}