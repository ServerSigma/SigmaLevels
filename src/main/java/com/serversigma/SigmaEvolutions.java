package com.serversigma;

import com.henryfabio.minecraft.inventoryapi.manager.InventoryManager;
import com.serversigma.command.PickaxeGiveCommand;
import com.serversigma.command.SwordGiveCommand;
import com.serversigma.listener.BlockBreakListener;
import com.serversigma.listener.EntityDeathListener;
import com.serversigma.listener.PlayerInteractListener;
import com.serversigma.manager.ItemManager;
import com.serversigma.manager.LevelManager;
import me.bristermitten.pdm.PluginDependencyManager;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public final class SigmaEvolutions extends JavaPlugin {

    @Override
    public void onEnable() {
        loadDependencies();
        registerListeners();
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

        ItemManager itemManager = new ItemManager(this);
        LevelManager levelManager = new LevelManager(this);
        levelManager.loadPickaxeLevels();
        levelManager.loadSwordLevels();
       getCommand("pickaxe").setExecutor(new PickaxeGiveCommand(itemManager));
       getCommand("sword").setExecutor(new SwordGiveCommand(itemManager));
        registerListeners(
                new BlockBreakListener(levelManager, itemManager),
                new EntityDeathListener(levelManager, itemManager),
                new PlayerInteractListener(levelManager, itemManager)
        );
    }

    private void registerListeners(Listener... listeners) {
        for (Listener listener : listeners) {
            Bukkit.getPluginManager().registerEvents(listener, this);
        }
    }

}