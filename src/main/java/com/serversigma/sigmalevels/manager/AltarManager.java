package com.serversigma.sigmalevels.manager;

import com.serversigma.sigmalevels.runnable.EffectRunnable;
import lombok.RequiredArgsConstructor;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.plugin.Plugin;

@RequiredArgsConstructor
public class AltarManager {

    private final Plugin plugin;
    private final ConfigurationManager configManager;

    private Location location;
    private EffectRunnable effectRunnable;


    public Location getAltarLocation() {
        if (location == null) {
            location = configManager.getAltarLocation();
        }
        return location;
    }

    public void setAltarLocation(Location location) {
        configManager.setAltarLocation(location);
        this.location = configManager.getAltarLocation();
    }

    public boolean altarIsBreaked() {
        return getAltarLocation() == null
                || !getAltarLocation().getBlock().getType().name().equals("ENCHANTMENT_TABLE");
    }

    public void startTask() {

        loadAltarLocation();

        if (location == null || altarIsBreaked()) {
            plugin.getLogger().severe("Unable to start altar effect (Location is invalid).");
            return;
        }

        if (effectRunnable == null) {
            effectRunnable = new EffectRunnable(plugin, this);
            effectRunnable.start();
        } else {
            effectRunnable.restart();
        }
    }

    public void stopTask() {
        if (effectRunnable == null) return;
        effectRunnable.stop();
    }

    public void loadAltarLocation() {
        try {
            if (!getAltarLocation().getWorld().isChunkLoaded(location.getChunk())) {
                location.getWorld().loadChunk(location.getChunk());
            }
        } catch (NullPointerException ignored) {}
    }

    public void playEffect() {
        if (altarIsBreaked()) return;

        helixEffect(location, Effect.PORTAL);
        helixEffect(location, Effect.FLYING_GLYPH);
    }

    private void helixEffect(Location location, Effect effect) {
        for (double y = 0; y <= 2; y += 0.10) {
            double x = Math.cos(y);
            double z = Math.sin(y);

            location.getWorld().spigot().playEffect(location.clone().add(x, y, z), effect);
            location.getWorld().spigot().playEffect(location.clone().add(z, y, x), effect);
        }
    }

}