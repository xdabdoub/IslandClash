package me.yhamarsheh.islandclash.managers;

import dev.triumphteam.gui.builder.item.ItemBuilder;
import me.yhamarsheh.islandclash.IslandClash;
import me.yhamarsheh.islandclash.lootboxes.LootBox;
import me.yhamarsheh.islandclash.lootboxes.LootBoxType;
import me.yhamarsheh.islandclash.lootboxes.boxes.CommonBox;
import me.yhamarsheh.islandclash.lootboxes.boxes.EpicBox;
import me.yhamarsheh.islandclash.lootboxes.boxes.LegendaryBox;
import me.yhamarsheh.islandclash.lootboxes.boxes.RareBox;
import me.yhamarsheh.islandclash.utilities.LocationsUtils;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.ArmorStand;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class LootBoxesManager {

    private IslandClash plugin;
    private ArrayList<LootBox> lootBoxes;

    private List<Location> commonLocations;
    private List<Location> rareLocations;
    private List<Location> epicLocations;
    private List<Location> legendaryLocations;

    private List<ItemStack> commonRewards;
    private List<ItemStack> rareRewards;
    private List<ItemStack> epicRewards;
    private List<ItemStack> legendaryRewards;

    public LootBoxesManager(IslandClash plugin) {
        this.plugin = plugin;
        this.lootBoxes = new ArrayList<>();

        this.commonLocations = new ArrayList<>();
        this.rareLocations = new ArrayList<>();
        this.epicLocations = new ArrayList<>();
        this.legendaryLocations = new ArrayList<>();

        this.commonRewards = new ArrayList<>();
        this.rareRewards = new ArrayList<>();
        this.epicRewards = new ArrayList<>();
        this.legendaryRewards = new ArrayList<>();

    }

    public void initLocations() {
        for (String type : plugin.getConfig().getConfigurationSection("lootboxes").getKeys(false)) {
            LootBoxType lootBoxType = LootBoxType.valueOf(type.toUpperCase());

            for (String rew : plugin.getConfig().getStringList("lootboxes." + type + ".rewards")) {
                String[] data = rew.split(";");
                ItemStack itemStack = null;

                if (data[0].equalsIgnoreCase("ENCHANTED_GOLDEN_APPLE")) {
                    itemStack = new ItemStack(Material.GOLDEN_APPLE, Integer.parseInt(data[1]), (short) 1);
                } else if (data[0].equalsIgnoreCase("KNOCKBACK_STICK")) {
                        itemStack = ItemBuilder.from(Material.STICK).amount(Integer.parseInt(data[1])).enchant(Enchantment.KNOCKBACK, 1).build();
                    } else {
                    itemStack = new ItemStack(Material.valueOf(data[0]), Integer.parseInt(data[1]));
                }

                if (lootBoxType == LootBoxType.COMMON) commonRewards.add(itemStack);
                else if (lootBoxType == LootBoxType.RARE) rareRewards.add(itemStack);
                else if (lootBoxType == LootBoxType.EPIC) epicRewards.add(itemStack);
                else if (lootBoxType == LootBoxType.LEGENDARY) legendaryRewards.add(itemStack);
            }

            for (String loc : plugin.getConfig().getStringList("lootboxes." + type + ".locations")) {
                Location location = LocationsUtils.locationFromString(loc);
                createByType(lootBoxType).spawn(location);
                getLocationsByType(lootBoxType).add(location);
            }

        }
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
                CommonBox commonBox = new CommonBox(commonRewards.get(new Random().nextInt(commonRewards.size())));
                add(commonBox);
                return commonBox;
            case RARE:
                RareBox rareBox = new RareBox(rareRewards.get(new Random().nextInt(rareRewards.size())));
                add(rareBox);
                return rareBox;
            case EPIC:
                EpicBox epicBox = new EpicBox(epicRewards.get(new Random().nextInt(epicRewards.size())));
                add(epicBox);
                return epicBox;
            case LEGENDARY:
                LegendaryBox legendaryBox = new LegendaryBox(legendaryRewards.get(new Random().nextInt(legendaryRewards.size())));
                add(legendaryBox);
                return legendaryBox;
            default:
                return null;
        }
    }

    public List<Location> getLocationsByType(LootBoxType lootBoxType) {
        switch (lootBoxType) {
            case COMMON:
                return commonLocations;
            case RARE:
                return rareLocations;
            case EPIC:
                return epicLocations;
            case LEGENDARY:
                return legendaryLocations;
            default:
                return null;
        }
    }

    public LootBox getByArmorStand(ArmorStand armorStand) {
        for (LootBox lootBox : lootBoxes) {
            if (lootBox.getArmorStand() == armorStand) return lootBox;
        }
        return null;
    }


    public boolean isLootBox(ArmorStand armorStand) {
        for (LootBox lootBox : lootBoxes) {
            if (lootBox.getArmorStand() == armorStand) return true;
        }

        return false;
    }

    public void disable() {
        for (LootBox lootBox : lootBoxes) {
            lootBox.getArmorStand().remove();
        }

        lootBoxes.clear();
        commonRewards.clear();
        rareRewards.clear();
        epicRewards.clear();
        legendaryRewards.clear();

        commonLocations.clear();
        rareLocations.clear();
        epicLocations.clear();
        legendaryLocations.clear();
    }

}
