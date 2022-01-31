package de.redfox.steckbrief.commands.edit.character;

import de.redfox.steckbrief.command.Command;

public class CharacterCommand extends Command {
	
	public CharacterCommand() {
		super("character");
		register(new KillCharacterCommand());
		register(new MarryCharacterCommand());
	}
	
}
