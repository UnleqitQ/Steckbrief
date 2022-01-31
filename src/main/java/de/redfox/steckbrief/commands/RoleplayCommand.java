package de.redfox.steckbrief.commands;

import de.redfox.steckbrief.command.Command;
import de.redfox.steckbrief.commands.edit.EditCommand;

public class RoleplayCommand extends Command {
	
	public RoleplayCommand() {
		super("rp");
		register(new CreateCommand());
		register(new LeaveCommand());
		register(new InfoCommand());
		register(new IdCardCommand());
		register(new IdMapCommand());
		register(new ReloadCommand());
		register(new EditCommand());
	}
	
}
