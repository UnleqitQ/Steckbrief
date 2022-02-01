package de.redfox.steckbrief.commands.admin;

import de.redfox.steckbrief.CharacterManager;
import de.redfox.steckbrief.Steckbrief;
import de.redfox.steckbrief.command.Command;
import de.redfox.steckbrief.manager.config.ConfigManager;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class ReloadCommand extends Command {
	
	public ReloadCommand() {
		super("reload");
	}
	
	@Override
	public void execute(CommandSender sender, String[] args) {
		ConfigManager.init();
		CharacterManager.loadCharacters();
		CharacterManager.loadPlayers();
		Steckbrief.getInstance().rlc();
		sender.sendMessage(ChatColor.GREEN + "Successfully reloaded RP");
	}
	
}
