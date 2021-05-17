package com.serversigma.manager;

import com.serversigma.runnable.EffectRunnable;
import lombok.RequiredArgsConstructor;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.plugin.Plugin;

@RequiredArgsConstructor
public class EffectManager {

    private final Plugin plugin;
    private EffectRunnable effectRunnable;
    private final LocationManager locationManager;

    public void startTask() {
        if (effectRunnable == null) {
            effectRunnable = new EffectRunnable(plugin, this, locationManager);
            effectRunnable.start();
        } else {
            effectRunnable.restart();
        }
    }

    public void stopTask() {
        if (effectRunnable == null) return;
        effectRunnable.stop();
    }

    public void createHelix(Location loc) {
        int radius = 1;
        for (double y = 0; y <= 2; y += 0.10) {
            double x = radius * Math.cos(y);
            double z = radius * Math.sin(y);
            loc.getWorld().spigot().playEffect(loc.clone().add(x, y, z), Effect.PORTAL);
            loc.getWorld().spigot().playEffect(loc.clone().add(z, y, x), Effect.PORTAL);
            loc.getWorld().spigot().playEffect(loc.clone().add(x, y, z), Effect.FLYING_GLYPH);
            loc.getWorld().spigot().playEffect(loc.clone().add(z, y, x), Effect.FLYING_GLYPH);
        }
    }
}