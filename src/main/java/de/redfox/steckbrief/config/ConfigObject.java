package de.redfox.steckbrief.config;

import com.google.gson.JsonObject;
import de.redfox.steckbrief.utils.JSONParser;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
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
		load();
	}
	
	public void create() throws IOException {
		file = new File(path.getParent().toUri());
		file.mkdirs();
		file = new File(path.toUri());
		file.createNewFile();
	}
	
	public void load() {
		try {
			SeekableByteChannel sbc = Files.newByteChannel(path);
			InputStream in = Channels.newInputStream(sbc);
			
			StringBuilder read = new StringBuilder((int) sbc.size());
			int res;
			while ((res = in.read()) != -1) {
				read.append(res);
			}
			rootSection = JSONParser.parse(read.toString()).getAsJsonObject();
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
