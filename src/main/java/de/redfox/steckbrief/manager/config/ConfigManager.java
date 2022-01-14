package de.redfox.steckbrief.manager.config;

import com.google.gson.JsonPrimitive;

public class ConfigManager {
	
	public static ConfigObject data;

	public static class CharactersConfigObject extends ConfigObject {

		public CharactersConfigObject(String path, String file) {
			super(path, file);
		}

		boolean isRegistered() {
			return false;
		}
	}

	public static CharactersConfigObject characters;
	public static ConfigObject players;
	public static ConfigObject lang_de;
	public static ConfigObject lang_en;
	
	private static final String pluginPath = "plugins/Steckbrief";
	
	public static void init() {
		characters = new CharactersConfigObject(pluginPath, "characters.json");

		players = new ConfigObject(pluginPath, "players.json");
		data = new ConfigObject(pluginPath, "data.json");
		lang_de = new ConfigObject(pluginPath, "lang_de.json");
		lang_en = new ConfigObject(pluginPath, "lang_en.json");

		data.setDefault("test.test2.test3", new JsonPrimitive("testss"));
		data.save();
	}
	
}
