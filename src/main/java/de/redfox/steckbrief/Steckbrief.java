package de.redfox.steckbrief;

import de.redfox.steckbrief.commands.RoleplayCommand;
import de.redfox.steckbrief.manager.PlaceholderManger;
import de.redfox.steckbrief.manager.blindness.BlindnessManager;
import de.redfox.steckbrief.manager.config.ConfigManager;
import de.redfox.steckbrief.tobemoved.FirstJoinSession;
import net.arcaniax.headdisplays.HeadDisplays;
import net.arcaniax.headdisplays.display.Display;
import net.arcaniax.headdisplays.display.UpdateFrequency;
import net.arcaniax.headdisplays.util.Cuboid;
import net.arcaniax.headdisplays.util.Direction;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
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
		BlindnessManager.init();
		
		registerCommand("roleplay", new RoleplayCommand());
		
		//Bukkit.getPluginManager().registerEvents(new BlindnessListener(), this);
		Bukkit.getPluginManager().registerEvents(new CreationManager(), this);
		Bukkit.getPluginManager().registerEvents(new CharacterManager(), this);
		
		placeholderManger = new PlaceholderManger();
		placeholderManger.register();
		Display display = new Display(HeadDisplays.getDisplayManger().getNextDisplayId(),
				Cuboid.fromRadius(FirstJoinSession.spawnLoc.clone().add(2, 1, 0), 4), UpdateFrequency.SECONDS_1, true,
				true, false, true, "%steckbrief_1%", 10, FirstJoinSession.spawnLoc.clone().add(2, 1, 0),
				Direction.NORTH);
		HeadDisplays.getDisplayManger().addDisplay(display);
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
