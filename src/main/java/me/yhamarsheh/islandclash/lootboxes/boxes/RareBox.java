package me.yhamarsheh.islandclash.lootboxes.boxes;

import me.yhamarsheh.islandclash.lootboxes.LootBox;
import me.yhamarsheh.islandclash.lootboxes.LootBoxType;
import org.bukkit.inventory.ItemStack;

public class RareBox extends LootBox {

    public RareBox(ItemStack itemStack) {
        super("ewogICJ0aW1lc3RhbXAiIDogMTU5NzM3OTY1MDk5OSwKICAicHJvZmlsZUlkIiA6ICJkZTU3MWExMDJjYjg0ODgwOGZlN2M5ZjQ0OTZlY2RhZCIsCiAgInByb2ZpbGVOYW1lIiA6ICJNSEZfTWluZXNraW4iLAogICJzaWduYXR1cmVSZXF1aXJlZCIgOiB0cnVlLAogICJ0ZXh0dXJlcyIgOiB7CiAgICAiU0tJTiIgOiB7CiAgICAgICJ1cmwiIDogImh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNDk2NDA2NzljZWYyMDA5OTFhNTI1Nzg2ZjMyZmVhOWU0Y2FhZjUxMWM4MDM2MzcxNTBhZDYyMGRkMjk1NjJkZiIKICAgIH0KICB9Cn0=",
                itemStack);
    }

    @Override
    public LootBoxType getType() {
        return LootBoxType.COMMON;
    }
}
