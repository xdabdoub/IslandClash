package me.yhamarsheh.islandclash.managers;

import dev.triumphteam.gui.builder.item.ItemBuilder;
import me.yhamarsheh.islandclash.IslandClash;
import me.yhamarsheh.islandclash.storage.objects.HPlayer;
import org.bukkit.Color;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.material.Wool;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionEffectType;
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

        ItemBuilder sword = ItemBuilder.from(Material.STONE_SWORD)
                .unbreakable();
        if (hPlayer.hasSharpnessUpgrade()) {
            sword.enchant(Enchantment.DAMAGE_ALL, 1);
        }


        ItemBuilder helmet = ItemBuilder.from(Material.LEATHER_HELMET).color(Color.AQUA).unbreakable();
        ItemBuilder chest = ItemBuilder.from(Material.LEATHER_CHESTPLATE).color(Color.AQUA).unbreakable();
        ItemBuilder leggings = ItemBuilder.from(Material.IRON_LEGGINGS).unbreakable();
        ItemBuilder boots = ItemBuilder.from(Material.IRON_BOOTS).unbreakable();

        if (hPlayer.hasProtectionUpgrade()) {
            helmet.enchant(Enchantment.PROTECTION_ENVIRONMENTAL, hPlayer.getPlayerUpgrades().getProtectionUpgrade().getLevel());
            chest.enchant(Enchantment.PROTECTION_ENVIRONMENTAL, hPlayer.getPlayerUpgrades().getProtectionUpgrade().getLevel());
            leggings.enchant(Enchantment.PROTECTION_ENVIRONMENTAL, hPlayer.getPlayerUpgrades().getProtectionUpgrade().getLevel());
            boots.enchant(Enchantment.PROTECTION_ENVIRONMENTAL, hPlayer.getPlayerUpgrades().getProtectionUpgrade().getLevel());
        }

        int[] layout = hPlayer.getLayout().getNLayout();

        inventory.setItem(layout[0], sword.build());
        inventory.setHelmet(helmet.build());
        inventory.setChestplate(chest.build());
        inventory.setLeggings(leggings.build());
        inventory.setBoots(boots.build());

        Random random = new Random();

        ItemStack item = new ItemStack(Material.POTION, 1, (short) 8229);
        Potion pot = new Potion(1);
        pot.setType(PotionType.INSTANT_HEAL);
        pot.apply(item);
        inventory.setItem(layout[4], item);

        inventory.setItem(layout[1], ItemBuilder.from(getRandomWoolColor()).amount(64).build());
        inventory.setItem(layout[2], ItemBuilder.from(getRandomWoolColor()).amount(64).build());
        inventory.setItem(layout[3], ItemBuilder.from(Material.FISHING_ROD).unbreakable().build());
        inventory.setItem(layout[5], ItemBuilder.from(Material.GOLDEN_APPLE).amount(2).build());
        inventory.setItem(layout[6], ItemBuilder.from(Material.LADDER).amount(16).build());
        inventory.setItem(layout[7], ItemBuilder.from(Material.EGG).amount(16).build());
        inventory.setItem(layout[8], ItemBuilder.from(Material.SNOW_BALL).amount(16).build());
    }

    private ItemStack getRandomWoolColor() {
        DyeColor[] colors = DyeColor.values();
        DyeColor dye = colors[new Random().nextInt(colors.length)];

        Wool wool = new Wool(dye);
        return wool.toItemStack();
    }
}