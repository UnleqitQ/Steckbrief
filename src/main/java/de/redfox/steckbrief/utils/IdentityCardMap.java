package de.redfox.steckbrief.utils;

import de.redfox.steckbrief.CharacterDescription;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.map.MapCanvas;
import org.bukkit.map.MapRenderer;
import org.bukkit.map.MapView;
import org.jetbrains.annotations.NotNull;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;

public class IdentityCardMap extends MapRenderer {
	//https://visage.surgeplay.com/head/%UUID_U%
	
	CharacterDescription character;
	BufferedImage head = null;
	long lastUpdate = 0;
	
	public IdentityCardMap(CharacterDescription character) {
		this.character = character;
	}
	
	private BufferedImage getHead() {
		if (System.currentTimeMillis() - lastUpdate > 1000 * 60)
			try {
				head = ImageIO.read(new URL("https://visage.surgeplay.com/head/" + character.player.toString()));
			} catch (IOException e) {
				Bukkit.getLogger().log(Level.WARNING, e.getMessage(), e);
			}
		return head;
	}
	
	public void drawBackground(Graphics2D g) {
		g.setColor(java.awt.Color.WHITE);
		g.fillRoundRect(0, 16, 128, 96, 16, 16);
	}
	
	public void drawHead(Graphics2D g) {
		BufferedImage image = getHead();
		g.drawImage(image, 0, 16, 43, 43, null);
	}
	
	public void drawLastName(Graphics2D g) {
		g.setColor(java.awt.Color.BLACK);
		g.drawString(character.lastname, 32, 28);
	}
	
	public void drawFirstName(Graphics2D g) {
		g.setColor(java.awt.Color.BLACK);
		g.drawString(character.firstname, 32, 52);
	}
	
	public void drawAge(Graphics2D g) {
		g.setColor(java.awt.Color.BLACK);
		g.drawString("Age: " + character.getAge(), 8, 76);
	}
	
	public void drawSex(Graphics2D g) {
		g.setColor(java.awt.Color.BLACK);
		g.drawString("Sex: " + character.sexuality.getDisplay(), 8, 100);
	}
	
	
	@Override
	public void render(@NotNull MapView map, @NotNull MapCanvas canvas, @NotNull Player player) {
		BufferedImage image = new BufferedImage(128, 128, BufferedImage.TYPE_4BYTE_ABGR);
		Graphics2D g = image.createGraphics();
		g.setFont(new Font("", Font.PLAIN, 10));
		drawBackground(g);
		drawHead(g);
		drawLastName(g);
		drawFirstName(g);
		drawAge(g);
		drawSex(g);
		g.dispose();
		canvas.drawImage(0, 0, image);
	}
	
	
}
