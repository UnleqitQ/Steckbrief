package de.redfox.steckbrief;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import de.redfox.steckbrief.config.ConfigObject;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PlayerInformation {
	
	public UUID player;
	public List<UUID> characters;
	
	public PlayerInformation(UUID player) {
		this.player = player;
		characters = new ArrayList<>();
	}
	
	public void save(ConfigObject configObject) {
		JsonArray array = new JsonArray(characters.size());
		for (UUID character : characters) {
			array.add(character.toString());
		}
		configObject.rootSection.add(player.toString(), array);
	}
	
	public static PlayerInformation load(ConfigObject configObject, UUID player) {
		PlayerInformation playerInformation = new PlayerInformation(player);
		JsonArray array = (JsonArray) configObject.rootSection.get(player.toString());
		for (JsonElement character : array) {
			playerInformation.characters.add(UUID.fromString(character.getAsString()));
		}
		return playerInformation;
	}
	
}
