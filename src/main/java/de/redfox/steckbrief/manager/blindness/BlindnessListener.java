package de.redfox.steckbrief.manager.blindness;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class BlindnessListener implements Listener {

    @EventHandler
    public void onEvent(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        player.teleport(new Location(Bukkit.getWorld("test"), 0, 0, 0));
        player.setGameMode(GameMode.CREATIVE);
        player.setFlying(true);
        player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 9999, 254, false, false));
        System.out.println("test");
    }

    @EventHandler
    public void onEvent(EntitySpawnEvent event) {
        Entity entity = event.getEntity();
        if (entity.getType() == EntityType.ENDER_DRAGON && entity.getWorld().getUID().equals(BlindnessManager.customGenerator.world.getUID())) {
            entity.remove();
        }
    }
}
