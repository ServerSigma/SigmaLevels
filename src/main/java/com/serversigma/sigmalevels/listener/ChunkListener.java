package com.serversigma.sigmalevels.listener;

import com.serversigma.sigmalevels.manager.AltarManager;
import lombok.RequiredArgsConstructor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.ChunkUnloadEvent;

@RequiredArgsConstructor
public class ChunkListener implements Listener {

    private final AltarManager altarManager;

    @EventHandler
    public void onChunkEvent(ChunkUnloadEvent event) {
        try {
            if (event.getChunk().equals(altarManager.getAltarLocation().getChunk())) {
                event.setCancelled(true);
            }
        } catch (NullPointerException ignored) {}
    }

}
