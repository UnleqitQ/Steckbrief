package de.redfox.steckbrief;

import com.google.gson.JsonObject;
import de.redfox.steckbrief.manager.config.ConfigManager;
import de.redfox.steckbrief.tobemoved.FirstJoinSession;
import de.redfox.steckbrief.tobemoved.MessageQueue;
import de.redfox.steckbrief.tobemoved.TitleData;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

import java.util.Map;
import java.util.UUID;

public class CreationInstance {
	
	private static final JsonObject selectedLang;
	
	static {
		selectedLang = ConfigManager.registerCharacterCreationMessages().rootSection.get(
				"CharacterCreation").getAsJsonObject();
	}
	
	public Player player;
	public CharacterDescription description;
	
	private Step currentStep;
	
	private final MessageQueue messageQueue;
	
	
	public CreationInstance(Player player) {
		messageQueue = new MessageQueue(player);
		messageQueue.add(new TitleData(selectedLang.get("firstname").getAsString(), "", 1000, 0)).add(
				new TitleData(selectedLang.get("lastname").getAsString(), "", 1000, 0)).add(
				new TitleData(selectedLang.get("gender").getAsString(), "", 1000, 0)).add(
				new TitleData(selectedLang.get("age").getAsString(), "", 1000, 0));
		
		this.player = player;
		description = new CharacterDescription(UUID.randomUUID());
		description.player = player.getUniqueId();
		description.alive = true;
		currentStep = Step.START;
		nextStep();
	}
	
	public void preInit() {
		player.setGameMode(GameMode.SPECTATOR);
		player.setFlying(true);
		player.teleport(FirstJoinSession.startLoc);
		player.sendMessage(ChatColor.RED.toString() + ChatColor.BOLD + selectedLang.get("name_warning").getAsString());
		player.sendMessage("");
		player.sendMessage("");
		player.sendMessage("");
		player.sendMessage("");
		player.sendMessage(ChatColor.RED.toString() + ChatColor.BOLD + selectedLang.get("name_warning").getAsString());
	}
	
	public void finish() {
		player.setGameMode(GameMode.SURVIVAL);
		player.setFlying(false);
		player.teleport(FirstJoinSession.spawnLoc);
	}
	
	public void input(String input) {
		switch (currentStep) {
			case FIRSTNAME -> {
				input = input.replaceAll("\\W", "");
				if (input.length() < 3) {
					player.sendMessage(selectedLang.get("err_invalid_firstname").getAsString());
					return;
				}
				if (input.length() > 20) {
					player.sendMessage(selectedLang.get("err_invalid_first").getAsString());
					return;
				}
				description.firstname = input.substring(0, 1).toUpperCase() + input.substring(1).toLowerCase();
			}
			case LASTNAME -> {
				input = input.replaceAll("\\W", "");
				if (input.length() < 3) {
					player.sendMessage(selectedLang.get("err_invalid_lastname").getAsString());
					return;
				}
				if (input.length() > 20) {
					player.sendMessage(selectedLang.get("err_invalid_lastname").getAsString());
					return;
				}
				description.lastname = input.substring(0, 1).toUpperCase() + input.substring(1).toLowerCase();
				if (CharacterManager.characterNames.containsKey(description.getName())) {
					player.sendMessage(selectedLang.get("err_name_exists").getAsString());
					player.sendMessage(selectedLang.get("err_restart").getAsString());
					currentStep = Step.START;
				}
			}
			case SEX -> {
				input = input.replaceAll("\\W", "");
				try {
					description.sexuality = CharacterDescription.Sex.valueOf(input.toUpperCase());
				} catch (IllegalArgumentException e) {
					player.sendMessage(selectedLang.get("err_invalid_gender").getAsString());
					return;
				}
			}
			case AGE -> {
				try {
					int val = Integer.parseInt(input);
					if (val < 4 || val > 100) {
						player.sendMessage(selectedLang.get("err_invalid_age").getAsString());
						return;
					}
					description.joinAge = val;
				} catch (NumberFormatException e) {
					player.sendMessage(selectedLang.get("err_invalid_age").getAsString());
					return;
				}
			}
			case CONFIRM -> {
			
			}
		}
		nextStep();
	}
	
