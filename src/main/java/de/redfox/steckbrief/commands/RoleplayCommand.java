package de.redfox.steckbrief.commands;

import de.redfox.steckbrief.command.Command;
import de.redfox.steckbrief.commands.admin.AdminCommand;

public class RoleplayCommand extends Command {
	
	public RoleplayCommand() {
		super("rp");
		register(new AdminCommand());
		register(new LeaveCommand());
		register(new InfoCommand());
		register(new KillCharacterCommand());
		register(new MarryCharacterCommand());
		register(new DivorceCharacterCommand());
	}
	
}
