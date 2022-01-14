package de.redfox.steckbrief.tobemoved;

public class TitleData {
    public String title, subtitle;
    public int fadeIn, duration, fadeOut;

    public TitleData(String title, String subtitle, int fadeIn, int duration, int fadeOut) {
        this.title = title;
        this.subtitle = subtitle;
        this.fadeIn = fadeIn;
        this.duration = duration;
        this.fadeOut = fadeOut;
    }

    public int totalTime() {
        return fadeIn + duration + fadeOut;
    }
}
