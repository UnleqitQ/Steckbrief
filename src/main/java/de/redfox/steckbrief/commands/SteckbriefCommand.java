package de.redfox.steckbrief.commands;

import de.redfox.steckbrief.command.Command;

public class SteckbriefCommand extends Command {
	
	public SteckbriefCommand() {
		super("steckbrief");
		register(new CreateCommand());
	}
	
}
