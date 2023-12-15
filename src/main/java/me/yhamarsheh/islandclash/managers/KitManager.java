package me.yhamarsheh.islandclash.managers;

import dev.triumphteam.gui.builder.item.ItemBuilder;
import me.yhamarsheh.islandclash.IslandClash;
import me.yhamarsheh.islandclash.storage.objects.HPlayer;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionType;

import java.util.Random;

public class KitManager {

    private IslandClash plugin;
    public KitManager(IslandClash plugin) {
        this.plugin = plugin;
    }

    public void giveKit(HPlayer hPlayer) {
        Player player = hPlayer.getPlayer();
        PlayerInventory inventory = player.getInventory();

        ItemBuilder sword = ItemBuilder.from(Material.STONE_SWORD);
        if (hPlayer.hasSharpnessUpgrade()) {
            sword.enchant(Enchantment.DAMAGE_ALL, 1);
        }


        ItemBuilder helmet = ItemBuilder.from(Material.LEATHER_HELMET).color(Color.AQUA);
        ItemBuilder chest = ItemBuilder.from(Material.LEATHER_CHESTPLATE).color(Color.AQUA);
        ItemBuilder leggings = ItemBuilder.from(Material.CHAINMAIL_LEGGINGS);
        ItemBuilder boots = ItemBuilder.from(Material.CHAINMAIL_BOOTS);

        if (hPlayer.hasProtectionUpgrade()) {
            helmet.enchant(Enchantment.PROTECTION_ENVIRONMENTAL, hPlayer.getPlayerUpgrades().getProtectionUpgrade().getLevel());
            chest.enchant(Enchantment.PROTECTION_ENVIRONMENTAL, hPlayer.getPlayerUpgrades().getProtectionUpgrade().getLevel());
            leggings.enchant(Enchantment.PROTECTION_ENVIRONMENTAL, hPlayer.getPlayerUpgrades().getProtectionUpgrade().getLevel());
            boots.enchant(Enchantment.PROTECTION_ENVIRONMENTAL, hPlayer.getPlayerUpgrades().getProtectionUpgrade().getLevel());
        }

        inventory.setItem(0, sword.build());
        inventory.setHelmet(helmet.build());
        inventory.setChestplate(chest.build());
        inventory.setLeggings(leggings.build());
        inventory.setBoots(boots.build());

        Random random = new Random();

        ItemStack potion = new ItemStack(Material.POTION);
        PotionMeta potionMeta = (PotionMeta) potion.getItemMeta();
        if (potionMeta != null) {
            potionMeta.setBasePotionType(PotionType.INSTANT_HEAL);
            potion.setItemMeta(potionMeta);
            inventory.setItem(4, potion);
        }

        inventory.setItem(1, ItemBuilder.from(getRandomWool(random)).amount(64).build());
        inventory.setItem(2, ItemBuilder.from(getRandomWool(random)).amount(64).build());
        inventory.setItem(3, ItemBuilder.from(Material.SNOWBALL).amount(16).build());
        inventory.setItem(5, ItemBuilder.from(Material.GOLDEN_APPLE).amount(2).build());
        inventory.setItem(6, ItemBuilder.from(Material.LADDER).amount(64).build());
        inventory.setItem(7, ItemBuilder.from(Material.EGG).amount(16).build());
    }

    private Material getRandomWool(Random random) {
        int r = random.nextInt(15);
        Material wool = Material.WHITE_WOOL;

        switch (r) {
            case 0:
                wool = Material.BLUE_WOOL;
                break;
            case 1:
                wool = Material.RED_WOOL;
                break;
            case 2:
                wool = Material.BROWN_WOOL;
                break;
            case 3:
                wool = Material.WHITE_WOOL;
                break;
            case 4:
                wool = Material.PINK_WOOL;
                break;
            case 5:
                wool = Material.PURPLE_WOOL;
                break;
            case 6:
                wool = Material.GRAY_WOOL;
                break;
            case 7:
                wool = Material.CYAN_WOOL;
                break;
            case 8:
                wool = Material.LIME_WOOL;
                break;
            case 9:
                wool = Material.GREEN_WOOL;
                break;
            default:
                wool = Material.WHITE_WOOL;
                break;
        }

        return wool;
    }
}