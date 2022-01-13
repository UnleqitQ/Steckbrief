package de.redfox.steckbrief.manager.blindness;

public class BlindnessManager {
    static CustomGenerator customGenerator;

    public static void init() {
        customGenerator = new CustomGenerator();
    }
}
