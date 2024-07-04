package me.yhamarsheh.islandclash.game.upgrades;

import me.yhamarsheh.islandclash.storage.objects.HPlayer;

public class SharpnessUpgrade extends PlayerUpgrade {

    public SharpnessUpgrade() {
        super(1, 750);
    }

    @Override
    public void upgrade(HPlayer hPlayer) {
        if (level == maxLevel) return;

        int cost = upgradeCost * (level + 1);
        if (hPlayer.getSessionalStatistics().getHyions() < cost) {
            hPlayer.sendMessage("&b&lIsland Clash &7- &cYou don't have enough session Hyions!");
            return;
        }

        level++;
        hPlayer.sendMessage("&b&lIsland Clash &7- Sharpness upgrade purchased!");
        hPlayer.sendMessage("&c&l- &c$" + cost);

        hPlayer.setHyions(hPlayer.getHyions() - cost);
        hPlayer.getPlayer().closeInventory();
    }
}
