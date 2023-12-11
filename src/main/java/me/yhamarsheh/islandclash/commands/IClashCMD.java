package me.yhamarsheh.islandclash.commands;

import me.yhamarsheh.islandclash.IslandClash;
import me.yhamarsheh.islandclash.storage.objects.HPlayer;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class IClashCMD implements CommandExecutor {

    private IslandClash plugin;
    public IClashCMD(IslandClash plugin) {
        this.plugin = plugin;
        plugin.getCommand("islandclash").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if (!(commandSender instanceof Player)) return true;
        Player player = (Player) commandSender;
        HPlayer hPlayer = plugin.getPlayer(player.getUniqueId());

        if (!(player.hasPermission("islandclash.admin"))) {
            hPlayer.sendMessage("&b&lIsland Clash &7- Season 1 (Find out more: discord.hybridmc.xyz)");
            return true;
        }

        if (args[0].equalsIgnoreCase("setspawn")) {
            Location location = hPlayer.getLocation();
            plugin.getActiveGame().setSpawnLocation(location);

            FileConfiguration configuration = plugin.getConfig();
            configuration.set("spawn.world", location.getWorld().getName());
            configuration.set("spawn.x", location.getX());
            configuration.set("spawn.y", location.getY());
            configuration.set("spawn.z", location.getZ());
            configuration.set("spawn.yaw", location.getYaw());
            configuration.set("spawn.pitch", location.getPitch());

            plugin.saveConfig();

            hPlayer.sendMessage("&b&lIsland Clash &7- Spawn location set successfully.");
        }
        return false;
    }
}
