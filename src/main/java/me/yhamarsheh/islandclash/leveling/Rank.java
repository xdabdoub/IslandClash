package me.yhamarsheh.islandclash.leveling;

import me.yhamarsheh.islandclash.utilities.ChatUtils;

public enum Rank {

    UNRANKED(0, "&7Unranked"),
    BRONZE(1, "&8Bronze"),
    SILVER(2, "&fSilver"),
    GOLD(3, "&6Gold"),
    PLATINUM(4, "&9Platinum"),
    DIAMOND(5, "&bDiamond"),
    ELITE(6, "&aElite"),
    CHAMPION(7, "&5Champion"),
    BEYOND(8, "&1Beyond");

    int id;
    String displayName;
    Rank(int id, String displayName) {
        this.id = id;
        this.displayName = ChatUtils.color(displayName);
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return displayName;
    }

}
