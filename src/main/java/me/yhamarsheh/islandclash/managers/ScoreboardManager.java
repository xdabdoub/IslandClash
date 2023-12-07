package me.yhamarsheh.islandclash.managers;

import fr.mrmicky.fastboard.FastBoard;
import me.yhamarsheh.islandclash.IslandClash;
import me.yhamarsheh.islandclash.storage.objects.HPlayer;
import me.yhamarsheh.islandclash.utilities.ChatUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ScoreboardManager {

    private final IslandClash plugin;
    private Map<UUID, FastBoard> scoreboards;

    private String todayDateFormatted;
    public ScoreboardManager(IslandClash plugin) {
        this.plugin = plugin;
        this.scoreboards = new HashMap<>();

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        this.todayDateFormatted = sdf.format(new Date().toInstant());

        startUpdating();
    }

    private void startUpdating() {
        new BukkitRunnable() {

            @Override
            public void run() {
                for (UUID uuid : scoreboards.keySet()) {
                    HPlayer hPlayer = plugin.getPlayersManager().getPlayerMap().get(uuid);
                    if (hPlayer == null) continue;
                    FastBoard board = scoreboards.get(uuid);
                    board.updateLines(
                            ChatUtils.color("&7"),
                            ChatUtils.color("&7" + todayDateFormatted + " Season 1"),
                            ChatUtils.color("&7"),
                            ChatUtils.color("Rank: " + hPlayer.getRank().toString()),
                            ChatUtils.color("&7"),
                            ChatUtils.color("Level: &b" + hPlayer.getLevel()),
                            ChatUtils.color("XP: &b" + hPlayer.getXP() + " &8(" + hPlayer.getXP()/5000 + "%)"),
                            ChatUtils.color("&7"),
                            ChatUtils.color("Hyions: &b$" + hPlayer.getHyions()),
                            ChatUtils.color("&7"),
                            ChatUtils.color("Kills: &b" + hPlayer.getKills()),
                            ChatUtils.color("Streak: &b" + hPlayer.getStreak()),
                            ChatUtils.color("&7"),
                            ChatUtils.color("&bplay.hybridmc.xyz")
                    );
                }
            }
        }.runTaskTimer(plugin, 0, 10L);
    }

    public void createBoard(HPlayer hPlayer) {
        FastBoard board = new FastBoard(hPlayer.getPlayer());
        board.updateTitle(ChatUtils.color("&b&lIsland &3Clash"));

        board.updateLines(
                ChatUtils.color("&7"),
                ChatUtils.color("&7" + todayDateFormatted + " Season 1"),
                ChatUtils.color("&7"),
                ChatUtils.color("Rank: " + hPlayer.getRank().toString()),
                ChatUtils.color("&7"),
                ChatUtils.color("Level: &b" + hPlayer.getLevel()),
                ChatUtils.color("XP: &b" + hPlayer.getXP() + " &8(" + hPlayer.getXP()/5000 + "%)"),
                ChatUtils.color("&7"),
                ChatUtils.color("Hyions: &b$" + hPlayer.getHyions()),
                ChatUtils.color("&7"),
                ChatUtils.color("Kills: &b" + hPlayer.getKills()),
                ChatUtils.color("Streak: &b" + hPlayer.getStreak()),
                ChatUtils.color("&7"),
                ChatUtils.color("&bplay.hybridmc.xyz")
        );
    }

    public void removeBoard(HPlayer hPlayer) {
        scoreboards.remove(hPlayer.getUniqueId());
    }
}
