package de.redfox.steckbrief.tobemoved;

import de.redfox.steckbrief.Steckbrief;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class FirstJoinSession {
    private static Location startLoc = new Location(Bukkit.getWorlds().get(0), 0, 65, 0, 0, 0);
    private static Location spawnLoc = new Location(Bukkit.getWorlds().get(0), 10, 65, 0, 0, 0);

    private Player player;

    private LocalListener activeListener;

    public FirstJoinSession(Player player) {
        this.player = player;
        activeListener = new LocalListener();
        Bukkit.getPluginManager().registerEvents(activeListener, Steckbrief.getInstance());
        start();
    }

    public void start() {
        player.setGameMode(GameMode.SPECTATOR);
        player.setFlying(true);
        player.teleport(startLoc);


    }

    public void stop() {
        HandlerList.unregisterAll(activeListener);

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
