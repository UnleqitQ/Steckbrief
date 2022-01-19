package de.redfox.steckbrief.tobemoved;

import net.arcaniax.headdisplays.display.Display;

public class HeadDisplayData {
	
	public String title, subtitle;
	public int fadeIn, duration, fadeOut;
	public Display display;
	
	public HeadDisplayData(String title, String subtitle, int fadeIn, int duration, int fadeOut) {
		this.title = title;
		this.subtitle = subtitle;
		
		this.fadeIn = (int) (fadeIn / 1000f * 20);
		this.duration = (int) (duration / 1000f * 20);
		this.fadeOut = (int) (fadeOut / 1000f * 20);
	}
	
	public int totalTime() {
		return fadeIn + duration + fadeOut;
	}
	
}
