package de.redfox.steckbrief.tobemoved;

import de.redfox.steckbrief.Steckbrief;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class MessageQueue {
    public final CQueue<TitleData> queue = new CQueue<>();

    private Player player;
    private Runnable localCallback;

    public MessageQueue(Player player, Runnable callback) {
        this.player = player;
    }

    private void sendNext() {
        TitleData poll = queue.poll();
        if (poll == null)
            return;

        player.sendTitle(poll.title, poll.subtitle, poll.fadeOut, poll.duration, poll.fadeIn);
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
