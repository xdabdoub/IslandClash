package me.yhamarsheh.islandclash.lootboxes.boxes;

import me.yhamarsheh.islandclash.lootboxes.LootBox;
import me.yhamarsheh.islandclash.lootboxes.LootBoxType;
import org.bukkit.inventory.ItemStack;

public class EpicBox extends LootBox {

    public EpicBox(ItemStack itemStack) {
        super("ewogICJ0aW1lc3RhbXAiIDogMTYwODQ4NTc3NDk0OSwKICAicHJvZmlsZUlkIiA6ICI5MThhMDI5NTU5ZGQ0Y2U2YjE2ZjdhNWQ1M2VmYjQxMiIsCiAgInByb2ZpbGVOYW1lIiA6ICJCZWV2ZWxvcGVyIiwKICAic2lnbmF0dXJlUmVxdWlyZWQiIDogdHJ1ZSwKICAidGV4dHVyZXMiIDogewogICAgIlNLSU4iIDogewogICAgICAidXJsIiA6ICJodHRwOi8vdGV4dHVyZXMubWluZWNyYWZ0Lm5ldC90ZXh0dXJlL2FkNGQ1YTk3MWJhN2I3OGNmMTQxYjNlMWMyNjgxZWMyYWQwZTMyYjhjODE2YzQ3NWM5MWRhNGY4NTVmYzEyZiIKICAgIH0KICB9Cn0=",
                itemStack);
    }

    @Override
    public LootBoxType getType() {
        return LootBoxType.COMMON;
    }
}
