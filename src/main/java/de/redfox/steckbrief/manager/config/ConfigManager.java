package de.redfox.steckbrief.manager.config;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import org.bukkit.entity.Player;

import javax.swing.*;

public class ConfigManager {
	
	public static ConfigObject data;

	public static ConfigObject characters;
	public static ConfigObject players;
	public static ConfigObject lang_de;
	public static ConfigObject lang_en;
	
	private static final String pluginPath = "plugins/Steckbrief";
	
	public static void init() {
		characters = new ConfigObject(pluginPath, "characters.json");
		players = new ConfigObject(pluginPath, "players.json");
		data = new ConfigObject(pluginPath, "data.json");

		lang_de = new ConfigObject(pluginPath, "lang_de.json");
		lang_en = new ConfigObject(pluginPath, "lang_en.json");

		data.setDefault("test.test2.test3", new JsonPrimitive("testss"));

		data.save();
	}
}
