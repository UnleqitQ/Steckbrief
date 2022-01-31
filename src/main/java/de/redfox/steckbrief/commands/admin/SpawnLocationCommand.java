package de.redfox.steckbrief.commands.admin;

import de.redfox.steckbrief.Steckbrief;
import de.redfox.steckbrief.command.Command;
import de.redfox.steckbrief.tobemoved.FirstJoinSession;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class SpawnLocationCommand extends Command {
	
	public SpawnLocationCommand() {
		super("setspawnlocation");
	}
	
	@Override
	public void execute(CommandSender sender, String[] args) {
		if (sender instanceof Player player) {
			Steckbrief instance = Steckbrief.getInstance();
			instance.rlc();
			
			FirstJoinSession.spawnLoc = new Location(player.getWorld(), player.getLocation().getBlockX(),
					player.getLocation().getBlockY(), player.getLocation().getBlockZ());
			FileConfiguration config = instance.getConfig();
			config.set("spawnLocation.x", player.getLocation().getBlockX());
			config.set("spawnLocation.y", player.getLocation().getBlockY());
			config.set("spawnLocation.z", player.getLocation().getBlockZ());
			config.set("spawnLocation.world", player.getWorld().getName());
			
			instance.saveConfig();
		}
	}
	
}
