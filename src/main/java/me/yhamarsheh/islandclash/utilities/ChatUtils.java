package me.yhamarsheh.islandclash.utilities;

import org.bukkit.ChatColor;

public class ChatUtils {

    public static String color(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }
}
