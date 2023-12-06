package me.yhamarsheh.islandclash.locale;

import me.yhamarsheh.islandclash.utilities.ChatUtils;

public enum Messages {

    DUMMY("dummy", "Dummy Message");

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
