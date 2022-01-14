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
    private static Location startLoc;
    private static Location spawnLoc;

    private Player player;

    private LocalListener activeListener;

    public FirstJoinSession(Player player) {
        this.player = player;
        activeListener = new LocalListener();
        Bukkit.getPluginManager().registerEvents(activeListener, Steckbrief.getInstance());
    }

    public void start() {
        player.setGameMode(GameMode.SPECTATOR);
        player.setFlying(true);
        player.teleport(startLoc);

        new MessageQueue(player)
                .add(new TitleData("test", "tes", 1, 1, 1))
                .add(new TitleData("test2", "tes2", 2, 2, 2))
                .add(new TitleData("test3", "tes3", 1, 2, 1))
                .add(new TitleData("test4", "tes4", 1, 1, 1))
        .start();
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

            if (x != to.getX() || y != to.getY() || z != to.getZ()) {
                event.setTo(from);
            }
        }
    }

}
