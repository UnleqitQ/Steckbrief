package de.redfox.steckbrief;

import com.google.gson.JsonElement;
import de.redfox.steckbrief.manager.config.ConfigManager;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public final class CharacterManager implements Listener {
	
	public static Map<UUID, CharacterDescription> characters = new HashMap<>();
	
	public static Map<UUID, PlayerInformation> players = new HashMap<>();
	
	public static void loadCharacters() {
		Set<Map.Entry<String, JsonElement>> entries = ConfigManager.characters.rootSection.entrySet();
		for (Map.Entry<String, JsonElement> entry : entries) {
			CharacterDescription characterDescription = CharacterDescription.load(entry.getValue().getAsJsonObject());
			characters.put(characterDescription.uuid, characterDescription);
		}
	}
	
	public static void loadPlayers() {
		for (String key : ConfigManager.players.rootSection.keySet()) {
			PlayerInformation playerInformation = PlayerInformation.load(ConfigManager.players, UUID.fromString(key));
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
	
	public static void setPlayerCharacter(UUID player, UUID character) {
		PlayerInformation information = players.get(player);
		if (information == null) {
			information = new PlayerInformation(player);
			players.put(player, information);
		}
		information.characters.add(0, character);
	}
	
	public static boolean hasAliveCharacter(UUID player) {
		return players.containsKey(player) && players.get(player).characters.size() > 0 && characters.get(
				players.get(player).characters.get(0)).alive;
	}
	
	@EventHandler
	public void onJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		if (hasAliveCharacter(player.getUniqueId())) {
			CharacterDescription character = characters.get(players.get(player.getUniqueId()).characters.get(0));
			player.setDisplayName(ChatColor.BLUE + character.firstname + " " + character.lastname + ChatColor.WHITE);
			player.setPlayerListName(
					ChatColor.BLUE + character.firstname + " " + character.lastname + ChatColor.GRAY + " (" + player.getName() + ")");
			player.setCustomName(ChatColor.BLUE + character.firstname + " " + character.lastname + ChatColor.WHITE);
			player.setCustomNameVisible(true);
			player.sendMessage(
					ChatColor.GREEN + "You joined as " + ChatColor.GOLD + character.firstname + " " + character.lastname);
		}
		else {
		
		}
	}
	
}
