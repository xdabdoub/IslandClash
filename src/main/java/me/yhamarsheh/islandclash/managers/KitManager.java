package me.yhamarsheh.islandclash.managers;

import dev.triumphteam.gui.builder.item.ItemBuilder;
import me.yhamarsheh.islandclash.IslandClash;
import me.yhamarsheh.islandclash.storage.objects.HPlayer;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;

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

        inventory.setItem(0, sword.build());

        ItemBuilder helmet = ItemBuilder.from(Material.CHAINMAIL_HELMET);
        ItemBuilder chest = ItemBuilder.from(Material.CHAINMAIL_CHESTPLATE);
        ItemBuilder leggings = ItemBuilder.from(Material.CHAINMAIL_LEGGINGS);
        ItemBuilder boots = ItemBuilder.from(Material.CHAINMAIL_BOOTS);

        if (hPlayer.hasProtectionUpgrade()) {
            helmet.enchant(Enchantment.PROTECTION_ENVIRONMENTAL, hPlayer.getPlayerUpgrades().getProtectionUpgrade().getLevel());
            chest.enchant(Enchantment.PROTECTION_ENVIRONMENTAL, hPlayer.getPlayerUpgrades().getProtectionUpgrade().getLevel());
            leggings.enchant(Enchantment.PROTECTION_ENVIRONMENTAL, hPlayer.getPlayerUpgrades().getProtectionUpgrade().getLevel());
            boots.enchant(Enchantment.PROTECTION_ENVIRONMENTAL, hPlayer.getPlayerUpgrades().getProtectionUpgrade().getLevel());
        }
    }
}