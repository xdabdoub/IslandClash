package me.yhamarsheh.islandclash.listeners;

import de.netzkronehd.wgregionevents.events.RegionEnterEvent;
import me.clip.placeholderapi.PlaceholderAPI;
import me.yhamarsheh.islandclash.IslandClash;
import me.yhamarsheh.islandclash.locale.Messages;
import me.yhamarsheh.islandclash.storage.objects.HPlayer;
import me.yhamarsheh.islandclash.utilities.RewardsUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerMoveEvent;

public class GameListener implements Listener {

    private IslandClash plugin;
    public GameListener(IslandClash plugin) {
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent e) {
        Entity damaged = e.getEntity();
        Entity damager = e.getDamager();

        if (!(damaged instanceof Player)) return;
        if (!(damager instanceof Player)) return;

        Player player = (Player) damaged;
        Player pDamager = (Player) damager;

        HPlayer hPlayer = plugin.getPlayersManager().getPlayerMap().get(player.getUniqueId());
        HPlayer hDamager = plugin.getPlayersManager().getPlayerMap().get(pDamager.getUniqueId());

        plugin.getPlayersManager().getDamageManager().handle(hDamager, hPlayer);

        if (player.getHealth() - e.getDamage() < 1) {
            e.setCancelled(true);

            player.teleport(plugin.getGameManager().getGame().getSpawnLocation());
            hPlayer.addDeath();
            hDamager.addKill();

            hDamager.addXP((hDamager.getStreak() > 1 ? 10 : 1));
            int xp = hDamager.getXP();
            if (xp >= 5000) {
                // Call the ICPlayerLevelUpEvent

                int left = 5000 - xp;
                if (left < 0) {
                    left = ~left;
                }

                hDamager.addLevel();
                hDamager.setXP(left);

                hDamager.sendMessage(String.format(Messages.LEVEL_UP_MESSAGE.toString(),
                        hDamager.getLevel() - 1, hDamager.getLevel()));
                RewardsUtils.giveLevelingUpRewards(hDamager);
            }

            // Call the ICPlayerKillEvent
            // Call the ICPlayerDeathEvent - Cause.PLAYER

            plugin.getPlayersManager().getDamageManager().remove(hDamager);

            String killerRank = extractColorCode(PlaceholderAPI.setPlaceholders(pDamager, "%luckperms_suffix%"));
            String killedRank = extractColorCode(PlaceholderAPI.setPlaceholders(player, "%luckperms_suffix%"));

            plugin.getGameManager().getGame().announceMessage(String.format(Messages.KILL.toString(),
                    killedRank, hPlayer.getName(), killerRank, hDamager.getName(), (hDamager.getStreak() > 5 ? String.format(Messages.STREAK.toString(), hDamager.getStreak()) : "")));
        }
    }

    @EventHandler
    public void onFall(PlayerMoveEvent e) {
        Player player = e.getPlayer();
        if (player.getLocation().getY() > 25) return;

        HPlayer hPlayer = plugin.getPlayersManager().getPlayerMap().get(player.getUniqueId());
        HPlayer lastDamager = plugin.getPlayersManager().getDamageManager().getLastDamager(hPlayer);

        hPlayer.teleport(plugin.getGameManager().getGame().getSpawnLocation());

        String killedRank = extractColorCode(PlaceholderAPI.setPlaceholders(player, "%luckperms_suffix%"));

        if (lastDamager == null) {
            // Call the ICPlayerDeathEvent - Cause.VOID

            hPlayer.addDeath();

            plugin.getGameManager().getGame().announceMessage(String.format(Messages.FELL_INTO_THE_VOID.toString(),
                    killedRank, hPlayer.getName()));
            return;
        }
        hPlayer.addDeath();

        lastDamager.addXP((lastDamager.getStreak() > 1 ? 10 : 1));
        int xp = lastDamager.getXP();
        if (xp >= 5000) {
            // Call the ICPlayerLevelUpEvent

            int left = 5000 - xp;
            if (left < 0) {
                left = ~left;
            }

            lastDamager.addLevel();
            lastDamager.setXP(left);

            lastDamager.sendMessage(String.format(Messages.LEVEL_UP_MESSAGE.toString(),
                    lastDamager.getLevel() - 1, lastDamager.getLevel()));
            RewardsUtils.giveLevelingUpRewards(lastDamager);
        }

        // Call the ICPlayerKillEvent
        // Call the ICPlayerDeathEvent - Cause.PLAYER

        String killerRank = extractColorCode(PlaceholderAPI.setPlaceholders(lastDamager.getPlayer(), "%luckperms_suffix%"));
        plugin.getGameManager().getGame().announceMessage(String.format(Messages.PUSHED_INTO_THE_VOID.toString(),
                killedRank, hPlayer.getName(), killerRank, lastDamager.getName(), (lastDamager.getStreak() > 5 ?
                        String.format(Messages.STREAK.toString(), lastDamager.getStreak()) : "")));

        plugin.getPlayersManager().getDamageManager().remove(lastDamager);
    }

    @EventHandler
    public void onDropToArena(RegionEnterEvent e) {
        Player player = e.getPlayer();
        if (!e.getRegion().getId().equals("drop")) return;

        HPlayer hPlayer = plugin.getPlayersManager().getPlayerMap().get(player.getUniqueId());
        plugin.getKitManager().giveKit(hPlayer);
    }

    private String extractColorCode(String rank) {
        char[] characters = rank.toCharArray();
        if (characters.length > 2) {
            return "&" + characters[1];
        } else {
            return "&7";
        }
    }
}
