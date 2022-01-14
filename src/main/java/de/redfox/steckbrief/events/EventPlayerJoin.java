package de.redfox.steckbrief.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class EventPlayerJoin implements Listener {

    @EventHandler
    public void onEvent(PlayerJoinEvent event) {
        Player player = event.getPlayer();

    }
}
