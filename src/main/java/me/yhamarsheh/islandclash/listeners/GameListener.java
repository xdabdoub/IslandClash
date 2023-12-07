package me.yhamarsheh.islandclash.listeners;

import me.clip.placeholderapi.PlaceholderAPI;
import me.yhamarsheh.islandclash.IslandClash;
import me.yhamarsheh.islandclash.locale.Messages;
import me.yhamarsheh.islandclash.storage.objects.HPlayer;
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
            player.teleport(plugin.getGameManager().getGame().getSpawnLocation());
            hPlayer.addDeath();
            hDamager.addKill();

            hDamager.addXP((hDamager.getStreak() > 1 ? 10 : 1));
            int xp = hDamager.getXP();
            if (xp >= 5000) {
                int left = 5000 - xp;
                hDamager.addLevel();
                hDamager.setXP(left);

                // Leveling Up Message
            }

            plugin.getPlayersManager().getDamageManager().remove(hDamager);

            String killerRank = PlaceholderAPI.setPlaceholders(pDamager, "%luckperms_prefix%");
            String killedRank = PlaceholderAPI.setPlaceholders(player, "%luckperms_prefix%");

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

        String killedRank = PlaceholderAPI.setPlaceholders(player, "%luckperms_prefix%");

        if (lastDamager == null) {
            plugin.getGameManager().getGame().announceMessage(String.format(Messages.FELL_INTO_THE_VOID.toString(),
                    killedRank, hPlayer.getName()));
            return;
        }

        lastDamager.addXP((lastDamager.getStreak() > 1 ? 10 : 1));
        int xp = lastDamager.getXP();
        if (xp >= 5000) {
            int left = 5000 - xp;
            lastDamager.addLevel();
            lastDamager.setXP(left);

            // Leveling Up Message
        }

        String killerRank = PlaceholderAPI.setPlaceholders(lastDamager.getPlayer(), "%luckperms_prefix%");
        plugin.getGameManager().getGame().announceMessage(String.format(Messages.PUSHED_INTO_THE_VOID.toString(),
                killedRank, hPlayer.getName(), killerRank, lastDamager.getName(), (lastDamager.getStreak() > 5 ?
                        String.format(Messages.STREAK.toString(), lastDamager.getStreak()) : "")));

        plugin.getPlayersManager().getDamageManager().remove(lastDamager);
    }
}
