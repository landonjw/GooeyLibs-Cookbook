package ca.landonjw.gooeylibs.cookbook.configuration;

import ca.landonjw.gooeylibs.cookbook.Cookbook;
import ca.landonjw.gooeylibs.cookbook.configuration.elements.ConfigElement;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class Configuration {

	public static void load(File configFile, ConfigElement... elements) {
		load(configFile, Arrays.asList(elements));
	}

	public static void load(File configFile, List<ConfigElement> elements) {
		if(!configFile.getParentFile().exists()) {
			configFile.getParentFile().mkdirs();
		}
		if(!configFile.exists()) {
			write(configFile, elements);
		}
		else {
			read(configFile, elements);
			//Used to write any default config values that are not currently present to file, and get rid of junk.
			write(configFile, elements);
		}
	}

	public static void save(File configFile, ConfigElement... elements) {
		save(configFile, Arrays.asList(elements));
	}

	public static void save(File configFile, List<ConfigElement> elements) {
		if(!configFile.getParentFile().exists()) {
			configFile.getParentFile().mkdirs();
		}
		write(configFile, elements);
	}

	private static void write(File configFile, Iterable<ConfigElement> configElements) {
		JsonObject data = new JsonObject();

		for(ConfigElement element : configElements) {
			String[] pathArgs = element.getPath().split("\\.");
			JsonObject targetJson = getTarget(data, element.getPath(), true);
			if(targetJson != null) {
				targetJson.add(pathArgs[pathArgs.length - 1], element.serialize());
			}
		}

		try {
			FileWriter writer = new FileWriter(configFile);
			writer.write(Cookbook.PRETTY_GSON.toJson(data));
			writer.close();
		}
		catch(IOException e) {
			Cookbook.LOGGER.error("Could not write configuration to '" + configFile.getPath() + "'!");
			e.printStackTrace();
		}
	}

	private static void read(File configFile, Iterable<ConfigElement> configElements) {
		try {
			FileReader reader = new FileReader(configFile);
			JsonObject data = Cookbook.PRETTY_GSON.fromJson(reader, JsonObject.class);
			reader.close();

			for(ConfigElement element : configElements) {
				String prettyKey = element.getPath();
				String[] pathArgs = prettyKey.split("\\.");

				JsonObject targetJson = getTarget(data, element.getPath(), false);
				if(targetJson != null) {
					JsonElement targetValue = targetJson.get(pathArgs[pathArgs.length - 1]);
					if(targetValue != null) {
						element.setValue(targetValue);
					}
					else {
						Cookbook.LOGGER.warn("Invalid value supplied for configuration element '" + element.getPath() + "'. Using default.");
					}
				}
			}
		}
		catch(IOException e) {
			Cookbook.LOGGER.error("Could not read configuration from '" + configFile.getPath() + "'!");
			e.printStackTrace();
		}
	}

	private static JsonObject getTarget(JsonObject data, String path, boolean createPath) {
		String[] pathArgs = path.split("\\.");

		JsonObject targetJson = data;
		for(int i = 0; i < pathArgs.length - 1; i++) {
			if(targetJson.has(pathArgs[i])) {
				if(!targetJson.get(pathArgs[i]).isJsonObject()) {
					Cookbook.LOGGER.warn("Configuration loading encountered an error in element path '" + path + "'.");
					targetJson = null;
					break;
				}
				else {
					targetJson = targetJson.get(pathArgs[i]).getAsJsonObject();
				}
			}
			else {
				if(createPath) {
					JsonObject newJsonObject = new JsonObject();
					targetJson.add(pathArgs[i], newJsonObject);
					targetJson = newJsonObject;
				}
			}
		}
		return targetJson;
	}

}
