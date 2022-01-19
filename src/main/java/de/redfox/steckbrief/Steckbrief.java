package de.redfox.steckbrief;

import de.redfox.steckbrief.commands.RoleplayCommand;
import de.redfox.steckbrief.events.EventPlayerJoin;
import de.redfox.steckbrief.manager.blindness.BlindnessManager;
import de.redfox.steckbrief.manager.config.ConfigManager;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class Steckbrief extends JavaPlugin {
	private static Steckbrief instance;
	
	public Steckbrief() {
		instance = this;
	}
	
	public static Steckbrief getInstance() {
		return instance;
	}
	
	@Override
	public void onEnable() {
		// Plugin startup logic
		ConfigManager.init();
		CharacterManager.loadCharacters();
		CharacterManager.loadPlayers();
		registerCommand("roleplay", new RoleplayCommand());
		//Bukkit.getPluginManager().registerEvents(new BlindnessListener(), this);
		Bukkit.getPluginManager().registerEvents(new CreationManager(), this);
		CharacterManager manager = new CharacterManager();
		Bukkit.getPluginManager().registerEvents(manager, this);
		Bukkit.getScheduler().runTaskTimer(this, manager, 20, 40);

		Bukkit.getPluginManager().registerEvents(new EventPlayerJoin(), this);

	}
	
	@Override
	public void onLoad() {
	}
	
	@Override
	public void onDisable() {
		// Plugin shutdown logic
	}
	
	public <T extends CommandExecutor> void registerCommand(String cmd, T handler) {
		Objects.requireNonNull(getCommand(cmd)).setExecutor(handler);
	}
	
}
