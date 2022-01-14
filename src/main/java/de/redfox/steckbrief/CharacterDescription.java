package de.redfox.steckbrief;

import com.google.gson.JsonObject;
import de.redfox.steckbrief.manager.config.ConfigObject;
import de.redfox.steckbrief.utils.IdentityCardMap;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.MapMeta;
import org.bukkit.map.MapView;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class CharacterDescription {
	
	public static final NamespacedKey cardKey = new NamespacedKey(Steckbrief.getInstance(), "character");
	
	public final UUID uuid;
	public UUID player;
	public String firstname;
	public String lastname;
	public Sex sexuality;
	public int joinAge;
	public long firstJoin;
	public long deathTime;
	public boolean alive;
	public MapView mapView;
	
	public CharacterDescription(UUID uuid) {
		this.uuid = uuid;
	}
	
	public MapView getMapView() {
		if (mapView == null)
			mapView = Bukkit.createMap(Bukkit.getWorlds().get(0));
		return mapView;
	}
	
	public void updateMapView() {
		getMapView().getRenderers().forEach(mapRenderer -> mapView.removeRenderer(mapRenderer));
		getMapView().addRenderer(new IdentityCardMap(this));
	}
	
	public enum Sex {
		MALE("Male"), FEMALE("Female"), DIVERSE("Diverse"), ATTACK_HELICOPTER("Attack Helicopter");
		
		private final String display;
		
		private Sex(String display) {
			this.display = display;
		}
		
		public String getDisplay() {
			return display;
		}
	}
	
	public String getName() {
		return firstname + " " + lastname;
	}
	
	public int[] getTimeSinceJoin() {
		long current = System.currentTimeMillis();
		long difference = current - firstJoin;
		int totalSeconds = (int) (difference / 1000);
		int totalMinutes = totalSeconds / 60;
		int totalHours = totalMinutes / 60;
		int days = totalHours / 24;
		int seconds = totalSeconds % 60;
		int minutes = totalMinutes % 60;
		int hours = totalHours % 24;
		return new int[]{seconds, minutes, hours, days, totalSeconds, totalMinutes, totalHours};
	}
	
	public int[] getTimeDeathJoin() {
		long current = deathTime;
		long difference = current - firstJoin;
		int totalSeconds = (int) (difference / 1000);
		int totalMinutes = totalSeconds / 60;
		int totalHours = totalMinutes / 60;
		int days = totalHours / 24;
		int seconds = totalSeconds % 60;
		int minutes = totalMinutes % 60;
		int hours = totalHours % 24;
		return new int[]{seconds, minutes, hours, days, totalSeconds, totalMinutes, totalHours};
	}
	
	public int getAge() {
		int[] difference;
		if (alive)
			difference = getTimeSinceJoin();
		else
			difference = getTimeDeathJoin();
		return joinAge + difference[3] / 28;
	}
	
	public void save() {
		CharacterManager.saveCharacter(uuid);
	}
	
	public void save(@NotNull ConfigObject configObject) {
		JsonObject config = new JsonObject();
		config.addProperty("uuid", uuid.toString());
		config.addProperty("player", player.toString());
		config.addProperty("firstname", firstname);
		config.addProperty("lastname", lastname);
		config.addProperty("sexuality", sexuality.ordinal());
		config.addProperty("joinAge", joinAge);
		config.addProperty("firstJoin", firstJoin);
		config.addProperty("alive", alive);
		config.addProperty("deathTime", deathTime);
		config.addProperty("map", mapView.getId());
		configObject.rootSection.add(uuid.toString(), config);
	}
	
	@NotNull
	public static CharacterDescription load(@NotNull JsonObject config) {
		CharacterDescription characterDescription = new CharacterDescription(
				UUID.fromString(config.get("uuid").getAsString()));
		characterDescription.player = UUID.fromString(config.get("player").getAsString());
		characterDescription.firstname = config.get("firstname").getAsString();
		characterDescription.lastname = config.get("lastname").getAsString();
		characterDescription.sexuality = Sex.values()[config.get("sexuality").getAsInt()];
		characterDescription.joinAge = config.get("joinAge").getAsInt();
		characterDescription.firstJoin = config.get("firstJoin").getAsLong();
		characterDescription.deathTime = config.get("deathTime").getAsLong();
		characterDescription.alive = config.get("alive").getAsBoolean();
		characterDescription.mapView = Bukkit.getMap(config.get("map").getAsInt());
		return characterDescription;
	}
	
	public List<String> getDescription(boolean admin) {
		List<String> l = new ArrayList<>();
		if (admin) {
			l.add("UUID: " + uuid.toString());
			l.add("Player UUID: " + player.toString());
			l.add("Player: " + Bukkit.getOfflinePlayer(player).getName());
		}
		l.add("Name: " + getName());
		l.add("Sex: " + sexuality.getDisplay());
		l.add("Age: " + getAge());
		SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
		if (admin) {
			l.add("Created: " + format.format(new Date(firstJoin)));
		}
		if (!alive) {
			if (admin)
				l.add("RT Death: " + format.format(new Date(deathTime)));
			l.add("Death: " + format.format(new Date(deathTime)));
		}
		return l;
	}
	
	public ItemStack updateIdentityCard0(ItemStack item) {
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(ChatColor.DARK_PURPLE + "Identity Card");
		meta.setLore(getDescription(false));
		meta.getPersistentDataContainer().set(cardKey, PersistentDataType.STRING, uuid.toString());
		item.setItemMeta(meta);
		return item;
	}
	
	public ItemStack getIdentityCard() {
		return updateIdentityCard(new ItemStack(Material.PAPER));
	}
	
	public ItemStack updateIdentityCard(ItemStack item) {
		ItemMeta meta = item.getItemMeta();
		
		meta.setDisplayName(ChatColor.DARK_PURPLE + "Identity Card");
		meta.setLore(getDescription(false));
		meta.getPersistentDataContainer().set(cardKey, PersistentDataType.STRING, uuid.toString());
		item.setItemMeta(meta);
		return item;
	}
	
	public ItemStack getIdentityMap() {
		ItemStack map = new ItemStack(Material.FILLED_MAP);
		MapMeta meta = (MapMeta) map.getItemMeta();
		meta.setMapView(mapView);
		meta.getPersistentDataContainer().set(cardKey, PersistentDataType.STRING, uuid.toString());
		map.setItemMeta(meta);
		return map;
	}
	
}
