package de.redfox.steckbrief.manager.config;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import de.redfox.steckbrief.utils.JSONParser;

import java.io.*;
import java.nio.channels.Channels;
import java.nio.channels.SeekableByteChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ConfigObject {
	
	public JsonObject rootSection;
	
	public File file;
	private final Path path;
	
	public ConfigObject(String path, String file) {
		this.path = Paths.get(path, file);
		create();
		load();
	}

	private void set(String property, JsonElement value, boolean overwrite) {
		String[] sections = property.split("\\.");

		JsonObject currentObject = rootSection;
		for (int i = 0; i < sections.length; i++) {
			String sectionKey = sections[i];
			if (i == sections.length - 1) {
				if (currentObject.get(sectionKey) != null && !overwrite)
					return;
				currentObject.add(sectionKey, value);
				break;
			}

			JsonElement tempObject = currentObject.get(sectionKey);

			if (tempObject == null) {
				tempObject = new JsonObject();
				currentObject.add(sectionKey, tempObject);
			}

			currentObject = tempObject.getAsJsonObject();
		}
	}

	public void set(String property, JsonElement value) {
		this.set(property, value, true);
	}

	public void setDefault(String property, JsonElement value) {
		this.set(property, value, false);
	}
	
	public void create() {
		file = new File(path.getParent().toUri());
		file.mkdirs();
		file = new File(path.toUri());
		try {
			file.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void load() {
		try {
			SeekableByteChannel sbc = Files.newByteChannel(path);
			Reader reader = Channels.newReader(sbc, StandardCharsets.UTF_8);
			StringBuilder read = new StringBuilder((int) sbc.size());
			int res;
			while ((res = reader.read()) != -1) {
				read.append((char) res);
			}
			rootSection = JSONParser.parse(read.toString());
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void save() {
		try {
			FileOutputStream stream = new FileOutputStream(file);
			stream.write(JSONParser.toString(rootSection).getBytes(StandardCharsets.UTF_8));
			stream.flush();
			stream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
