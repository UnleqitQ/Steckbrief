package de.redfox.steckbrief.tobemoved;

import de.redfox.steckbrief.CreationManager;
import de.redfox.steckbrief.Steckbrief;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.HashMap;

public class FirstJoinSession {

	public static HashMap<Player, FirstJoinSession> activeSessions = new HashMap<>();
	
	public static Location startLoc = new Location(Bukkit.getWorld("world"), 0, 65, 0, 0, 0);
	public static Location spawnLoc = new Location(Bukkit.getWorld("world"), 10, 0, 0, 0, 0);
	
	private Player player;
	
	private LocalListener activeListener;


	public FirstJoinSession(Player player) {
		this.player = player;
		activeListener = new LocalListener();
		Bukkit.getPluginManager().registerEvents(activeListener, Steckbrief.getInstance());
		activeSessions.put(player, this);
		start();
	}
	
	public void start() {
		player.setGameMode(GameMode.SPECTATOR);
		player.setFlying(true);
		player.teleport(startLoc);
		CreationManager.startCreation(player, this::stop);
	}
	
	public void stop() {
		Bukkit.getScheduler().runTask(Steckbrief.getInstance(), () -> {
			HandlerList.unregisterAll(activeListener);
			activeSessions.remove(player, this);

			player.setGameMode(GameMode.SURVIVAL);
			player.setFlying(false);
			player.teleport(spawnLoc);
		});
	}
	
	private class LocalListener implements Listener {
		
		@EventHandler
		public void onEvent(PlayerMoveEvent event) {
			
			if (!event.getPlayer().getUniqueId().equals(player.getUniqueId()))
				return;
			
			Location from = event.getFrom();
			Location to = event.getTo();
			double x = from.getX();
			double y = from.getY();
			double z = from.getZ();
			
			from.setYaw(to.getYaw());
			from.setPitch(to.getPitch());
			
			if (x != to.getX() || y != to.getY() || z != to.getZ()) {
				event.setTo(from);
			}
		}
		
	}
	
}
