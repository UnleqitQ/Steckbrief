package de.redfox.steckbrief.utils;

import de.redfox.steckbrief.CharacterDescription;
import de.redfox.steckbrief.CharacterManager;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.MapMeta;
import org.bukkit.map.MapCanvas;
import org.bukkit.map.MapRenderer;
import org.bukkit.map.MapView;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;

public class IdentityCardMap extends MapRenderer implements MapView {
	//https://visage.surgeplay.com/head/%UUID_U%
	
	CharacterDescription character;
	BufferedImage head = null;
	long lastUpdate = System.currentTimeMillis();
	
	public IdentityCardMap(CharacterDescription character) {
		this.character = character;
	}
	
	public static void update(@NotNull ItemStack item) {
		if (item.getType() != Material.FILLED_MAP)
			return;
		ItemMeta meta = item.getItemMeta();
		if (meta == null)
			return;
		if (!meta.getPersistentDataContainer().has(CharacterDescription.cardKey, PersistentDataType.STRING))
			return;
		UUID characterUuid = UUID.fromString(
				meta.getPersistentDataContainer().get(CharacterDescription.cardKey, PersistentDataType.STRING));
		CharacterDescription character = CharacterManager.characters.get(characterUuid);
		MapMeta mapMeta = (MapMeta) meta;
		//mapMeta.setMapView(Bukkit.getMap(0));
		mapMeta.setColor(Color.GRAY);
		mapMeta.setLocationName("ID");
		mapMeta.setMapId(0);
		mapMeta.getMapView().getRenderers().forEach(mapRenderer -> mapMeta.getMapView().removeRenderer(mapRenderer));
		mapMeta.getMapView().addRenderer(new IdentityCardMap(character));
		item.setItemMeta(mapMeta);
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
		g.drawImage(image, 0, 16, 32, 32, null);
	}
	
	public void drawLastName(Graphics2D g) {
		g.setColor(java.awt.Color.BLACK);
		g.drawString(character.lastname, 32, 20);
	}
	
	public void drawFirstName(Graphics2D g) {
		g.setColor(java.awt.Color.BLACK);
		g.drawString(character.firstname, 32, 36);
	}
	
	public void drawAge(Graphics2D g) {
		g.setColor(java.awt.Color.BLACK);
		g.drawString("Age: " + character.getAge(), 32, 52);
	}
	
	public void drawSex(Graphics2D g) {
		g.setColor(java.awt.Color.BLACK);
		g.drawString("Sex: " + character.sexuality.getDisplay(), 32, 68);
	}
	
	
	@Override
	public void render(@NotNull MapView map, @NotNull MapCanvas canvas, @NotNull Player player) {
		BufferedImage image = new BufferedImage(128, 128, BufferedImage.TYPE_4BYTE_ABGR);
		Graphics2D g = image.createGraphics();
		g.setFont(new Font("", Font.PLAIN, 12));
		drawBackground(g);
		drawHead(g);
		drawLastName(g);
		drawFirstName(g);
		drawAge(g);
		drawSex(g);
		g.dispose();
		canvas.drawImage(0, 0, image);
	}
	
	@Override
	public int getId() {
		return 128;
	}
	
	@Override
	public boolean isVirtual() {
		return true;
	}
	
	@NotNull
	@Override
	public Scale getScale() {
		return Scale.CLOSEST;
	}
	
	@Override
	public void setScale(@NotNull MapView.Scale scale) {
	
	}
	
	@Override
	public int getCenterX() {
		return 0;
	}
	
	@Override
	public int getCenterZ() {
		return 0;
	}
	
	@Override
	public void setCenterX(int x) {
	
	}
	
	@Override
	public void setCenterZ(int z) {
	
	}
	
	@Nullable
	@Override
	public World getWorld() {
		return Bukkit.getWorlds().get(0);
	}
	
	@Override
	public void setWorld(@NotNull World world) {
	
	}
	
	@NotNull
	@Override
	public List<MapRenderer> getRenderers() {
		return List.of(this);
	}
	
	@Override
	public void addRenderer(@NotNull MapRenderer renderer) {
	
	}
	
	@Override
	public boolean removeRenderer(@Nullable MapRenderer renderer) {
		return false;
	}
	
	@Override
	public boolean isTrackingPosition() {
		return false;
	}
	
	@Override
	public void setTrackingPosition(boolean trackingPosition) {
	
	}
	
	@Override
	public boolean isUnlimitedTracking() {
		return false;
	}
	
	@Override
	public void setUnlimitedTracking(boolean unlimited) {
	
	}
	
	@Override
	public boolean isLocked() {
		return true;
	}
	
	@Override
	public void setLocked(boolean locked) {
	
	}
	
}
