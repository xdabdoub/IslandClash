package me.yhamarsheh.islandclash;

import me.yhamarsheh.islandclash.storage.SQLDatabase;
import org.bukkit.plugin.java.JavaPlugin;

public class IslandClash extends JavaPlugin {

    private SQLDatabase sqlDatabase;

    @Override
    public void onEnable() {

    }

    @Override
    public void onDisable() {

    }

    private void init() {
        sqlDatabase = new SQLDatabase(this);
    }

    public SQLDatabase getSQLDatabase() {
        return sqlDatabase;
    }
}
