package de.redfox.steckbrief.commands;

import de.redfox.steckbrief.CharacterDescription;
import de.redfox.steckbrief.CharacterManager;
import de.redfox.steckbrief.PlayerInformation;
import de.redfox.steckbrief.command.Command;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;

import java.util.*;

public class InfoCharacterCommand extends Command {
	
	public InfoCharacterCommand() {
		super("character");
	}
	
	@Override
	public void execute(CommandSender sender, String[] args) {
		if (args.length == 2) {
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
				for (String line : character.getDescription(sender.hasPermission(getParent().getPerm()+".admin"))) {
					sender.sendMessage(" ".repeat(4) + line);
				}
				sender.sendMessage("");
				sender.sendMessage("-".repeat(20));
			}
			else {
				sender.sendMessage("Character existiert nicht");
			}
		}
		else {
			sender.sendMessage("Please use /roleplay info character <firstname> <lastname>");
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
