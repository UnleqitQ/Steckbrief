package de.redfox.steckbrief.manager.config;

import com.google.gson.JsonPrimitive;
import de.redfox.steckbrief.CreationInstance;

import java.util.Map;

public class ConfigManager {
	
	public static ConfigObject data;

	public static ConfigObject characters;
	public static ConfigObject players;
	public static ConfigObject lang_de;
	public static ConfigObject lang_en;

	public static ConfigObject selectedLang;
	
	public static final String pluginPath = "plugins/Steckbrief";
	
	public static void init() {
		characters = new ConfigObject(pluginPath, "characters.json");
		players = new ConfigObject(pluginPath, "players.json");
		data = new ConfigObject(pluginPath, "data.json");

		lang_en = new ConfigObject(pluginPath, "lang_en.json");
		lang_de = new ConfigObject(pluginPath, "lang_de.json");

		selectedLang = lang_de;
	}

	public static ConfigObject registerCharacterCreationMessages() {
		Map<String, Map<String, String>> messages = CreationInstance.getMessages();
		Map<String, String> en = messages.get("en");
		Map<String, String> de = messages.get("de");

		en.forEach((k, v) -> lang_en.setDefault("CharacterCreation." + k, new JsonPrimitive(v)));
		de.forEach((k, v) -> lang_de.setDefault("CharacterCreation." + k, new JsonPrimitive(v)));
		lang_en.save();
		lang_de.save();
		return selectedLang;
	}
}
