package de.redfox.steckbrief;

import de.redfox.steckbrief.commands.RoleplayCommand;
import de.redfox.steckbrief.events.EventPlayerJoin;
import de.redfox.steckbrief.events.EventPlayerQuit;
import de.redfox.steckbrief.manager.PlaceholderManger;
import de.redfox.steckbrief.manager.config.ConfigManager;
import de.redfox.steckbrief.tobemoved.Other;
import net.arcaniax.headdisplays.HeadDisplays;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;
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
		
		registerCommand("roleplay", new RoleplayCommand());
		
		//Bukkit.getPluginManager().registerEvents(new BlindnessListener(), this);
		Bukkit.getPluginManager().registerEvents(new CreationManager(), this);
		Bukkit.getPluginManager().registerEvents(new CharacterManager(), this);
		Bukkit.getPluginManager().registerEvents(new EventPlayerJoin(), this);
		Bukkit.getPluginManager().registerEvents(new EventPlayerQuit(), this);


		//placeholderManger = new PlaceholderManger();
		//PlaceholderAPIPlugin.getInstance().getLocalExpansionManager().register(placeholderManger);

		ArrayList

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
	
}
