package com.serversigma;

import org.bukkit.plugin.java.JavaPlugin;

public final class SigmaEvolutions extends JavaPlugin {

    @Override
    public void onEnable() {
        loadDependencies();
        registerCommands();
        registerListeners();
    }

    private void loadDependencies() {
        // TODO
    }

    private void registerCommands() {
        // TODO
    }

    private void registerListeners() {
        // TODO
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

}