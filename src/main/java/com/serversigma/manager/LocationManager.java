package com.serversigma.manager;

import lombok.RequiredArgsConstructor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;

@RequiredArgsConstructor
public class LocationManager {

    private Location location;
    private final Plugin plugin;
    private final FileConfiguration config;

    public Location getTableLocation() {
        if (location == null) {
            World world = plugin.getServer().getWorld(config.getString("table.world"));
            double x = config.getDouble("table.locX");
            double y = config.getDouble("table.locY");
            double z = config.getDouble("table.locZ");
            setTableLocation(new Location(world, x, y, z));
        }
        return location;
    }

    public void setTableLocation(Location location) {
        config.set("table.world", location.getWorld().getName());
        config.set("table.locX", location.getX());
        config.set("table.locY", location.getY());
        config.set("table.locZ", location.getZ());
        plugin.saveConfig();
        this.location = location;
    }
}