	public void nextStep() {
		messageQueue.sendNext();
		Step next = currentStep.getNextStep();
		if (next == null) {
			CreationManager.instances.remove(player);
			Bukkit.getScheduler().runTask(Steckbrief.getInstance(), () -> {
				finish();
				save();
			});
			return;
		}
		currentStep = next;
		switch (currentStep) {
			case FIRSTNAME -> player.sendMessage(selectedLang.get("firstname").getAsString());
			case LASTNAME -> player.sendMessage(selectedLang.get("lastname").getAsString());
			case SEX -> player.sendMessage(selectedLang.get("gender").getAsString());
			case AGE -> player.sendMessage(selectedLang.get("age").getAsString());
		}
	}
	
	public void save() {
		description.firstJoin = System.currentTimeMillis();
		CharacterManager.characters.put(description.uuid, description);
		CharacterManager.characterNames.put(description.getName(), description.uuid);
		CharacterManager.saveCharacter(description.uuid);
		CharacterManager.setPlayerCharacter(player.getUniqueId(), description.uuid);
		CharacterManager.savePlayer(player.getUniqueId());
		ConfigManager.characters.save();
		ConfigManager.players.save();
		
		//description.updateMapView();
		player.sendMessage(selectedLang.get("character_created").getAsString());
		
		FirstJoinSession firstJoinSession = FirstJoinSession.activeSessions.get(player);
		if (firstJoinSession != null)
			firstJoinSession.stop();
		Bukkit.getScheduler().runTask(Steckbrief.getInstance(), description::updateMapView);
		new Thread(() -> {
			try {
				Thread.sleep(400);
			} catch (InterruptedException ignored) {
			}
			Bukkit.getScheduler().runTask(Steckbrief.getInstance(),
					() -> player.kickPlayer(selectedLang.get("character_rejoin").getAsString()));
		}).start();
	}
	
	public static Map<String, Map<String, String>> getMessages() {
		return Map.of("en", Map.ofEntries(Map.entry("firstname", "Enter your firstname"),
						Map.entry("lastname", "Enter your lastname"), Map.entry("gender", "Enter your gender"),
						Map.entry("age", "Enter your age"),
						Map.entry("err_invalid_firstname", "Please enter a valid lirstname (At least 3 characters)"),
						Map.entry("err_invalid_lastname", "Please enter a valid lastname (At least 3 characters)"),
						Map.entry("err_invalid_gender",
								"Please enter a valid gender (Male=Männlich, Female=Weiblich, Diverse=Divers)"),
						Map.entry("err_invalid_age", "Please enter a valid age (4-100)"),
						Map.entry("err_name_exists", "This name already exists"),
						Map.entry("err_restart", "Please redo the creation of your character"),
						Map.entry("name_warning",
								"Players with offensive oder illegal names will be banned and the information will maybe be sent to the police"),
						Map.entry("character_rejoin", "Please rejoin to finish the creation"),
						Map.entry("character_created", "Your character was created")),
				"de", Map.ofEntries(Map.entry("firstname", "Wähle deinen Vornamen"),
						Map.entry("lastname", "Wähle deinen Nachnamen"),
						Map.entry("gender", "Wähle dein Geschlecht"),
						Map.entry("age", "Wähle dein Alter"),
						Map.entry("err_invalid_firstname", "Der Vorname ist ungültig (Mindestens 3 Zeichen)"),
						Map.entry("err_invalid_lastname", "Der Nachname ist ungültig (Mindestens 3 Zeichen)"),
						Map.entry("err_invalid_gender", "Das Geschlecht ist ungültig (Männlich oder Weiblich)"),
						Map.entry("err_invalid_age", "Das Alter ist ungültig (4 bis 100)"),
						Map.entry("err_name_exists", "Der Name ist derzeit in Benutzung. Wähle bitte einen anderen."),
						Map.entry("err_restart", "Es ist uns ein Fehler unterlaufen. Bitte wiederhole die Erstellung des Charakters."),
						Map.entry("name_warning",
								"Spieler mit beleidigenden oder illegalen Namen werden gebannt und die Informationen ggf. an die Strafverfolgungsbehörden weiter gegeben."),
						Map.entry("character_rejoin", "Du wurdest gekickt, damit dein Name für die Zukunft geladen ist. Du darfst den Server nun wieder betreten."),
						Map.entry("character_created", "Dein Charakter wurde erfolgreich erstellt.")));
	}
	
	public enum Step {
		CONFIRM(null), AGE(null), SEX(AGE), LASTNAME(SEX), FIRSTNAME(LASTNAME), START(FIRSTNAME);
		
		private final Step nextStep;
		
		Step(Step nextStep) {
			this.nextStep = nextStep;
		}
		
		public Step getNextStep() {
			return nextStep;
		}
	}
	
}
