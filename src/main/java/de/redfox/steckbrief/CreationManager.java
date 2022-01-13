package de.redfox.steckbrief;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public final class CreationManager implements Listener {
	
	public static Map<Player, CreationInstance> instances = new HashMap<>();
	
	public static void startCreation(Player player) {
		instances.put(player, new CreationInstance(player));
	}
	
	@EventHandler
	public void onLeave(@NotNull PlayerQuitEvent event) {
		instances.remove(event.getPlayer());
	}
	
	@EventHandler
	public void onChat(@NotNull AsyncPlayerChatEvent event) {
		if (instances.containsKey(event.getPlayer())) {
		}
		
	}
	
}
