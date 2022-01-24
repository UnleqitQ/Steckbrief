package de.redfox.steckbrief.events;

import com.google.gson.JsonElement;
import de.redfox.steckbrief.manager.config.ConfigManager;
import de.redfox.steckbrief.tobemoved.FirstJoinSession;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class EventPlayerJoin implements Listener {

    @EventHandler
    public void onEvent(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        player.sendTitle("", "");
        System.out.println("test");
        JsonElement jsonElement = ConfigManager.players.rootSection.get(player.getUniqueId().toString());
        if (jsonElement == null) {
            System.out.println("test");
            new FirstJoinSession(player);
        }

    }
}
