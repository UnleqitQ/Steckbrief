package de.redfox.steckbrief;

import com.google.gson.JsonObject;
import de.redfox.steckbrief.config.ConfigObject;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class CharacterDescription {
	
	public UUID player;
	public String firstname;
	public String lastname;
	public Sex sexuality;
	public int joinAge;
	public long firstJoin;
	public long deathTime;
	public boolean alive;
	
	public CharacterDescription() {
	}
	
	public enum Sex {
		MALE, FEMALE, DIVERSE, ATTACK_HELICOPTER
	}
	
	public void save(ConfigObject configObject) {
		JsonObject config = new JsonObject();
		config.addProperty("player", player.toString());
		config.addProperty("firstname", firstname);
		config.addProperty("lastname", lastname);
		config.addProperty("sexuality", sexuality.ordinal());
		config.addProperty("joinAge", joinAge);
		config.addProperty("firstJoin", firstJoin);
		config.addProperty("alive", alive);
		config.addProperty("deathTime", deathTime);
		configObject.rootSection.add(firstname + "_" + lastname, config);
	}
	
	@NotNull
	public static CharacterDescription load(@NotNull JsonObject config) {
		CharacterDescription characterDescription = new CharacterDescription();
		characterDescription.player = UUID.fromString(config.get("player").getAsString());
		characterDescription.firstname = config.get("player").getAsString();
		characterDescription.lastname = config.get("player").getAsString();
		characterDescription.sexuality = Sex.values()[config.get("player").getAsInt()];
		characterDescription.joinAge = config.get("player").getAsInt();
		characterDescription.firstJoin = config.get("player").getAsLong();
		characterDescription.deathTime = config.get("player").getAsLong();
		characterDescription.alive = config.get("player").getAsBoolean();
		return characterDescription;
	}
	
}
