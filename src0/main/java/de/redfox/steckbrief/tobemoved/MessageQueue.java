package de.redfox.steckbrief.tobemoved;

import de.redfox.steckbrief.Steckbrief;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class MessageQueue {
	
	public final CQueue<TitleData> queue = new CQueue<>();
	
	private final Player player;
	private Runnable localCallback;
	
	public MessageQueue(Player player) {
		this.player = player;
	}
	
	public void sendNext() {
		TitleData poll = queue.poll();
		if (poll == null) {
			player.sendTitle("", "", 0, 0, 0);
			return;
		}
		
		player.sendTitle(poll.title, poll.subtitle, poll.fadeOut, poll.duration, poll.fadeIn);
		if (!poll.permanent)
			Bukkit.getScheduler().runTaskLater(Steckbrief.getInstance(), localCallback, poll.totalTime());
	}
	
	public void start() {
		localCallback = this::sendNext;
		localCallback.run();
	}
	
	public MessageQueue add(TitleData data) {
		queue.add(data);
		return this;
	}
	
}
