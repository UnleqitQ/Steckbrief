package de.redfox.steckbrief;

import de.redfox.steckbrief.config.ConfigManager;
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
		description.player = player.getUniqueId();
		currentStep = Step.START;
		nextStep();
	}
	
	public void input(String input) {
		switch (currentStep) {
			case FIRSTNAME: {
				input = input.replaceAll("\\W", "");
				if (input.length() < 3) {
					player.sendMessage(ChatColor.RED + "Please Type in a valid Firstname (At least 3 characters)");
					return;
				}
				if (input.length() > 20) {
					player.sendMessage(ChatColor.RED + "Please Type in a valid Firstname (At most 20 characters)");
					return;
				}
				description.firstname = input.substring(0, 0).toUpperCase() + input.substring(1).toLowerCase();
			}
			break;
			case LASTNAME: {
				input = input.replaceAll("\\W", "");
				if (input.length() < 3) {
					player.sendMessage(ChatColor.RED + "Please Type in a valid Lastname (At least 3 characters)");
					return;
				}
				if (input.length() > 20) {
					player.sendMessage(ChatColor.RED + "Please Type in a valid Lastname (At most 20 characters)");
					return;
				}
				description.lastname = input.substring(0, 0).toUpperCase() + input.substring(1).toLowerCase();
			}
			break;
			case SEX: {
				input = input.replaceAll("\\W", "");
				try {
					description.sexuality = CharacterDescription.Sex.valueOf(input.toUpperCase());
				} catch (IllegalArgumentException e) {
					player.sendMessage(ChatColor.RED + "Please Type in a valid sexuality (Male, Female, Diverse)");
					return;
				}
			}
			break;
			case AGE: {
				try {
					int val = Integer.parseInt(input);
					if (val < 4 || val > 100) {
						player.sendMessage(ChatColor.RED + "Please Type in a valid age (4-100)");
						return;
					}
					description.joinAge = val;
				} catch (NumberFormatException e) {
					player.sendMessage(ChatColor.RED + "Please Type in a valid age (4-100)");
					return;
				}
			}
			break;
			case CONFIRM: {
			
			}
			break;
		}
		nextStep();
	}
	
	public void nextStep() {
		Step next = currentStep.getNextStep();
		if (next == null) {
			save();
			CreationManager.instances.remove(player);
			return;
		}
		currentStep = next;
		switch (currentStep) {
			case FIRSTNAME -> player.sendMessage(ChatColor.GREEN + "Please type in your Firstname");
			case LASTNAME -> player.sendMessage(ChatColor.GREEN + "Please type in your Lastname");
			case SEX -> player.sendMessage(ChatColor.GREEN + "Please type in your Sexuality (Male, Female, Diverse)");
			case AGE -> player.sendMessage(ChatColor.GREEN + "Please type in your Age");
		}
	}
	
	public void save() {
		CharacterManager.characters.put(description.uuid, description);
		CharacterManager.saveCharacter(description.uuid);
		CharacterManager.setPlayerCharacter(player.getUniqueId(), description.uuid);
		CharacterManager.savePlayer(player.getUniqueId());
		ConfigManager.characters.save();
		ConfigManager.players.save();
	}
	
	public enum Step {
		CONFIRM(null),
		AGE(null),
		SEX(AGE),
		LASTNAME(SEX),
		FIRSTNAME(LASTNAME),
		START(FIRSTNAME);
		
		private final Step nextStep;
		
		private Step(Step nextStep) {
			this.nextStep = nextStep;
		}
		
		public Step getNextStep() {
			return nextStep;
		}
	}
	
}
