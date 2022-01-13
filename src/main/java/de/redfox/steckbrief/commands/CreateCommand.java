package de.redfox.steckbrief.commands;

import de.redfox.command.Command;
import de.redfox.steckbrief.CreationManager;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CreateCommand extends Command {
	
	public CreateCommand() {
		super("create");
	}
	
	@Override
	public void execute(CommandSender sender, String[] args) {
		if (sender instanceof Player) {
			Player player = (Player) sender;
			CreationManager.startCreation(player);
		}
		else {
			sender.sendMessage("Has to be used by a player");
		}
	}
	
}
