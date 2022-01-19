package de.redfox.steckbrief.tobemoved;

import org.bukkit.Bukkit;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

public class Other {
    public static Scoreboard scoreboard;
    public static Team team;

    public static void init() {
        scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        team = scoreboard.registerNewTeam("NoName");
        team.setOption(Team.Option.NAME_TAG_VISIBILITY, Team.OptionStatus.NEVER);
    }
}
