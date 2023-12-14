package me.yhamarsheh.islandclash.game.session;

import me.yhamarsheh.islandclash.storage.objects.HPlayer;

public class SessionalStatistics {

    // No need to add all of the data, since we wont be using them.
    private int kills;
    private int hyions;

    private final HPlayer hPlayer;
    public SessionalStatistics(HPlayer hPlayer) {
        this.hPlayer = hPlayer;
    }

    public void setKills(int kills) {
        this.kills = kills;
    }

    public void setHyions(int hyions) {
        this.hyions = hyions;
    }

    public int getKills() {
        return kills;
    }

    public int getHyions() {
        return hyions;
    }
}
