package me.yhamarsheh.islandclash.game.upgrades;

import me.yhamarsheh.islandclash.storage.objects.HPlayer;

public class ProtectionUpgrade extends PlayerUpgrade {

    public ProtectionUpgrade() {
        super(2, 500);
    }

    @Override
    public void upgrade(HPlayer hPlayer) {
        if (level == maxLevel) return;

        int cost = upgradeCost * (level + 1);
        if (hPlayer.getSessionalStatistics().getHyions() <= cost) {
            hPlayer.sendMessage("&b&lIsland Clash &7- &cYou don't have enough session Hyions!");
            return;
        }

        level++;
        hPlayer.sendMessage("&b&lIsland Clash &7- Protection upgrade purchased!");
        hPlayer.sendMessage("&c&l- &c$" + cost);

        hPlayer.setHyions(hPlayer.getHyions() - cost);
        hPlayer.getPlayer().closeInventory();
    }
}
