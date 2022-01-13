package de.redfox.steckbrief.events;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class MainListener implements Listener {

    @EventHandler
    public void onEvent(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        player.teleport(new Location(Bukkit.getWorld("test"), 0, 0, 0));
        player.setGameMode(GameMode.CREATIVE);
        player.setFlying(true);
        System.out.println("test");
    }
}
