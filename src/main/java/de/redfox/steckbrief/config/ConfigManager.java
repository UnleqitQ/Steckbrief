package de.redfox.steckbrief.config;

public class ConfigManager {
    public static ConfigObject data;
    public static ConfigObject lang_de;
    public static ConfigObject lang_en;

    private static final String pluginPath = "plugins/Steckbrief";

    public static void init() {
        data = new ConfigObject(pluginPath, "data.json");
        lang_de = new ConfigObject(pluginPath, "lang_de.json");
        lang_en = new ConfigObject(pluginPath, "lang_en.json");
    }
}
