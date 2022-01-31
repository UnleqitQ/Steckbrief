package de.redfox.steckbrief;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public final class CreationManager implements Listener {
	
	public static Map<UUID, CreationInstance> instances = new HashMap<>();
	
	public static void startCreation(Player player) {
		CreationInstance instance = new CreationInstance(player);
		instances.put(player.getUniqueId(), instance);
		instance.preInit();
	}
	
	@EventHandler
	public void onLeave(@NotNull PlayerQuitEvent event) {
		if (instances.containsKey(event.getPlayer().getUniqueId()))
			event.getPlayer().sendTitle("", "", 0, 0, 0);
	}
	
	@EventHandler (priority = EventPriority.HIGHEST)
	public void onChat(@NotNull AsyncPlayerChatEvent event) {
		if (instances.containsKey(event.getPlayer().getUniqueId())) {
			event.setCancelled(true);
			CreationInstance instance = instances.get(event.getPlayer().getUniqueId());
			instance.input(event.getMessage());
		}
	}
	
	@EventHandler
	public void onMove(@NotNull PlayerMoveEvent event) {
		if (instances.containsKey(event.getPlayer().getUniqueId())) {
			Location to = Objects.requireNonNullElse(event.getTo(), event.getFrom());
			Location result = event.getFrom().clone();
			result.setYaw(to.getYaw());
			result.setPitch(to.getPitch());
			event.setTo(result);
		}
	}
	
	@EventHandler
	public void onJoin(@NotNull PlayerJoinEvent event) {
		if (instances.containsKey(event.getPlayer().getUniqueId())) {
			event.getPlayer().sendTitle("", "", 0, 0, 0);
		}
		instances.remove(event.getPlayer().getUniqueId());
	}
	
}
