package de.redfox.steckbrief;

import java.util.UUID;

public class CharacterDescription {
	
	public UUID player;
	public String firstname;
	public String lastname;
	public Sex sexuality;
	public int joinAge;
	public long firstJoin;
	public boolean alive;
	
	public CharacterDescription(UUID player, String firstname, String lastname, Sex sexuality, int joinAge, long firstJoin, boolean alive) {
		this.player = player;
		this.firstname = firstname;
		this.lastname = lastname;
		this.sexuality = sexuality;
		this.joinAge = joinAge;
		this.firstJoin = firstJoin;
		this.alive = alive;
	}
	
	public enum Sex {
		MALE, FEMALE, DIVERSE, ATTACK_HELICOPTER
	}
	
}
