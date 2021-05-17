package com.serversigma;

import com.henryfabio.minecraft.inventoryapi.manager.InventoryManager;
import com.serversigma.command.PickaxeGiveCommand;
import com.serversigma.manager.ItemManager;
import me.bristermitten.pdm.PluginDependencyManager;
import org.bukkit.command.CommandExecutor;
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
        registerCommand("pickaxe", new PickaxeGiveCommand(itemManager));
    }

    private void registerCommand(String commandName, CommandExecutor executor) {
        getCommand(commandName).setExecutor(executor);
    }

    private void registerListeners() {
        // TODO
    }

}