package de.redfox.steckbrief.commands.edit.character;

import de.redfox.steckbrief.CharacterDescription;
import de.redfox.steckbrief.CharacterManager;
import de.redfox.steckbrief.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionDefault;

import java.util.ArrayList;
import java.util.List;

public class KillCharacterCommand extends Command {
	
	public KillCharacterCommand() {
		super("kill");
		setPermissionDefault(PermissionDefault.OP);
	}
	
	@Override
	public void execute(CommandSender sender, String[] args) {
		if (sender instanceof Player) {
			if (args.length == 2) {
				String firstname = args[0];
				String lastname = args[1];
				String name = firstname.substring(0, 1).toUpperCase() + firstname.substring(
						1).toLowerCase() + " " + lastname.substring(0, 1).toUpperCase() + lastname.substring(
						1).toLowerCase();
				if (CharacterManager.characterNames.containsKey(name)) {
					CharacterDescription character = CharacterManager.characters.get(
							CharacterManager.characterNames.get(name));
					character.onDeath();
				}
				else {
					sender.sendMessage("Character does not exist");
				}
			}
			else {
				sender.sendMessage("Please use /roleplay edit character kill <firstname> <lastname>");
			}
		}
		else {
			sender.sendMessage("This command can only be used by a player");
		}
	}
	
	@Override
	public List<String> tab(CommandSender sender, String[] args) {
		List<String> l = new ArrayList<>();
		if (args.length == 1) {
			CharacterManager.characters.forEach((uuid, character) -> {
				if (character.firstname.toLowerCase().contains(args[0].toLowerCase()))
					l.add(character.firstname + " " + character.lastname);
			});
		}
		if (args.length == 2) {
			CharacterManager.characters.forEach((uuid, character) -> {
				if (character.firstname.equalsIgnoreCase(args[0]) && character.lastname.toLowerCase().contains(
						args[1].toLowerCase()))
					l.add(character.lastname);
			});
		}
		return l;
	}
	
}
