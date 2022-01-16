package de.redfox.steckbrief;

import com.earth2me.essentials.Essentials;
import com.earth2me.essentials.User;
import com.google.gson.JsonElement;
import de.redfox.steckbrief.manager.config.ConfigManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public final class CharacterManager implements Listener, Runnable {
	
	public Essentials essentials = (Essentials) Bukkit.getPluginManager().getPlugin("EssentialsX");
	public static Map<UUID, CharacterDescription> characters = new HashMap<>();
	public static Map<String, UUID> characterNames = new HashMap<>();
	public static Map<UUID, PlayerInformation> players = new HashMap<>();
	
	public static void loadCharacters() {
		Set<Map.Entry<String, JsonElement>> entries = ConfigManager.characters.rootSection.entrySet();
		for (Map.Entry<String, JsonElement> entry : entries) {
			CharacterDescription characterDescription = CharacterDescription.load(entry.getValue().getAsJsonObject());
			characters.put(characterDescription.uuid, characterDescription);
			characterNames.put(characterDescription.getName(), characterDescription.uuid);
			characterDescription.updateMapView();
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
	
	public static PlayerInformation getPlayer(UUID player) {
		PlayerInformation information = players.get(player);
		if (information == null) {
			information = new PlayerInformation(player);
			players.put(player, information);
		}
		return information;
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
			player.setDisplayName(ChatColor.BLUE + character.getName() + ChatColor.WHITE);
			player.setPlayerListName(
					ChatColor.BLUE + character.getName() + ChatColor.GRAY + " (" + player.getName() + ")");
			player.setCustomName(ChatColor.BLUE + character.getName() + ChatColor.WHITE);
			player.setCustomNameVisible(true);
			User user = essentials.getUser(player);
			user.setNickname(ChatColor.BLUE + character.getName());
			player.sendMessage(
					ChatColor.GREEN + "You joined as " + ChatColor.GOLD + character.firstname + " " + character.lastname);
		}
		else {
			
		}
	}
	
	public void updateInventory(Inventory inventory) {
		for (ItemStack item : inventory.all(Material.PAPER).values()) {
			try {
				UUID characterUuid = UUID.fromString(
						item.getItemMeta().getPersistentDataContainer().get(CharacterDescription.cardKey,
								PersistentDataType.STRING));
				CharacterDescription character = characters.get(characterUuid);
				character.updateIdentityCard(item);
			} catch (Exception e) {
			}
		}
	}
	
	@Override
	public void run() {
		for (Player player : Bukkit.getOnlinePlayers()) {
			updateInventory(player.getInventory());
			updateInventory(player.getOpenInventory().getTopInventory());
		}
	}
	
}
