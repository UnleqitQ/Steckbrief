package de.redfox.steckbrief.commands;

import de.redfox.steckbrief.CharacterManager;
import de.redfox.steckbrief.command.Command;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class ReloadCommand extends Command {
	
	public ReloadCommand() {
		super("reload");
	}
	
	@Override
	public void execute(CommandSender sender, String[] args) {
		CharacterManager.loadCharacters();
		CharacterManager.loadPlayers();
		sender.sendMessage(ChatColor.GREEN + "Successfully reloaded RP");
	}
	
}
