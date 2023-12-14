package me.yhamarsheh.islandclash.game.upgrades;

import me.yhamarsheh.islandclash.storage.objects.HPlayer;

public abstract class PlayerUpgrade {

    protected int level;
    protected int maxLevel;

    public PlayerUpgrade(int maxLevel) {
        this.maxLevel = maxLevel;
    }

    public abstract void upgrade(HPlayer hPlayer);

    public int getLevel() {
        return level;
    }
    public int getMaxLevel() {
        return maxLevel;
    }
}
