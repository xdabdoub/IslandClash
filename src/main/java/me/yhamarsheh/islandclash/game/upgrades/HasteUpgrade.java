package me.yhamarsheh.islandclash.game.upgrades;

import me.yhamarsheh.islandclash.storage.objects.HPlayer;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class HasteUpgrade extends PlayerUpgrade {

    public HasteUpgrade() {
        super(2, 200);
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
        hPlayer.sendMessage("&b&lIsland Clash &7- Haste upgrade purchased!");
        hPlayer.sendMessage("&c&l- &c$" + cost);

        hPlayer.setHyions(hPlayer.getHyions() - cost);
        hPlayer.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, Integer.MAX_VALUE, level, true, false));
        hPlayer.getPlayer().closeInventory();
    }
}
