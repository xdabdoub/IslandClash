package me.yhamarsheh.islandclash.game.upgrades;

import me.yhamarsheh.islandclash.storage.objects.HPlayer;

public class ProtectionUpgrade extends PlayerUpgrade {

    public ProtectionUpgrade() {
        super(4);
    }

    @Override
    public void upgrade(HPlayer hPlayer) {
        if (level == 4) return;
    }
}
