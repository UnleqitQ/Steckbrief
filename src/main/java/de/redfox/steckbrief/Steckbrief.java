package de.redfox.steckbrief;

import de.redfox.steckbrief.commands.SteckbriefCommand;
import de.redfox.steckbrief.manager.blindness.BlindnessListener;
import de.redfox.steckbrief.manager.blindness.BlindnessManager;
import de.redfox.steckbrief.manager.config.ConfigManager;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class Steckbrief extends JavaPlugin {
	
	@Override
	public void onEnable() {
		// Plugin startup logic
		ConfigManager.init();
		CharacterManager.loadCharacters();
		CharacterManager.loadPlayers();
		registerCommand("steckbrief", new SteckbriefCommand());
		Bukkit.getPluginManager().registerEvents(new BlindnessListener(), this);
		Bukkit.getPluginManager().registerEvents(new CreationManager(), this);
		Bukkit.getPluginManager().registerEvents(new CharacterManager(), this);
		
		BlindnessManager.init();
		
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
