package de.redfox.steckbrief.commands;

import de.redfox.steckbrief.command.Command;

public class RoleplayCommand extends Command {
	
	public RoleplayCommand() {
		super("roleplay");
		register(new CreateCommand());
		register(new LeaveCommand());
	}
	
}
