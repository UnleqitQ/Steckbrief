package de.redfox.steckbrief.tobemoved;

public class TitleData {
    public String title, subtitle;
    public int fadeIn, duration, fadeOut;

    public boolean permanent;

    public TitleData(String title, String subtitle, int fadeIn, int duration, int fadeOut) {
        this.title = title;
        this.subtitle = subtitle;

        this.fadeIn = (int) (fadeIn / 1000f * 20);
        this.duration = (int) (duration / 1000f * 20);
        this.fadeOut = (int) (fadeOut / 1000f * 20);
    }

    public TitleData(String title, String subtitle, int fadeIn, int fadeOut) {
        this.title = title;
        this.subtitle = subtitle;

        this.fadeIn = (int) (fadeIn / 1000f * 20);
        this.fadeOut = (int) (fadeOut / 1000f * 20);
        this.duration = 20*10000; // Assuming nobody will be creating their character for more than two hours

        permanent = true;
    }

    public int totalTime() {
        return fadeIn + duration + fadeOut;
    }
}
