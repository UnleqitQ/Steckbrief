package de.redfox.steckbrief;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public final class CreationManager implements Listener {
	
	public static Map<Player, CreationInstance> instances = new HashMap<>();
	
	public static void startCreation(Player player) {
		CreationInstance instance = new CreationInstance(player);
		instances.put(player, instance);
		instance.preInit();
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
	
	@EventHandler
	public void onMove(@NotNull PlayerMoveEvent event) {
		if (instances.containsKey(event.getPlayer())) {
			Location to = Objects.requireNonNullElse(event.getTo(), event.getFrom());
			Location result = event.getFrom().clone();
			result.setYaw(to.getYaw());
			result.setPitch(to.getPitch());
			event.setTo(result);
		}
	}
	
}
