package de.redfox.steckbrief;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CharacterManager {
	
	public static Map<UUID, CharacterDescription> characters = new HashMap<>();
	
	public static Map<UUID, PlayerInformation> players = new HashMap<>();
	
}
