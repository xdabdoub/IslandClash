package me.yhamarsheh.islandclash;

import me.yhamarsheh.islandclash.api.IslandClashAPI;
import me.yhamarsheh.islandclash.commands.IClashCMD;
import me.yhamarsheh.islandclash.game.Game;
import me.yhamarsheh.islandclash.hooks.ICPlaceholders;
import me.yhamarsheh.islandclash.listeners.DataHandlingListener;
import me.yhamarsheh.islandclash.listeners.GameListener;
import me.yhamarsheh.islandclash.managers.*;
import me.yhamarsheh.islandclash.storage.SQLDatabase;
import me.yhamarsheh.islandclash.storage.objects.HPlayer;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.UUID;

public class IslandClash extends JavaPlugin implements IslandClashAPI {

    private SQLDatabase sqlDatabase;
    private PlayersManager playersManager;
    private ScoreboardManager scoreboardManager;
    private GameManager gameManager;
    private LootBoxesManager lootBoxesManager;
    private KitManager kitManager;

    private ArrayList<Block> blocks;

    @Override
    public void onEnable() {
        init();
    }

    @Override
    public void onDisable() {
        disableLogic();
    }

    private void init() {
        saveDefaultConfig();
        sqlDatabase = new SQLDatabase(this);
        playersManager = new PlayersManager(this);
        scoreboardManager = new ScoreboardManager(this);
        gameManager = new GameManager(this);
        lootBoxesManager = new LootBoxesManager(this);
        kitManager = new KitManager(this);

        blocks = new ArrayList<>();

        // Placeholders
        new ICPlaceholders(this);

        registerListeners();
        registerCommands();
        setupGame();
    }

    private void registerListeners() {
        new DataHandlingListener(this);
        new GameListener(this);
    }

    private void registerCommands() {
        new IClashCMD(this);
    }

    private void setupGame() {
        FileConfiguration configuration = getConfig();
        if (configuration.getString("spawn.world") == null) return;

        World world = Bukkit.getWorld(configuration.getString("spawn.world"));
        double x = configuration.getDouble("spawn.x");
        double y = configuration.getDouble("spawn.y");
        double z = configuration.getDouble("spawn.z");
        double yaw = configuration.getDouble("spawn.yaw");
        double pitch = configuration.getDouble("spawn.pitch");

        getActiveGame().setSpawnLocation(new Location(world, x, y, z, (float) yaw, (float) pitch));
    }

    private void disableLogic() {
        playersManager.disable(); // SEVERE - Contains Players Data
        lootBoxesManager.disable();

        for (Block block : blocks) block.setType(Material.AIR);
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

    public GameManager getGameManager() {
        return gameManager;
    }

    public LootBoxesManager getLootBoxesManager() {
        return lootBoxesManager;
    }
    public KitManager getKitManager() {
        return kitManager;
    }

    public ArrayList<Block> getBlocks() {
        return blocks;
    }

    @Override
    public HPlayer getPlayer(UUID uuid) {
        return getPlayersManager().getPlayerMap().get(uuid);
    }

    @Override
    public Game getActiveGame() {
        return getGameManager().getGame();
    }
}
