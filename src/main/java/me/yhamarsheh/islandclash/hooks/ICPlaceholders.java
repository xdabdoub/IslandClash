package me.yhamarsheh.islandclash.hooks;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import me.yhamarsheh.islandclash.IslandClash;
import me.yhamarsheh.islandclash.storage.objects.HPlayer;
import me.yhamarsheh.islandclash.utilities.ChatUtils;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

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
                return hPlayer.getLevel() + "";
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
            default:
                return ChatUtils.color("&7");
        }
    }
}
