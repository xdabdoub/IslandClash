package me.yhamarsheh.islandclash.managers;

import me.yhamarsheh.islandclash.IslandClash;
import me.yhamarsheh.islandclash.game.Game;

public class GameManager {

    private final IslandClash plugin;
    private final Game game;
    public GameManager(IslandClash plugin) {
        this.plugin = plugin;
        this.game = new Game(null);
    }

    public Game getGame() {
        return game;
    }

}
