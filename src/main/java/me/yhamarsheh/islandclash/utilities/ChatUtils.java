package me.yhamarsheh.islandclash.utilities;

import net.kyori.adventure.text.Component;
import org.bukkit.ChatColor;

import java.util.GregorianCalendar;

public class ChatUtils {

    public static String color(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }
    public static Component component(String s) {
        return Component.text(color(s));
    }
}
