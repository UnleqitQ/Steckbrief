package de.redfox.steckbrief.commands.admin;

import de.redfox.steckbrief.command.Command;
import org.bukkit.permissions.PermissionDefault;

public class AdminCommand extends Command {
	
	public AdminCommand() {
		super("admin");
		setPermissionDefault(PermissionDefault.OP);
		register(new SpawnLocationCommand());
	}
	
}
