package de.redfox.steckbrief.commands;

import de.redfox.steckbrief.CharacterDescription;
import de.redfox.steckbrief.CharacterManager;
import de.redfox.steckbrief.PlayerInformation;
import de.redfox.steckbrief.command.Command;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;

import java.util.Arrays;
import java.util.UUID;

public class InfoCommand extends Command {
	
	public InfoCommand() {
		super("info");
	}
	
	@Override
	public void execute(CommandSender sender, String[] args) {
		if (args.length == 1) {
			String name = args[0];
			OfflinePlayer player = Arrays.stream(Bukkit.getOfflinePlayers()).filter(
					player0 -> player0.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
			if (player != null) {
				PlayerInformation information = CharacterManager.getPlayer(player.getUniqueId());
				sender.sendMessage("-".repeat(20));
				sender.sendMessage("");
				for (UUID characterUuid : information.characters) {
					CharacterDescription character = CharacterManager.characters.get(characterUuid);
					sender.sendMessage(character.getName() + ":");
					for (String line : character.getDescription(true)) {
						sender.sendMessage(" ".repeat(4) + line);
					}
					sender.sendMessage("");
				}
				sender.sendMessage("-".repeat(20));
			}
			else {
				sender.sendMessage("Player does not exist");
			}
		}
		else if (args.length == 2) {
			String firstname = args[0];
			String lastname = args[1];
			String name = firstname.substring(0, 1).toUpperCase() + firstname.substring(
					1).toLowerCase() + " " + lastname.substring(0, 1).toUpperCase() + lastname.substring(
					1).toLowerCase();
			if (CharacterManager.characterNames.containsKey(name)) {
				CharacterDescription character = CharacterManager.characters.get(
						CharacterManager.characterNames.get(name));
				sender.sendMessage("-".repeat(20));
				sender.sendMessage("");
				sender.sendMessage(character.getName() + ":");
				for (String line : character.getDescription(true)) {
					sender.sendMessage(" ".repeat(4) + line);
				}
				sender.sendMessage("");
				sender.sendMessage("-".repeat(20));
			}
			else {
				sender.sendMessage("Character does not exist");
			}
		}
		else {
			sender.sendMessage("Please use /roleplay info <player> or");
			sender.sendMessage("Please use /roleplay info <firstname> <lastname>");
		}
	}
	
}
