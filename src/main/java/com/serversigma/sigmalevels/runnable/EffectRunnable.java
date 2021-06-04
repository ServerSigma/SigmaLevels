package com.serversigma.sigmalevels.runnable;

import com.serversigma.sigmalevels.manager.AltarManager;
import lombok.RequiredArgsConstructor;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

@RequiredArgsConstructor
public class EffectRunnable {

    private final Plugin plugin;
    private final AltarManager altarManager;

    private BukkitTask runnable;

    public void start() {
        runnable = new BukkitRunnable() {
            @Override
            public void run() {
                altarManager.playEffect();
            }
        }.runTaskTimerAsynchronously(plugin, 20, 20);
    }

    public void stop() {
        try {
            runnable.cancel();
        } catch (NullPointerException ignored) {}
    }

    public void restart() {
        stop();
        start();
    }

}