package me.yhamarsheh.islandclash.lootboxes.boxes;

import me.yhamarsheh.islandclash.lootboxes.LootBox;
import me.yhamarsheh.islandclash.lootboxes.LootBoxType;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

public class CommonBox extends LootBox {

    public CommonBox(ItemStack itemStack) {
        super("eyJ0aW1lc3RhbXAiOjE1NzUyMjEwODE4MDEsInByb2ZpbGVJZCI6IjE5MjUyMWI0ZWZkYjQyNWM4OTMxZjAyYTg0OTZlMTFiIiwicHJvZmlsZU5hbWUiOiJTZXJpYWxpemFibGUiLCJzaWduYXR1cmVSZXF1aXJlZCI6dHJ1ZSwidGV4dHVyZXMiOnsiU0tJTiI6eyJ1cmwiOiJodHRwOi8vdGV4dHVyZXMubWluZWNyYWZ0Lm5ldC90ZXh0dXJlLzJlZjUyN2ZkNjNlMTFjMWFmM2YyMTIwZmQ5MzUxNjg3NTAwNTFhYjE4ZjY4MmIyMzAyMTM3N2EwOWNlNWU2NGEifX19",
                itemStack);
    }

    @Override
    public LootBoxType getType() {
        return LootBoxType.COMMON;
    }
}
