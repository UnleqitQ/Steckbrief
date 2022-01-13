package de.redfox.steckbrief;

import com.google.gson.JsonElement;
import de.redfox.steckbrief.manager.config.ConfigManager;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public final class CharacterManager {
	
	public static Map<UUID, CharacterDescription> characters = new HashMap<>();
	
	public static Map<UUID, PlayerInformation> players = new HashMap<>();
	
	private CharacterManager() {}
	
	public static void loadCharacters() {
		Set<Map.Entry<String, JsonElement>> entries = ConfigManager.characters.rootSection.entrySet();
		for (Map.Entry<String, JsonElement> entry : entries) {
			CharacterDescription characterDescription = CharacterDescription.load(entry.getValue().getAsJsonObject());
			characters.put(characterDescription.uuid, characterDescription);
		}
	}
	
	public static void loadPlayers() {
		for (String key : ConfigManager.players.rootSection.keySet()) {
			PlayerInformation playerInformation = PlayerInformation.load(ConfigManager.players,
					UUID.fromString(key));
			players.put(playerInformation.player, playerInformation);
		}
	}
	
	public static void savePlayer(UUID player) {
		if (players.containsKey(player)) {
			players.get(player).save(ConfigManager.players);
		}
	}
	
	public static void saveCharacter(UUID character) {
		if (characters.containsKey(character)) {
			characters.get(character).save(ConfigManager.characters);
		}
	}
	
}
