package de.redfox.steckbrief;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
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

	public static void startCreation(Player player, Runnable callback) {
		instances.put(player, new CreationInstance(player, callback	));
	}
	
	@EventHandler
	public void onLeave(@NotNull PlayerQuitEvent event) {
		instances.remove(event.getPlayer());
	}
	
	@EventHandler (priority = EventPriority.HIGHEST)
	public void onChat(@NotNull AsyncPlayerChatEvent event) {
		if (instances.containsKey(event.getPlayer())) {
			event.setCancelled(true);
			CreationInstance instance = instances.get(event.getPlayer());
			instance.input(event.getMessage());
		}
	}
	
}
