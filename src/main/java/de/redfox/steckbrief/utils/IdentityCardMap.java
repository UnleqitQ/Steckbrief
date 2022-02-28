package de.redfox.steckbrief.utils;

import de.redfox.steckbrief.CharacterDescription;
import de.redfox.steckbrief.CharacterManager;
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
	long lastHeadUpdate = 0;
	BufferedImage image = null;
	long lastUpdate = 0;
	
	public IdentityCardMap(CharacterDescription character) {
		this.character = character;
	}
	
	private BufferedImage getHead() {
		if (System.currentTimeMillis() - lastHeadUpdate > 1000 * 60)
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
		g.drawImage(image, 0, 16, 32, 32, null);
	}
	
	public void drawLastName(Graphics2D g) {
		g.setColor(java.awt.Color.BLACK);
		g.drawString(character.lastname, 32, 28);
	}
	
	public void drawFirstName(Graphics2D g) {
		g.setColor(java.awt.Color.BLACK);
		g.drawString(character.firstname, 32, 48);
	}
	
	public void drawAge(Graphics2D g) {
		g.setColor(java.awt.Color.BLACK);
		g.drawString("Alter: " + character.getAge(), 8, 68);
	}
	
	public void drawSex(Graphics2D g) {
		g.setColor(java.awt.Color.BLACK);
		g.drawString("Geschlecht: " + character.sexuality.getDisplay(), 8, 88);
	}
	
	
	private void drawMarried(Graphics2D g) {
		if (character.married != null) {
			g.setColor(java.awt.Color.BLACK);
			g.drawString("Verheiratet: " + CharacterManager.characters.get(character.married).getName(), 8, 108);
		}
	}
	
	@Override
	public void render(@NotNull MapView map, @NotNull MapCanvas canvas, @NotNull Player player) {
		if (System.currentTimeMillis() - lastUpdate > 1000 * 10 || image == null) {
			image = new BufferedImage(128, 128, BufferedImage.TYPE_4BYTE_ABGR);
			Graphics2D g = image.createGraphics();
			g.setFont(new Font("", Font.PLAIN, 10));
			//drawBackground(g);
			drawHead(g);
			drawLastName(g);
			drawFirstName(g);
			drawAge(g);
			drawSex(g);
			drawMarried(g);
			/*if (!character.alive) {
				Random rnd = new Random(character.getName().hashCode());
				int x1 = rnd.nextInt(12) + 5;
				for (int i = 0; i < 12; i++) {
					int x2 = rnd.nextInt(12) + 5;
					if (i % 8 == 0)
						x2 = -x2;
					int y1 = i * 8 + 16;
					int y2 = y1 + 7;
					/*g.setColor(Color.WHITE);
					for (int j = 63; j < 66; j++) {
						g.drawLine(x1 + j, y1, x2 + j, y2);
					}/
					g.setColor(Color.BLACK);
					//g.drawLine(x1 + 66, y1, x2 + 66, y2);
					//g.drawLine(x1 + 62, y1, x2 + 62, y2);
					g.drawLine(x1 + 64, y1, x2 + 64, y2);
					x1 = x2;
				}
			}*/
			g.dispose();
			lastUpdate = System.currentTimeMillis();
		}
		canvas.drawImage(0, 0, image);
	}
	
	
}
