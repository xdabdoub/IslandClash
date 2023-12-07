package me.yhamarsheh.islandclash.managers;

import me.yhamarsheh.islandclash.IslandClash;
import me.yhamarsheh.islandclash.game.Game;
import me.yhamarsheh.islandclash.storage.objects.HPlayer;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;

public class GameManager {

    private final IslandClash plugin;
    private final Game game;
    public GameManager(IslandClash plugin) {
        this.plugin = plugin;
        this.game = new Game(null);

        init();
    }

    private void init() {
        FileConfiguration configuration = plugin.getConfig();
        String world = configuration.getString("spawn.world");
        double x = configuration.getDouble("spawn.x");
        double y = configuration.getDouble("spawn.y");
        double z = configuration.getDouble("spawn.z");
        double yaw = configuration.getDouble("spawn.yaw");
        double pitch = configuration.getDouble("spawn.pitch");

        this.game.setSpawnLocation(new Location(Bukkit.getWorld(world), x, y, z, (float) yaw, (float) pitch));
    }

    public Game getGame() {
        return game;
    }

}
