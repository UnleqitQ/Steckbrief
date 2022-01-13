package de.redfox.steckbrief;

import de.redfox.steckbrief.commands.SteckbriefCommand;
import de.redfox.steckbrief.config.ConfigManager;
import de.redfox.steckbrief.events.MainListener;
import de.redfox.steckbrief.world.CustomGenerator;
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
		new CustomGenerator();

		Bukkit.getPluginManager().registerEvents(new MainListener(), this);
	}
	
	@Override
	public void onDisable() {
		// Plugin shutdown logic
	}
	
	public <T extends CommandExecutor> void registerCommand(String cmd, T handler) {
		Objects.requireNonNull(getCommand(cmd)).setExecutor(handler);
	}
	
}
