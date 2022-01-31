package de.redfox.steckbrief;

import de.redfox.steckbrief.commands.RoleplayCommand;
import de.redfox.steckbrief.manager.PlaceholderManger;
import de.redfox.steckbrief.manager.config.ConfigManager;
import de.redfox.steckbrief.tobemoved.FirstJoinSession;
import de.redfox.steckbrief.tobemoved.Other;
import net.arcaniax.headdisplays.HeadDisplays;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.CommandExecutor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class Steckbrief extends JavaPlugin {
	
	private static Steckbrief instance;
	public PlaceholderManger placeholderManger;
	
	public Steckbrief() {
		instance = this;
	}
	
	@Override
	public void onEnable() {
		ConfigManager.init();
		CharacterManager.loadCharacters();
		CharacterManager.loadPlayers();
		Other.init();
		
		saveDefaultConfig();
		rlc();
		
		registerCommand("roleplay", new RoleplayCommand());
		
		//Bukkit.getPluginManager().registerEvents(new BlindnessListener(), this);
		Bukkit.getPluginManager().registerEvents(new CreationManager(), this);
		Bukkit.getPluginManager().registerEvents(new CharacterManager(), this);
		//Bukkit.getPluginManager().registerEvents(new EventPlayerJoin(), this);
		//Bukkit.getPluginManager().registerEvents(new EventPlayerQuit(), this);
		
		
		//placeholderManger = new PlaceholderManger();
		//PlaceholderAPIPlugin.getInstance().getLocalExpansionManager().register(placeholderManger);
		
		for (Player player : Bukkit.getOnlinePlayers()) {
			player.kickPlayer("Plugin \"" + super.getName() + "\" loaded\nPlease rejoin");
		}
	}
	
	public <T extends CommandExecutor> void registerCommand(String cmd, T handler) {
		Objects.requireNonNull(getCommand(cmd)).setExecutor(handler);
	}
	
	public static Steckbrief getInstance() {
		return instance;
	}
	
	public static HeadDisplays getHeadDisplays() {
		return (HeadDisplays) Bukkit.getPluginManager().getPlugin("HeadDisplays");
	}
	
	public void rlc() {
		reloadConfig();
		loadConfig();
	}
	
	public void loadConfig() {
		{
			int x = Steckbrief.getInstance().getConfig().getInt("spawnLocation.x", 0);
			int y = Steckbrief.getInstance().getConfig().getInt("spawnLocation.y", 100);
			int z = Steckbrief.getInstance().getConfig().getInt("spawnLocation.z", 0);
			String worldName = Steckbrief.getInstance().getConfig().getString("spawnLocation.world", "world");
			World world = Bukkit.getWorld(worldName);
			if (world != null) {
				FirstJoinSession.spawnLoc = new Location(world, x, y, z);
			}
		}
	}
	
}
