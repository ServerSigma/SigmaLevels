package com.serversigma.sigmalevels.event;

import com.serversigma.sigmalevels.model.LevelType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

@Getter
@RequiredArgsConstructor
@SuppressWarnings("unused")
public class AltarOpenEvent extends Event {

    private final Player player;
    private final LevelType levelType;

    private static final HandlerList HANDLERS = new HandlerList();

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

}