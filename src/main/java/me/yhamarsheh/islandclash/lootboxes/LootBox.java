package me.yhamarsheh.islandclash.lootboxes;

import dev.triumphteam.gui.builder.item.ItemBuilder;
import me.yhamarsheh.islandclash.IslandClash;
import me.yhamarsheh.islandclash.animation.sub.LootBoxAnimation;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Random;

public abstract class LootBox {

    private final String SKULL_TEXTURE;
    private ArmorStand armorStand;
    private ItemStack loot;

    public LootBox(String SKULL_TEXTURE, ItemStack loot) {
        this.SKULL_TEXTURE = SKULL_TEXTURE;
        this.loot = loot;
    }

    public void spawn(@NotNull Location location) {
        this.armorStand = location.getWorld().spawn(location, ArmorStand.class);
        this.armorStand.setVisible(false);
        this.armorStand.setHealth(20);
        this.armorStand.setHelmet(ItemBuilder.skull().texture(SKULL_TEXTURE).build());
        this.armorStand.setGravity(false);
        new LootBoxAnimation(JavaPlugin.getPlugin(IslandClash.class), this);
    }

    public ItemStack getLoot() {
        return loot;
    }

    public String getSkullTexture() {
        return SKULL_TEXTURE;
    }

    public ArmorStand getArmorStand() {
        return armorStand;
    }

    public abstract LootBoxType getType();
}
