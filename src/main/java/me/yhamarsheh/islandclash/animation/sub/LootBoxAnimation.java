package me.yhamarsheh.islandclash.animation.sub;

import me.yhamarsheh.islandclash.IslandClash;
import me.yhamarsheh.islandclash.lootboxes.LootBox;
import org.bukkit.entity.ArmorStand;
import org.bukkit.scheduler.BukkitRunnable;

public class LootBoxAnimation extends BukkitRunnable {

    private final IslandClash plugin;

    private final LootBox lootBox;
    private final double speed = 6.0;

    public LootBoxAnimation(IslandClash plugin, LootBox lootBox) {
        this.plugin = plugin;
        this.lootBox = lootBox;
        runTaskTimer(plugin, 0, 1);
    }

   private int currentTick = 0;
    private double lastYOffset = 0;

    @Override
    public void run() {
        ArmorStand armorStand = lootBox.getArmorStand();
        if (armorStand == null || armorStand.isDead()) cancel();

        double yOffset = Math.sin(Math.toRadians(currentTick * speed));
        armorStand.teleport(armorStand.getLocation().add(0, (yOffset - lastYOffset), 0));
        armorStand.setHeadPose(armorStand.getHeadPose().add(0, 0.10, 0));

        lastYOffset = yOffset;
        currentTick++;
    }
}
