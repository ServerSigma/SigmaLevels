package com.serversigma.sigmaevolutions;

import com.henryfabio.minecraft.inventoryapi.manager.InventoryManager;
import com.serversigma.sigmaevolutions.command.PickaxeGiveCommand;
import com.serversigma.sigmaevolutions.command.SwordGiveCommand;
import com.serversigma.sigmaevolutions.listener.BlockBreakListener;
import com.serversigma.sigmaevolutions.listener.EntityDeathListener;
import com.serversigma.sigmaevolutions.listener.PlayerInteractListener;
import com.serversigma.sigmaevolutions.manager.EffectManager;
import com.serversigma.sigmaevolutions.manager.ItemManager;
import com.serversigma.sigmaevolutions.manager.LevelManager;
import com.serversigma.sigmaevolutions.manager.LocationManager;
import me.bristermitten.pdm.PluginDependencyManager;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public final class SigmaEvolutions extends JavaPlugin {

    private ItemManager itemManager;
    private LevelManager levelManager;
    private EffectManager effectManager;
    private LocationManager locationManager;

    @Override
    public void onEnable() {

        saveDefaultConfig();
        loadDependencies();
        loadManagers();

        getCommand("pickaxe").setExecutor(new PickaxeGiveCommand(itemManager));
        getCommand("sword").setExecutor(new SwordGiveCommand(itemManager));

        registerListeners(
                new BlockBreakListener(effectManager, locationManager),
                new EntityDeathListener(),
                new PlayerInteractListener(levelManager, effectManager, itemManager, locationManager)
        );
    }

    @Override
    public void onDisable() {
        effectManager.stopTask();
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

    }

    private void registerListeners(Listener... listeners) {
        for (Listener listener : listeners) {
            Bukkit.getPluginManager().registerEvents(listener, this);
        }
    }

    private void loadManagers() {
        locationManager = new LocationManager(this);
        effectManager = new EffectManager(this, locationManager);
        itemManager = new ItemManager();
        levelManager = new LevelManager(this);

        levelManager.loadPickaxeLevels();
        levelManager.loadSwordLevels();
        effectManager.startTask();
    }
}