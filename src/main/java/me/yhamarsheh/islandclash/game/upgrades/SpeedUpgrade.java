package me.yhamarsheh.islandclash.game.upgrades;

import me.yhamarsheh.islandclash.storage.objects.HPlayer;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class SpeedUpgrade extends PlayerUpgrade {

    public SpeedUpgrade() {
        super(2, 300);
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
        hPlayer.sendMessage("&b&lIsland Clash &7- Speed upgrade purchased!");
        hPlayer.sendMessage("&c&l- &c$" + cost);

        hPlayer.setHyions(hPlayer.getHyions() - cost);
        hPlayer.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, level, true, false));
        hPlayer.getPlayer().closeInventory();
    }
}
