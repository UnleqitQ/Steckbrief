package de.redfox.steckbrief.tobemoved;

import de.redfox.steckbrief.CreationManager;
import de.redfox.steckbrief.Steckbrief;
import net.arcaniax.headdisplays.HeadDisplays;
import net.arcaniax.headdisplays.display.Display;
import net.arcaniax.headdisplays.display.UpdateFrequency;
import net.arcaniax.headdisplays.util.Cuboid;
import net.arcaniax.headdisplays.util.Direction;
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
	public static Location spawnLoc = new Location(Bukkit.getWorld("world"), 10, 65, 0, 0, 0);
	
	private Player player;
	
	private LocalListener activeListener;

	static {
		Display display = new Display(HeadDisplays.getDisplayManger().getNextDisplayId(),
				Cuboid.fromRadius(FirstJoinSession.startLoc.clone().add(2, 1, 0), 4), UpdateFrequency.SECONDS_1, true,
				true, false, true, "%steckbrief_0%", 10, FirstJoinSession.startLoc.clone().add(2, 1, 0),
				Direction.NORTH);

		HeadDisplays.getDisplayManger().addDisplay(display);
	}

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
		CreationManager.startCreation(player);
	}
	
	public void stop() {
		HandlerList.unregisterAll(activeListener);
		activeSessions.remove(player, this);
		
		player.setGameMode(GameMode.SURVIVAL);
		player.setFlying(false);
		player.teleport(spawnLoc);
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
