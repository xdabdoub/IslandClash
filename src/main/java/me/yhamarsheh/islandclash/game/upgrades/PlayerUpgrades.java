package me.yhamarsheh.islandclash.game.upgrades;


import me.yhamarsheh.islandclash.storage.objects.HPlayer;

import java.util.ArrayList;

public class PlayerUpgrades {

   private final ArrayList<PlayerUpgrade> playerUpgradeArrayList;
   private final HPlayer hPlayer;
    public PlayerUpgrades(HPlayer hPlayer) {
        this.playerUpgradeArrayList = new ArrayList<>();
        this.hPlayer = hPlayer;

        init();
    }

    private void init() {
        playerUpgradeArrayList.add(null);
        playerUpgradeArrayList.add(null);
        playerUpgradeArrayList.add(null);
    }

    public void setSharpnessUpgrade(SharpnessUpgrade sharpnessUpgrade) {
        playerUpgradeArrayList.set(0, sharpnessUpgrade);
    }

    public void setProtectionUpgrade(ProtectionUpgrade protectionUpgrade) {
        playerUpgradeArrayList.set(1, protectionUpgrade);
    }

    public PlayerUpgrade getSharpnessUpgrade() {
        return playerUpgradeArrayList.get(0);
    }

    public PlayerUpgrade getProtectionUpgrade() {
        return playerUpgradeArrayList.get(1);
    }
}
