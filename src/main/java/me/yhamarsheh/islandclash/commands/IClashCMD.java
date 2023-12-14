package me.yhamarsheh.islandclash.commands;

import me.yhamarsheh.islandclash.IslandClash;
import me.yhamarsheh.islandclash.lootboxes.LootBoxType;
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
        } else if (args[0].equalsIgnoreCase("spawnlootbox")) {
            if (args.length < 2) {
                hPlayer.sendMessage("&b&lIsland Clash &7- &cYou must specify the type of the loot box you want to spawn." +
                        "\nChoose one of the following:\n1. COMMON\n2. RARE\n3. EPIC\n4. LEGENDARY");
                return true;
            }

            Location location = hPlayer.getLocation();
            LootBoxType lootBoxType = LootBoxType.valueOf(args[1].toUpperCase());
            plugin.getLootBoxesManager().createByType(lootBoxType).spawn(location);
            hPlayer.sendMessage("&b&lIsland Clash &7- &7Spawned a " + lootBoxType.name() + " loot box!");
        }
        return false;
    }
}
