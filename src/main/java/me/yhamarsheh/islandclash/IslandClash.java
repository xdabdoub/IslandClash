package me.yhamarsheh.islandclash;

import me.yhamarsheh.islandclash.managers.PlayersManager;
import me.yhamarsheh.islandclash.managers.ScoreboardManager;
import me.yhamarsheh.islandclash.storage.SQLDatabase;
import org.bukkit.plugin.java.JavaPlugin;

public class IslandClash extends JavaPlugin {

    private SQLDatabase sqlDatabase;
    private PlayersManager playersManager;
    private ScoreboardManager scoreboardManager;

    @Override
    public void onEnable() {
        init();
    }

    @Override
    public void onDisable() {

    }

    private void init() {
        sqlDatabase = new SQLDatabase(this);
        playersManager = new PlayersManager(this);
        scoreboardManager = new ScoreboardManager(this);
    }

    public SQLDatabase getSQLDatabase() {
        return sqlDatabase;
    }

    public PlayersManager getPlayersManager() {
        return playersManager;
    }

    public ScoreboardManager getScoreboardManager() {
        return scoreboardManager;
    }
}
