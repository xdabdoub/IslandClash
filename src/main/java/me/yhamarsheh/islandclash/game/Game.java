package me.yhamarsheh.islandclash.game;

import me.yhamarsheh.islandclash.storage.objects.HPlayer;
import org.bukkit.Location;

import java.util.ArrayList;
import java.util.List;

public class Game {

    private Location spawnLocation;
    private List<HPlayer> playerList;
    public Game(Location spawnLocation) {
        this.spawnLocation = spawnLocation;
        this.playerList = new ArrayList<>();
    }

    public void announceMessage(String s) {
        for (HPlayer hPlayer : playerList) {
            hPlayer.sendMessage(s);
        }
    }

    public void setSpawnLocation(Location spawnLocation) {
        this.spawnLocation = spawnLocation;
    }

    public void addPlayer(HPlayer hPlayer) {
        playerList.add(hPlayer);
    }

    public void removePlayer(HPlayer hPlayer) {
        playerList.remove(hPlayer);
    }

    public boolean isPlayerInGame(HPlayer hPlayer) {
        return playerList.contains(hPlayer);
    }

    public Location getSpawnLocation() {
        return spawnLocation;
    }

    public List<HPlayer> getPlayerList() {
        return playerList;
    }
}
