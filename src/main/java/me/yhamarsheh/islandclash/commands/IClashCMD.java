package me.yhamarsheh.islandclash.commands;

import me.yhamarsheh.islandclash.IslandClash;
import me.yhamarsheh.islandclash.guis.LayoutGUI;
import me.yhamarsheh.islandclash.guis.UpgradesGUI;
import me.yhamarsheh.islandclash.leveling.Rank;
import me.yhamarsheh.islandclash.lootboxes.LootBoxType;
import me.yhamarsheh.islandclash.storage.objects.HPlayer;
import me.yhamarsheh.islandclash.utilities.ChatUtils;
import me.yhamarsheh.islandclash.utilities.LocationsUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class IClashCMD implements CommandExecutor {

    private IslandClash plugin;
    public IClashCMD(IslandClash plugin) {
        this.plugin = plugin;
        plugin.getCommand("islandclash").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if (commandSender instanceof Player) {
            Player player = (Player) commandSender;
            HPlayer hPlayer = plugin.getPlayer(player.getUniqueId());

            if (!(player.hasPermission("islandclash.admin"))) {
                hPlayer.sendMessage("&b&lIsland Clash &7- Season 1 (Find out more: discord.hybridmc.xyz)");
                return true;
            }
        }

        if (args[0].equalsIgnoreCase("setspawn")) {
            if (!(commandSender instanceof Player)) return true;
            Player player = (Player) commandSender;
            HPlayer hPlayer = plugin.getPlayer(player.getUniqueId());

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
        } else if (args[0].equalsIgnoreCase("setspawnlootbox")) {
            if (!(commandSender instanceof Player)) return true;
            Player player = (Player) commandSender;
            HPlayer hPlayer = plugin.getPlayer(player.getUniqueId());

            if (args.length < 2) {
                hPlayer.sendMessage("&b&lIsland Clash &7- &cYou must specify the type of the loot box you want to spawn." +
                        "\nChoose one of the following:\n1. COMMON\n2. RARE\n3. EPIC\n4. LEGENDARY");
                return true;
            }

            Location location = hPlayer.getLocation();
            LootBoxType lootBoxType = LootBoxType.valueOf(args[1].toUpperCase());

            List<Location> locationList = plugin.getLootBoxesManager().getLocationsByType(lootBoxType);
            locationList.add(location);

            ArrayList<String> locations = locationList.stream().map(LocationsUtils::locationToString).collect(Collectors.toCollection(ArrayList::new));
            plugin.getConfig().set("lootboxes." + lootBoxType.name().toLowerCase() + ".locations", locations);
            plugin.saveConfig();
            hPlayer.sendMessage("&b&lIsland Clash &7- &7Spawn set for a " + lootBoxType.name() + " loot box!");
        } else if (args[0].equalsIgnoreCase("setstat")) {
            if (!(commandSender instanceof Player)) return true;
            Player player = (Player) commandSender;
            HPlayer hPlayer = plugin.getPlayer(player.getUniqueId());

            if (args.length < 4) {
                hPlayer.sendMessage("&b&lIsland Clash &7- &cNot enough arguments. /islandclash setstat <player> <LEVEL,XP,RANK,HYIONS,STREAKS> <value>");
                return true;
            }

            Player target = Bukkit.getPlayer(args[1]);
            if (target == null) {
                hPlayer.sendMessage("&b&lIsland Clash &7- &cInvalid Player");
                return true;
            }

            HPlayer hTarget = plugin.getPlayer(target.getUniqueId());

            String statType = args[2];
            String statValue = args[3];

            switch (statType.toLowerCase()) {
                case "level":
                    hTarget.setLevel(Integer.parseInt(statValue));
                    hPlayer.sendMessage("&b&lIsland Clash &7- Updated the player's level successfully!");
                    break;
                case "xp":
                    hTarget.setXP(Integer.parseInt(statValue));
                    hPlayer.sendMessage("&b&lIsland Clash &7- Updated the player's XP successfully!");
                    break;
                case "rank":
                    hTarget.setRank(Rank.valueOf(statValue.toUpperCase()));
                    hPlayer.sendMessage("&b&lIsland Clash &7- Updated the player's rank successfully!");
                    break;
                case "hyions":
                    hTarget.setHyions(Integer.parseInt(statValue));
                    hPlayer.sendMessage("&b&lIsland Clash &7- Updated the player's hyions successfully!");
                    break;
                case "streak":
                    hTarget.setLevel(Integer.parseInt(statValue));
                    hPlayer.sendMessage("&b&lIsland Clash &7- Updated the player's streak successfully!");
                    break;
                case "shyions":
                    hTarget.getSessionalStatistics().setHyions(Integer.parseInt(statValue));
                    hPlayer.sendMessage("&b&lIsland Clash &7- Updated the player's session hyions successfully!");
                    break;
                default:
                    hPlayer.sendMessage("&b&lIsland Clash &7- &cInvalid stat type.!");
                    break;
            }
        } else if (args[0].equalsIgnoreCase("openlayout")) {
            if (args.length < 2) {
                commandSender.sendMessage(ChatUtils.color("&b&lIsland Clash &7- &cNot enough arguments. /islandclash openlayout <player>"));
                return true;
            }

            Player target = Bukkit.getPlayer(args[1]);
            if (target == null) {
                commandSender.sendMessage(ChatUtils.color("&b&lIsland Clash &7- &cInvalid Player"));
                return true;
            }

            new LayoutGUI(target, plugin);
            commandSender.sendMessage(ChatUtils.color("&b&lIsland Clash &7- &7Opened the layout editor for " + target.getName() + "!"));
        } else if (args[0].equalsIgnoreCase("openupgrades")) {
            if (args.length < 2) {
                commandSender.sendMessage(ChatUtils.color("&b&lIsland Clash &7- &cNot enough arguments. /islandclash openupgrades <player>"));
                return true;
            }

            Player target = Bukkit.getPlayer(args[1]);
            if (target == null) {
                commandSender.sendMessage(ChatUtils.color("&b&lIsland Clash &7- &cInvalid Player"));
                return true;
            }

            HPlayer hTarget = plugin.getPlayer(target.getUniqueId());
            new UpgradesGUI(plugin, hTarget);
            commandSender.sendMessage(ChatUtils.color("&b&lIsland Clash &7- &7Opened the upgrades gui for " + target.getName() + "!"));
        } else if (args[0].equalsIgnoreCase("start")) {
            plugin.getLootBoxesManager().initLocations();
        } else if (args[0].equalsIgnoreCase("addstat")) {
            if (args.length < 4) {
                commandSender.sendMessage("&b&lIsland Clash &7- &cNot enough arguments. /islandclash addstat <player> <LEVEL,XP,HYIONS,STREAKS> <value>");
                return true;
            }

            Player target = Bukkit.getPlayer(args[1]);
            if (target == null) {
                commandSender.sendMessage("&b&lIsland Clash &7- &cInvalid Player");
                return true;
            }

            HPlayer hTarget = plugin.getPlayer(target.getUniqueId());

            String statType = args[2];
            String statValue = args[3];

            switch (statType.toLowerCase()) {
                case "level":
                    hTarget.addLevel();
                    commandSender.sendMessage("&b&lIsland Clash &7- Updated the player's level successfully!");
                    break;
                case "xp":
                    hTarget.addXP(Integer.parseInt(statValue));
                    commandSender.sendMessage("&b&lIsland Clash &7- Updated the player's XP successfully!");
                    break;
                case "hyions":
                    hTarget.addHyions(Integer.parseInt(statValue));
                    commandSender.sendMessage("&b&lIsland Clash &7- Updated the player's hyions successfully!");
                    break;
                case "streak":
                    hTarget.addStreak();
                    commandSender.sendMessage("&b&lIsland Clash &7- Updated the player's streak successfully!");
                    break;
                case "shyions":
                    hTarget.getSessionalStatistics().addHyions();
                    commandSender.sendMessage("&b&lIsland Clash &7- Updated the player's session hyions successfully!");
                    break;
                default:
                    commandSender.sendMessage("&b&lIsland Clash &7- &cInvalid stat type.!");
                    break;
            }
        }
        return false;
    }
}
