package de.redfox.steckbrief.manager;

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
		return "steckbrief";
	}
	
	@Override
	public @NotNull String getAuthor() {
		return "Kevin, UnleqitQ";
	}
	
	@Override
	public @NotNull String getVersion() {
		return "1.0.0";
	}
	
	@Override
	public String onRequest(OfflinePlayer player, @NotNull String params) {
		return getValue(player.getUniqueId(), Integer.parseInt(params.replaceAll("[\\D]+", "")));
	}
	
	@Override
	public String onPlaceholderRequest(Player player, @NotNull String params) {
		return getValue(player.getUniqueId(), Integer.parseInt(params.replaceAll("[\\D]+", "")));
	}
	
}
