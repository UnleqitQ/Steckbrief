package de.redfox.steckbrief.commands.admin;

import de.redfox.steckbrief.command.Command;

public class AdminCommand extends Command {
	
	public AdminCommand() {
		super("admin");
		register(new SpawnLocationCommand());
	}
	
}
