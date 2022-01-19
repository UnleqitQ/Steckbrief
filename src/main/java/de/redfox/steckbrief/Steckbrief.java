package de.redfox.steckbrief;

import de.redfox.steckbrief.commands.RoleplayCommand;
import de.redfox.steckbrief.manager.blindness.BlindnessManager;
import de.redfox.steckbrief.manager.config.ConfigManager;
import net.arcaniax.headdisplays.HeadDisplays;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class Steckbrief extends JavaPlugin {

	private static Steckbrief instance;

	public Steckbrief() {
		instance = this;
	}
	
	@Override
	public void onEnable() {
		ConfigManager.init();
		CharacterManager.loadCharacters();
		CharacterManager.loadPlayers();
		BlindnessManager.init();

		registerCommand("roleplay", new RoleplayCommand());

		//Bukkit.getPluginManager().registerEvents(new BlindnessListener(), this);
		Bukkit.getPluginManager().registerEvents(new CreationManager(), this);
		Bukkit.getPluginManager().registerEvents(new CharacterManager(), this);
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
