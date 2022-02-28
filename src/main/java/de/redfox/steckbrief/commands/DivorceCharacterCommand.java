package de.redfox.steckbrief.commands;

import de.redfox.steckbrief.CharacterDescription;
import de.redfox.steckbrief.CharacterManager;
import de.redfox.steckbrief.command.Command;
import de.redfox.steckbrief.manager.config.ConfigManager;
import org.bukkit.command.CommandSender;
import org.bukkit.permissions.PermissionDefault;

import java.util.ArrayList;
import java.util.List;

public class DivorceCharacterCommand extends Command {
	
	public DivorceCharacterCommand() {
		super("divorce");
		setPermissionDefault(PermissionDefault.OP);
	}
	
	@Override
	public void execute(CommandSender sender, String[] args) {
		if (args.length == 2) {
			String firstname1 = args[0];
			String lastname1 = args[1];
			String name1 = firstname1.substring(0, 1).toUpperCase() + firstname1.substring(
					1).toLowerCase() + " " + lastname1.substring(0, 1).toUpperCase() + lastname1.substring(
					1).toLowerCase();
			if (CharacterManager.characterNames.containsKey(name1)) {
				CharacterDescription character1 = CharacterManager.characters.get(
						CharacterManager.characterNames.get(name1));
				if (character1.married != null) {
					CharacterDescription character2 = CharacterManager.characters.get(character1.married);
					character1.married = null;
					character2.married = null;
					character1.save();
					character2.save();
					ConfigManager.characters.save();
					sender.sendMessage("Divorced " + character1.getName() + " and " + character2.getName());
				}
				else {
					sender.sendMessage("Character ist nicht verheiratet");
				}
			}
			else {
				sender.sendMessage("Character existiert nicht");
			}
		}
		else {
			sender.sendMessage("Please use /roleplay divorce <firstname> <lastname>");
		}
	}
	
	@Override
	public List<String> tab(CommandSender sender, String[] args) {
		List<String> l = new ArrayList<>();
		if (args.length == 1) {
			CharacterManager.characters.forEach((uuid, character) -> {
				if (character.firstname.toLowerCase().contains(args[args.length - 1].toLowerCase()))
					l.add(character.firstname + " " + character.lastname);
			});
		}
		if (args.length == 2) {
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
