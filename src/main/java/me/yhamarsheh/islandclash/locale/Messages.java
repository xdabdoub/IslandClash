package me.yhamarsheh.islandclash.locale;

import me.yhamarsheh.islandclash.utilities.ChatUtils;

public enum Messages {

    DUMMY("dummy", "Dummy Message"),
    KILL("kill", "%s%s &7was killed by %s%s.%s"), // Rank, Name, Rank, Name, Streak
    STREAK("streak", " %s STREAK"),
    FELL_INTO_THE_VOID("fell_into_the_void", "%s%s &7fell into the void!"), // Rank, Name
    PUSHED_INTO_THE_VOID("pushed_into_the_void", "%s%s &7was pushed into the void by %s%s.%s"), // Rank, Name, Rank, Name, Streak
    LEVEL_UP_MESSAGE("level_up_message", "&7\n&7\n&b&lLEVEL UP! &8%s &7-> %s\n&7You received strength for 8 seconds and $50 Hyions for leveling " +
            "up!\n&7&c&l+ &cStrength &7(8 Seconds)\n&b&l+ &b$50 Hyions\n&7\n&7"); // Previous Level, Next Level

    final String path;
    String message;
    Messages(String path, String message) {
        this.path = path;
        this.message = ChatUtils.color(message);
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPath() {
        return this.path;
    }

    @Override
    public String toString() {
        return this.message;
    }
}
