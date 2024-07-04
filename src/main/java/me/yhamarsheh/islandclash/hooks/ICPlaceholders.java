package me.yhamarsheh.islandclash.hooks;

import me.clip.placeholderapi.PlaceholderAPI;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import me.yhamarsheh.islandclash.IslandClash;
import me.yhamarsheh.islandclash.storage.objects.HPlayer;
import me.yhamarsheh.islandclash.utilities.ChatUtils;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class ICPlaceholders extends PlaceholderExpansion {

    private IslandClash plugin;
    public ICPlaceholders(IslandClash plugin) {
        this.plugin = plugin;
        register();
    }

    @Override
    public @NotNull String getIdentifier() {
        return "islandclash";
    }

    @Override
    public @NotNull String getAuthor() {
        return "xDabDoub";
    }

    @Override
    public @NotNull String getVersion() {
        return "1.0";
    }

    @Override
    public @Nullable String onPlaceholderRequest(Player player, @NotNull String params) {
        if (player == null) return ChatUtils.color("&7-");
        HPlayer hPlayer = plugin.getPlayer(player.getUniqueId());
        if (hPlayer == null) return ChatUtils.color("&7-");

        switch (params.toLowerCase()) {
            case "kills":
                return hPlayer.getKills() + "";
            case "deaths":
                return hPlayer.getDeaths() + "";
            case "streak":
                return hPlayer.getStreak() + "";
            case "rank":
                return hPlayer.getRank().toString();
            case "level":
                int level = hPlayer.getLevel();
                if (level >= 10 && level < 20) return ChatUtils.color("&f" + level);
                if (level >= 20 && level < 30) return ChatUtils.color("&e" + level);
                if (level >= 30 && level < 40) return ChatUtils.color("&9" + level);
                if (level >= 40 && level < 50) return ChatUtils.color("&6" + level);
                if (level >= 50 && level < 60) return ChatUtils.color("&d" + level);
                if (level >= 60 && level < 70) return ChatUtils.color("&3" + level);
                if (level >= 70 && level < 80) return ChatUtils.color("&5" + level);
                if (level >= 80 && level < 90) return ChatUtils.color("&4" + level);
                if (level >= 90 && level < 100) return ChatUtils.color("&0" + level);
                if (level >= 100) return ChatUtils.color("&b" + level);
                else return ChatUtils.color("&7" + level);
            case "xp":
                return hPlayer.getXP() + "";
            case "hyions":
                return hPlayer.getHyions() + "";
            case "collectedboxes":
                return hPlayer.getCollectedBoxes() + "";
            case "session_hyions":
                return hPlayer.getSessionalStatistics().getHyions() + "";
            case "session_kills":
                return hPlayer.getSessionalStatistics().getKills() + "";
            case "rankcolor":
                return "&" + PlaceholderAPI.setPlaceholders(player, "%luckperms_prefix%").toCharArray()[1];
            default:
                return ChatUtils.color("&7");
        }
    }
}
