package me.yhamarsheh.islandclash.managers;

import me.yhamarsheh.islandclash.IslandClash;
import me.yhamarsheh.islandclash.lootboxes.LootBox;
import me.yhamarsheh.islandclash.lootboxes.LootBoxType;
import me.yhamarsheh.islandclash.lootboxes.boxes.CommonBox;

import java.util.ArrayList;

public class LootBoxesManager {

    private IslandClash plugin;
    private ArrayList<LootBox> lootBoxes;
    public LootBoxesManager(IslandClash plugin) {
        this.plugin = plugin;
        this.lootBoxes = new ArrayList<>();
    }

    public void add(LootBox lootBox) {
        lootBoxes.add(lootBox);
    }

    public void remove(LootBox lootBox) {
        lootBoxes.remove(lootBox);
    }

    public LootBox createByType(LootBoxType lootBoxType) {
        switch (lootBoxType) {
            case COMMON:
                return new CommonBox();
            default:
                return null;
        }
    }

}
