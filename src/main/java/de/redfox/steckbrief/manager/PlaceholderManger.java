package de.redfox.steckbrief.manager;

import de.redfox.steckbrief.CharacterManager;
import de.redfox.steckbrief.Steckbrief;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlaceholderManger extends PlaceholderExpansion {
	
	public static Map<UUID, String[]> valuesMap = new HashMap<>();
	
	public static String[] getValues(UUID uuid) {
		if (!valuesMap.containsKey(uuid))
			valuesMap.put(uuid, new String[5]);
		return valuesMap.get(uuid);
	}
	
	public static String getValue(UUID uuid, int index) {
		return getValues(uuid)[index];
	}
	
	public static void setValue(UUID uuid, int index, String value) {
		getValues(uuid)[index] = value;
	}
	
	@Override
	public @NotNull String getIdentifier() {
		return Steckbrief.getInstance().getDescription().getName();
	}
	
	@Override
	public @NotNull String getAuthor() {
		return Steckbrief.getInstance().getDescription().getAuthors().toString();
	}
	
	@Override
	public @NotNull String getVersion() {
		return Steckbrief.getInstance().getDescription().getVersion();
	}
	
	
	@Override
	public String onRequest(OfflinePlayer player, @NotNull String params) {
		String[] paramArray = params.split("_");
		if (paramArray.length > 0) {
			if (paramArray[0].contentEquals("name")) {
				if (CharacterManager.hasAliveCharacter(player.getUniqueId())) {
					return CharacterManager.characters.get(
							CharacterManager.getPlayer(player.getUniqueId()).getAliveCharacter()).getName();
				}
				else {
					return player.getName();
				}
			}
		}
		return "";
	}
	
	@Override
	public String onPlaceholderRequest(Player player, @NotNull String params) {
		//return getValue(player.getUniqueId(), Integer.parseInt(params.replaceAll("[\\D]+", "")));
		String[] paramArray = params.split("_");
		if (paramArray.length > 0) {
			if (paramArray[0].contentEquals("name")) {
				if (CharacterManager.hasAliveCharacter(player.getUniqueId())) {
					return CharacterManager.characters.get(
							CharacterManager.getPlayer(player.getUniqueId()).getAliveCharacter()).getName();
				}
				else {
					return player.getName();
				}
			}
		}
		return "";
	}
	
}
