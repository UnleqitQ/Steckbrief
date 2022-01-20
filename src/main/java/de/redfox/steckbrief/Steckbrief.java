package de.redfox.steckbrief;

import de.redfox.steckbrief.commands.RoleplayCommand;
import de.redfox.steckbrief.manager.PlaceholderManger;
import de.redfox.steckbrief.manager.config.ConfigManager;
import de.redfox.steckbrief.tobemoved.Other;
import me.clip.placeholderapi.PlaceholderAPIPlugin;
import net.arcaniax.headdisplays.HeadDisplays;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class Steckbrief extends JavaPlugin {
	public static volatile Thread spigotThread;
	
	private static Steckbrief instance;
	public PlaceholderManger placeholderManger;
	
	public Steckbrief() {
		instance = this;
	}
	
	@Override
	public void onEnable() {
		spigotThread = Thread.currentThread();

		ConfigManager.init();
		CharacterManager.loadCharacters();
		CharacterManager.loadPlayers();
		Other.init();
		
		registerCommand("roleplay", new RoleplayCommand());
		
		//Bukkit.getPluginManager().registerEvents(new BlindnessListener(), this);
		Bukkit.getPluginManager().registerEvents(new CreationManager(), this);
		Bukkit.getPluginManager().registerEvents(new CharacterManager(), this);


		placeholderManger = new PlaceholderManger();
		PlaceholderAPIPlugin.getInstance().getLocalExpansionManager().register(placeholderManger);

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
