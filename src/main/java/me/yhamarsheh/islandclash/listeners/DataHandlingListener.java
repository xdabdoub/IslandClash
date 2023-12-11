package me.yhamarsheh.islandclash.listeners;

import me.yhamarsheh.islandclash.IslandClash;
import me.yhamarsheh.islandclash.storage.objects.HPlayer;
import me.yhamarsheh.islandclash.utilities.ChatUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.UUID;
import java.util.logging.Level;

public class DataHandlingListener implements Listener {

    private final IslandClash plugin;
    public DataHandlingListener(IslandClash plugin) {
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void loadDataOnPreLogin(AsyncPlayerPreLoginEvent e) {
        UUID uuid = e.getUniqueId();
        HPlayer hPlayer = new HPlayer(plugin, uuid); // This will invoke the create(); non-async, since this method is run Asynchronously.

        plugin.getPlayersManager().addPlayer(uuid, hPlayer);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        HPlayer hPlayer = plugin.getPlayersManager().getPlayerMap().get(player.getUniqueId());
        if (hPlayer == null) {
            player.kickPlayer(ChatUtils.color("&cAn error occurred while attempting to load your data. Please rejoin!"));
            return;
        }

        hPlayer.setPlayer(player);
        plugin.getScoreboardManager().createBoard(hPlayer);
        plugin.getGameManager().getGame().addPlayer(hPlayer);

        hPlayer.teleport(plugin.getActiveGame().getSpawnLocation());
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        Player player = e.getPlayer();
        HPlayer hPlayer = plugin.getPlayersManager().getPlayerMap().get(player.getUniqueId()); // Could never be null, since we're kicking the player if its null when they join.

        plugin.getScoreboardManager().removeBoard(hPlayer);
        plugin.getGameManager().getGame().removePlayer(hPlayer);

        Bukkit.getScheduler().runTaskAsynchronously(plugin, hPlayer::saveData);
        plugin.getPlayersManager().removePlayer(player.getUniqueId());
    }
}
