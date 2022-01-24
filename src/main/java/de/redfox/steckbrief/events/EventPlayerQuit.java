package de.redfox.steckbrief.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class EventPlayerQuit implements Listener {

    @EventHandler
    public void onEvent(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        player.sendTitle("", "");
    }
}
