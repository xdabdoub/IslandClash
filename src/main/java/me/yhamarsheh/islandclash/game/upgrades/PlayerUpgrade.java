package me.yhamarsheh.islandclash.game.upgrades;

import me.yhamarsheh.islandclash.storage.objects.HPlayer;

public abstract class PlayerUpgrade {

    protected int level;
    protected int maxLevel;
    protected int upgradeCost;

    public PlayerUpgrade(int maxLevel, int upgradeCost) {
        this.maxLevel = maxLevel;
        this.upgradeCost = upgradeCost;
    }

    public abstract void upgrade(HPlayer hPlayer);

    public int getLevel() {
        return level;
    }
    public int getMaxLevel() {
        return maxLevel;
    }

    public int upgradeCost() {
        return upgradeCost;
    }
}
