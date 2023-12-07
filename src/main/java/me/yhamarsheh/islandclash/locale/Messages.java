package me.yhamarsheh.islandclash.locale;

import me.yhamarsheh.islandclash.utilities.ChatUtils;

public enum Messages {

    DUMMY("dummy", "Dummy Message"),
    KILL("kill", "%s%s was killed by %s%s.%s"), // Rank, Name, Rank, Name, Streak
    STREAK("streak", " %s STREAK"),
    FELL_INTO_THE_VOID("fell_into_the_void", "%s%s fell into the void!"), // Rank, Name
    PUSHED_INTO_THE_VOID("pushed_into_the_void", "%s%s was pushed into the void by %s%s.%s"); // Rank, Name, Rank, Name, Streak

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
