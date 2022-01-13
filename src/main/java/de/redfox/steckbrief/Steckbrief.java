package de.redfox.steckbrief;

import de.redfox.steckbrief.config.ConfigManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class Steckbrief extends JavaPlugin {
	
	@Override
	public void onEnable() {
		// Plugin startup logic
		ConfigManager.init();
	}
	
	@Override
	public void onDisable() {
		// Plugin shutdown logic
	}
	
}
