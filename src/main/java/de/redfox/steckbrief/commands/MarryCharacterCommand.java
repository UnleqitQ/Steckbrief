package de.redfox.steckbrief.commands;

import de.redfox.steckbrief.CharacterDescription;
import de.redfox.steckbrief.CharacterManager;
import de.redfox.steckbrief.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.permissions.PermissionDefault;

import java.util.ArrayList;
import java.util.List;

public class MarryCharacterCommand extends Command {
	
	public MarryCharacterCommand() {
		super("marry");
		setPermissionDefault(PermissionDefault.OP);
	}
	
	@Override
	public void execute(CommandSender sender, String[] args) {
		if (args.length == 4) {
			String firstname1 = args[0];
			String lastname1 = args[1];
			String name1 = firstname1.substring(0, 1).toUpperCase() + firstname1.substring(
					1).toLowerCase() + " " + lastname1.substring(0, 1).toUpperCase() + lastname1.substring(
					1).toLowerCase();
			if (CharacterManager.characterNames.containsKey(name1)) {
				CharacterDescription character1 = CharacterManager.characters.get(
						CharacterManager.characterNames.get(name1));
				String firstname2 = args[2];
				String lastname2 = args[3];
				String name2 = firstname2.substring(0, 1).toUpperCase() + firstname2.substring(
						1).toLowerCase() + " " + lastname2.substring(0, 1).toUpperCase() + lastname2.substring(
						1).toLowerCase();
				if (CharacterManager.characterNames.containsKey(name2)) {
					CharacterDescription character2 = CharacterManager.characters.get(
							CharacterManager.characterNames.get(name2));
					if (character1.married == null && character2.married == null) {
						if (character1.uuid.equals(character2.uuid)) {
							sender.sendMessage("You can't marry someone with him/her self");
							return;
						}
						character1.married = character2.uuid;
						character2.married = character1.uuid;
					}
					else {
						sender.sendMessage("Character is already married");
					}
				}
				else {
					sender.sendMessage("Character does not exist");
				}
			}
			else {
				sender.sendMessage("Character does not exist");
			}
		}
		else {
			sender.sendMessage(
					"Please use /roleplay marry <firstname1> <lastname1> <firstname2> <lastname2>");
		}
	}
	
	@Override
	public List<String> tab(CommandSender sender, String[] args) {
		List<String> l = new ArrayList<>();
		if (args.length == 1 || args.length == 3) {
			CharacterManager.characters.forEach((uuid, character) -> {
				if (character.firstname.toLowerCase().contains(args[args.length - 1].toLowerCase()))
					l.add(character.firstname + " " + character.lastname);
			});
		}
		if (args.length == 2 || args.length == 4) {
			CharacterManager.characters.forEach((uuid, character) -> {
				if (character.firstname.equalsIgnoreCase(
						args[args.length - 2]) && character.lastname.toLowerCase().contains(
						args[args.length - 1].toLowerCase()))
					l.add(character.lastname);
			});
		}
		return l;
	}
	
}
