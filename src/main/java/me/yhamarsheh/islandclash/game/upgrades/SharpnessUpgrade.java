package me.yhamarsheh.islandclash.game.upgrades;

import me.yhamarsheh.islandclash.storage.objects.HPlayer;

public class SharpnessUpgrade extends PlayerUpgrade {

    public SharpnessUpgrade() {
        super(1);
    }

    @Override
    public void upgrade(HPlayer hPlayer) {
        if (level == 1) return;
    }
}
