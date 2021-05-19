package com.serversigma.runnable;

import com.serversigma.manager.EffectManager;
import com.serversigma.manager.LocationManager;
import lombok.RequiredArgsConstructor;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

@RequiredArgsConstructor
public class EffectRunnable {

    private final Plugin plugin;
    private final EffectManager effectManager;
    private final LocationManager locationManager;
    private BukkitTask runnable;

    public void start() {
        runnable = new BukkitRunnable() {
            @Override
            public void run() {
                effectManager.createHelix(locationManager.getTableLocation());
            }
        }.runTaskTimerAsynchronously(plugin, 15, 15);
    }

    public void stop() {
        runnable.cancel();
    }

    public void restart() {
        stop();
        start();
    }

}
