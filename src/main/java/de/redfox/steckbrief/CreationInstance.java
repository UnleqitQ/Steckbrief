package de.redfox.steckbrief;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.UUID;

public class CreationInstance {
	
	public Player player;
	public CharacterDescription description;
	private Step currentStep;
	
	public CreationInstance(Player player) {
		this.player = player;
		description = new CharacterDescription(UUID.randomUUID());
		currentStep = Step.FIRSTNAME;
	}
	
	public void step(String input) {
		switch (currentStep) {
			case FIRSTNAME: {
				input = input.replaceAll("\\W", "");
				if (input.length() < 3) {
					player.sendMessage(ChatColor.RED + "Please Type in a valid Firstname (At least 3 characters)");
				}
				if (input.length() > 20) {
					player.sendMessage(ChatColor.RED + "Please Type in a valid Firstname (At most 20 characters)");
				}
				description.firstname = input.substring(0, 0).toUpperCase() + input.substring(1).toLowerCase();
			}
			break;
			case LASTNAME: {
			
			}
			break;
			case SEX: {
			
			}
			break;
			case AGE: {
			
			}
			break;
			case CONFIRM: {
			
			}
			break;
		}
	}
	
	public enum Step {
		FIRSTNAME, LASTNAME, SEX, AGE, CONFIRM
	}
	
}
