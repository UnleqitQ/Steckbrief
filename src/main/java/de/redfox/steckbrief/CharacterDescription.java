package de.redfox.steckbrief;

import com.google.gson.JsonObject;
import de.redfox.steckbrief.manager.config.ConfigObject;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class CharacterDescription {
	
	public final UUID uuid;
	public UUID player;
	public String firstname;
	public String lastname;
	public Sex sexuality;
	public int joinAge;
	public long firstJoin;
	public long deathTime;
	public boolean alive;
	
	public CharacterDescription(UUID uuid) {
		this.uuid = uuid;
	}
	
	public enum Sex {
		MALE, FEMALE, DIVERSE, ATTACK_HELICOPTER
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
		return characterDescription;
	}
	
}
