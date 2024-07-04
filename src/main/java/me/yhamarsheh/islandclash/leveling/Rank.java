package me.yhamarsheh.islandclash.leveling;

import me.yhamarsheh.islandclash.utilities.ChatUtils;

public enum Rank {

    UNRANKED(0, "&7Unranked"),
    BRONZE(1, "&8Bronze"),
    SILVER(2, "&7Sil&fver"),
    GOLD(3, "&6Gold"),
    PLATINUM(4, "&9Platinum"),
    DIAMOND(5, "&bDiamond"),
    ELITE(6, "&9&lElite"),
    CHAMPION(7, "&5&lChampion"),
    BEYOND(8, "&b&l&oBEY&3&l&oOND");

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
