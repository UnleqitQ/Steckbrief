package de.redfox.steckbrief.commands;

import de.redfox.steckbrief.CharacterDescription;
import de.redfox.steckbrief.CharacterManager;
import de.redfox.steckbrief.PlayerInformation;
import de.redfox.steckbrief.command.Command;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;

import java.util.*;

public class InfoPlayerCommand extends Command {
	
	public InfoPlayerCommand() {
		super("player");
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
		else {
			sender.sendMessage("Please use /roleplay info player <player>");
		}
	}
	
	@Override
	public List<String> tab(CommandSender sender, String[] args) {
		List<String> l = new ArrayList<>();
		if (args.length == 1) {
			Arrays.stream(Bukkit.getOfflinePlayers()).filter(
					player -> Objects.requireNonNullElse(player.getName(), "\n").toLowerCase().contains(
							args[0].toLowerCase())).forEach(player -> l.add(player.getName()));
		}
		return l;
	}
	
}
