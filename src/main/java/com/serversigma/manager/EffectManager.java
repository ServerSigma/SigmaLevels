package com.serversigma.manager;

import com.sun.glass.ui.EventLoop;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scheduler.BukkitTask;

@RequiredArgsConstructor
public class EffectManager {

    private final Plugin plugin;

    public void startTask(Location location) {

        BukkitScheduler scheduler = plugin.getServer().getScheduler();

        scheduler.cancelAllTasks();
        scheduler.runTaskTimerAsynchronously(plugin, () -> createHelix(location), 15, 15);
    }


    private void createHelix(Location loc) {

        int radius = 1;
        for (double y = 0; y <= 2; y += 0.10) {
            double x = radius * Math.cos(y);
            double z = radius * Math.sin(y);
            loc.getWorld().spigot().playEffect(loc.clone().add(x, y, z), Effect.WITCH_MAGIC);
            loc.getWorld().spigot().playEffect(loc.clone().add(z, y, x), Effect.WITCH_MAGIC);
        }
    }
}