package me.yhamarsheh.islandclash.utilities;

import me.yhamarsheh.islandclash.storage.objects.HPlayer;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class RewardsUtils {

    public static void giveLevelingUpRewards(HPlayer hPlayer) {
        hPlayer.addHyions(50);
        hPlayer.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 8*20, 1));
    }
}
