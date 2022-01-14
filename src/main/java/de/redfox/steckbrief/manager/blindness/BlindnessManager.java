package de.redfox.steckbrief.manager.blindness;

import de.redfox.steckbrief.Steckbrief;
import org.bukkit.Bukkit;

public class BlindnessManager {
    static CustomGenerator customGenerator;

    public static void init() {
        customGenerator = new CustomGenerator();
        Bukkit.getPluginManager().registerEvents(new BlindnessListener(), Steckbrief.getInstance());
    }
}
