package de.redfox.command;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;

import java.util.*;


public abstract class Command implements TabExecutor {
	
	String name;
	Map<String, Command> subCommands;
	String savedPath;
	
	public Command(String name) {
		this.name = name;
		savedPath = "";
		subCommands = new HashMap<>();
	}
	
	public Command(String name, String savedPath) {
		this.name = name;
		this.savedPath = savedPath + " ";
		subCommands = new HashMap<>();
	}
	
	
	public String getName() {
		return name;
	}
	
	public void setSavedPath(String savedPath) {
		this.savedPath = savedPath;
	}
	
	public void execute(CommandSender sender, String[] args) {
		sendHelp(sender);
	}
	
	public List<String> tab(CommandSender sender, String[] args) {
		return new ArrayList<>();
	}
	
	private void onCommand(CommandSender sender, String[] args) {
		if (subCommands.size() == 0) {
			execute(sender, args);
		}
		else {
			if (args.length == 0) {
				execute(sender, args);
			}
			else {
				if (subCommands.containsKey(args[0])) {
					String[] args0 = new String[args.length - 1];
					if (args0.length > 0) {
						System.arraycopy(args, 1, args0, 0, args0.length);
					}
					subCommands.get(args[0]).onCommand(sender, args0);
				}
				else {
					sender.sendMessage(ChatColor.RED + "Syntaxerror");
				}
			}
		}
	}
	
	private List<String> onTabComplete(CommandSender sender, String[] args) {
		List<String> l = new ArrayList<>();
		if (args.length == 0 || subCommands.size() == 0) {
			l.addAll(tab(sender, args));
			return l;
		}
		if (args.length < 2 && subCommands.size() > 0) {
			for (String string : subCommands.keySet()) {
				if (string.toLowerCase().contains(args[0].toLowerCase())) {
					l.add(string);
				}
			}
		}
		if (args.length > 0 && subCommands.size() > 0) {
			if (subCommands.containsKey(args[0])) {
				String[] args0 = new String[args.length - 1];
				if (args0.length > 0) {
					System.arraycopy(args, 1, args0, 0, args0.length);
				}
				l.addAll(subCommands.get(args[0]).onTabComplete(sender, args0));
			}
		}
		return l;
	}
	
	@Override
	public List<String> onTabComplete(CommandSender sender, org.bukkit.command.Command command, String label,
		String[] args) {
		return onTabComplete(sender, args);
	}
	
	@Override
	public boolean onCommand(CommandSender sender, org.bukkit.command.Command command, String label, String[] args) {
		onCommand(sender, args);
		return false;
	}
	
	public void register(Command command) {
		subCommands.put(command.name, command);
		command.savedPath = savedPath + name + " ";
	}
	
	public List<String> help(String path, int tab) {
		return Collections.singletonList(ChatColor.GOLD + "  ".repeat(tab) + path + name);
	}
	
	private List<String> getHelp(String path, int tab) {
		ArrayList<String> s = new ArrayList<>();
		s.addAll(help(path, tab));
		for (Command command : subCommands.values()) {
			s.addAll(command.getHelp(path + name + " ", tab + 1));
		}
		if (subCommands.size() > 0) {
			s.add("");
		}
		return s;
	}
	
	public List<String> getHelp() {
		return getHelp("/" + savedPath, 0);
	}
	
	public void sendHelp(CommandSender sender) {
		for (String line : getHelp()) {
			sender.sendMessage(line);
		}
	}
	
}
