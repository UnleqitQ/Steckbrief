package de.redfox.steckbrief.commands;

import de.redfox.steckbrief.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class LeaveCommand extends Command {
	
	public LeaveCommand() {
		super("leave");
	}
	
	@Override
	public void execute(CommandSender sender, String[] args) {
		if (sender instanceof Player) {
			Player player = (Player) sender;
			player.setCustomNameVisible(false);
			player.setCustomName(player.getName());
			player.setPlayerListName(player.getName());
			player.setDisplayName(player.getName());
		}
		else {
			sender.sendMessage("Has to be used by a player");
		}
	}
	
}
