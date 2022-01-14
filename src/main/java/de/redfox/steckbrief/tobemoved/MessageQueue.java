package de.redfox.steckbrief.tobemoved;

import de.redfox.steckbrief.Steckbrief;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.PriorityQueue;
import java.util.Queue;

public class MessageQueue {
    public final Queue<TitleData> queue = new PriorityQueue<>();

    private Player player;

    private Runnable callback;

    public MessageQueue(Player player) {
        this.player = player;
    }

    private void sendNext() {
        TitleData poll = queue.peek();
        if (poll == null)
            return;

        player.sendTitle(poll.title, poll.subtitle, poll.fadeOut, poll.duration, poll.fadeIn);
        Bukkit.getScheduler().runTaskLater(Steckbrief.getInstance(), callback, poll.totalTime());
    }

    public void start() {
        callback = this::sendNext;
    }

    public MessageQueue add(TitleData data) {
        queue.add(data);
        return this;
    }
}
