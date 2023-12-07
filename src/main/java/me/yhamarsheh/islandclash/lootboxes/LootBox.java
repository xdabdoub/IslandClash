package me.yhamarsheh.islandclash.lootboxes;

import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Random;

public abstract class LootBox {

    private final String SKULL_TEXTURE;
    private List<ItemStack> loot;

    public LootBox(String SKULL_TEXTURE, List<ItemStack> loot) {
        this.SKULL_TEXTURE = SKULL_TEXTURE;
        this.loot = loot;
    }

    public ItemStack getRandomLoot() {
        return loot.get(new Random().nextInt(loot.size()));
    }

    public String getSKULL_TEXTURE() {
        return SKULL_TEXTURE;
    }
}
