package me.yhamarsheh.islandclash.animation.sub;

import me.yhamarsheh.islandclash.IslandClash;
import me.yhamarsheh.islandclash.lootboxes.LootBox;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.scheduler.BukkitRunnable;

public class LootBoxAnimation {

    private final IslandClash plugin;

    private final LootBox lootBox;

    public LootBoxAnimation(IslandClash plugin, LootBox lootBox) {
        this.plugin = plugin;
        this.lootBox = lootBox;
        run();
    }


    public void run() {

        new BukkitRunnable() {
            double t = 0;
            Location loc = lootBox.getArmorStand().getLocation();

            @Override
            public void run() {
                t = t + Math.PI/32;
                double y = Math.sin(t);
                loc.add(0,y,0);

                lootBox.getArmorStand().teleport(loc);
                lootBox.getArmorStand().setHeadPose(lootBox.getArmorStand().getHeadPose().add(0, 0.17, 0));
                loc.subtract(0, y, 0);

            }
        }.runTaskTimer(plugin, 0, 1);
    }
}
