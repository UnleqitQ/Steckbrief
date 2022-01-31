package de.redfox.steckbrief.commands.edit;

import de.redfox.steckbrief.command.Command;
import de.redfox.steckbrief.commands.edit.character.CharacterCommand;

public class EditCommand extends Command {
	
	public EditCommand() {
		super("edit");
		register(new CharacterCommand());
	}
	
}
