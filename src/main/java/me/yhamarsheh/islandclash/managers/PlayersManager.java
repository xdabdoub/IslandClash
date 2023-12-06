package me.yhamarsheh.islandclash.managers;

import me.yhamarsheh.islandclash.IslandClash;
import me.yhamarsheh.islandclash.storage.objects.HPlayer;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayersManager {

    private final IslandClash plugin;
    private Map<UUID, HPlayer> playerMap;
    public PlayersManager(IslandClash plugin) {
        this.plugin = plugin;
        this.playerMap = new HashMap<>();
    }

    public void addPlayer(UUID uuid, HPlayer hPlayer) {
        playerMap.put(uuid, hPlayer);
    }

    public void removePlayer(UUID uuid) {
        playerMap.remove(uuid);
    }

    public Map<UUID, HPlayer> getPlayerMap() {
        return playerMap;
    }
}
