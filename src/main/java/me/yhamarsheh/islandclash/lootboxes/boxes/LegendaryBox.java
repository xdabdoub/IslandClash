package me.yhamarsheh.islandclash.lootboxes.boxes;

import me.yhamarsheh.islandclash.lootboxes.LootBox;
import me.yhamarsheh.islandclash.lootboxes.LootBoxType;
import org.bukkit.inventory.ItemStack;

public class LegendaryBox extends LootBox {

    public LegendaryBox(ItemStack itemStack) {
        super("ewogICJ0aW1lc3RhbXAiIDogMTYwODQ4NTY5NDIzOCwKICAicHJvZmlsZUlkIiA6ICIzZmM3ZmRmOTM5NjM0YzQxOTExOTliYTNmN2NjM2ZlZCIsCiAgInByb2ZpbGVOYW1lIiA6ICJZZWxlaGEiLAogICJzaWduYXR1cmVSZXF1aXJlZCIgOiB0cnVlLAogICJ0ZXh0dXJlcyIgOiB7CiAgICAiU0tJTiIgOiB7CiAgICAgICJ1cmwiIDogImh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZTMyYTg5YjZmNDIyYjBhMzk0ZDFlY2I0MTRkN2VjMTNiZTFkMTA3NTI2YjNiZDAyNjExMzViZWFmYWQ3MDI2MSIKICAgIH0KICB9Cn0=",
                itemStack);
    }

    @Override
    public LootBoxType getType() {
        return LootBoxType.COMMON;
    }
}
