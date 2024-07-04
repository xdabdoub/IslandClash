package me.yhamarsheh.islandclash.guis;

import com.google.common.collect.Sets;
import dev.triumphteam.gui.builder.item.ItemBuilder;
import dev.triumphteam.gui.components.InteractionModifier;
import dev.triumphteam.gui.guis.Gui;
import me.yhamarsheh.islandclash.IslandClash;
import me.yhamarsheh.islandclash.game.upgrades.*;
import me.yhamarsheh.islandclash.storage.objects.HPlayer;
import me.yhamarsheh.islandclash.utilities.ChatUtils;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;

import java.util.HashSet;
public class UpgradesGUI {
    
    private final IslandClash plugin;
    private final Gui gui;
    private final HPlayer hPlayer;

    public UpgradesGUI(IslandClash plugin, HPlayer hPlayer) {
        this.plugin = plugin;
        this.hPlayer = hPlayer;

        this.gui = new Gui(4, "Upgrades", Sets.newHashSet(InteractionModifier.VALUES));
        setupGui();
        gui.open(hPlayer.getPlayer());
    }

    private void setupGui() {
        if (hPlayer.hasSharpnessUpgrade()) {
            PlayerUpgrade sharp = hPlayer.getPlayerUpgrades().getSharpnessUpgrade();
            boolean maxedOut = sharp.getMaxLevel() == sharp.getLevel();
            String level = intToRoman(sharp.getLevel() + 1);

            if (!maxedOut) {
                gui.setItem(11, ItemBuilder.from(Material.STONE_SWORD)
                        .name(ChatUtils.component("&bSharpened Swords " + level))
                        .lore(ChatUtils.component("&7You gain sharpness " + level + " on all"),
                                ChatUtils.component("&7swords!"),
                                ChatUtils.component("&7"),
                                ChatUtils.component("&7Cost: &b$" + sharp.upgradeCost() * (sharp.getLevel() + 1))).asGuiItem(e -> {
                            sharp.upgrade(hPlayer);
                        }));
            } else {
                gui.setItem(11, ItemBuilder.from(Material.STONE_SWORD)
                        .name(ChatUtils.component("&bSharpened Swords I"))
                        .lore(ChatUtils.component("&7You gain sharpness I" + " on all"),
                                ChatUtils.component("&7swords!"),
                                ChatUtils.component("&7"),
                                ChatUtils.component("&cMaxed out!")).asGuiItem(e -> {
                                    e.setCancelled(true);
                        }));
            }
        } else {
            gui.setItem(11, ItemBuilder.from(Material.STONE_SWORD)
                    .name(ChatUtils.component("&bSharpened Swords I"))
                    .lore(ChatUtils.component("&7You gain sharpness I on all"),
                            ChatUtils.component("&7swords!"),
                            ChatUtils.component("&7"),
                            ChatUtils.component("&7Cost: &b$" + 750)).asGuiItem(e -> {
                        SharpnessUpgrade sharpnessUpgrade = new SharpnessUpgrade();
                        hPlayer.getPlayerUpgrades().setSharpnessUpgrade(sharpnessUpgrade);
                        sharpnessUpgrade.upgrade(hPlayer);
                    }));
        }

        if (hPlayer.hasProtectionUpgrade()) {
            PlayerUpgrade prot = hPlayer.getPlayerUpgrades().getProtectionUpgrade();
            boolean maxedOut = prot.getMaxLevel() == prot.getLevel();
            String level = intToRoman(prot.getLevel() + 1);

            if (!maxedOut) {
                gui.setItem(12, ItemBuilder.from(Material.IRON_CHESTPLATE)
                        .name(ChatUtils.component("&bReinforced Armor " + level))
                        .lore(ChatUtils.component("&7You gain Protection " + level + " on all"),
                                ChatUtils.component("&7armor pieces!"),
                                ChatUtils.component("&7"),
                                ChatUtils.component("&7Cost: &b$" + prot.upgradeCost() * (prot.getLevel() + 1)),
                                ChatUtils.component("&7"),
                                ChatUtils.component("&bClick to purchase")).asGuiItem(e -> {
                            prot.upgrade(hPlayer);
                        }));
            } else {
                gui.setItem(12, ItemBuilder.from(Material.IRON_CHESTPLATE)
                        .name(ChatUtils.component("&bReinforced Armor II"))
                        .lore(ChatUtils.component("&7You gain Protection II" + " on all"),
                                ChatUtils.component("&7armor pieces!"),
                                ChatUtils.component("&7"),
                                ChatUtils.component("&cMaxed out!")).asGuiItem(e -> {
                            e.setCancelled(true);
                        }));
            }
        } else {
            gui.setItem(12, ItemBuilder.from(Material.IRON_CHESTPLATE)
                    .name(ChatUtils.component("&bReinforced Armor I"))
                    .lore(ChatUtils.component("&7You gain Protection I on all"),
                            ChatUtils.component("&7armor pieces!"),
                            ChatUtils.component("&7"),
                            ChatUtils.component("&7Cost: &b$" + 500),
                            ChatUtils.component("&7"),
                            ChatUtils.component("&bClick to purchase")).asGuiItem(e -> {
                        ProtectionUpgrade protectionUpgrade = new ProtectionUpgrade();
                        hPlayer.getPlayerUpgrades().setProtectionUpgrade(protectionUpgrade);
                        protectionUpgrade.upgrade(hPlayer);
                    }));
        }


        if (hPlayer.hasHasteUpgrade()) {
            PlayerUpgrade haste = hPlayer.getPlayerUpgrades().getHasteUpgrade();
            boolean maxedOut = haste.getMaxLevel() == haste.getLevel();
            String level = intToRoman(haste.getLevel() + 1);

            if (!maxedOut) {
                gui.setItem(13, ItemBuilder.from(Material.GOLD_PICKAXE)
                        .name(ChatUtils.component("&bHaste " + level))
                        .lore(ChatUtils.component("&7You gain haste " + level),
                                ChatUtils.component("&7"),
                                ChatUtils.component("&7Cost: &b$" + haste.upgradeCost() * (haste.getLevel() + 1)),
                                ChatUtils.component("&7"),
                                ChatUtils.component("&bClick to purchase")).asGuiItem(e -> {
                            haste.upgrade(hPlayer);
                        }));
            } else {
                gui.setItem(13, ItemBuilder.from(Material.GOLD_PICKAXE)
                        .name(ChatUtils.component("&bHaste II"))
                        .lore(ChatUtils.component("&7You gain haste II"),
                                ChatUtils.component("&7"),
                                ChatUtils.component("&cMaxed out!")).asGuiItem(e -> {
                            e.setCancelled(true);
                        }));
            }
        } else {
            gui.setItem(13, ItemBuilder.from(Material.GOLD_PICKAXE)
                    .name(ChatUtils.component("&bHaste I"))
                    .lore(ChatUtils.component("&7You gain haste I"),
                            ChatUtils.component("&7"),
                            ChatUtils.component("&7Cost: &b$" + 200),
                            ChatUtils.component("&7"),
                            ChatUtils.component("&bClick to purchase")).asGuiItem(e -> {
                        HasteUpgrade hasteUpgrade = new HasteUpgrade();
                        hPlayer.getPlayerUpgrades().setHasteUpgrade(hasteUpgrade);
                        hasteUpgrade.upgrade(hPlayer);
                    }));
        }

        if (hPlayer.hasSpeedUpgrade()) {
            PlayerUpgrade speed = hPlayer.getPlayerUpgrades().getSpeedUpgrade();
            boolean maxedOut = speed.getMaxLevel() == speed.getLevel();
            String level = intToRoman(speed.getLevel() + 1);

            if (!maxedOut) {
                gui.setItem(14, ItemBuilder.from(Material.POTION)
                        .name(ChatUtils.component("&bSwiftness " + level))
                        .lore(ChatUtils.component("&7You gain speed " + level),
                                ChatUtils.component("&7"),
                                ChatUtils.component("&7Cost: &b$" + speed.upgradeCost() * (speed.getLevel() + 1)),
                                ChatUtils.component("&7"),
                                ChatUtils.component("&bClick to purchase")).asGuiItem(e -> {
                            speed.upgrade(hPlayer);
                        }));
            } else {
                gui.setItem(14, ItemBuilder.from(Material.POTION)
                        .name(ChatUtils.component("&bSwiftness II"))
                        .lore(ChatUtils.component("&7You gain speed II"),
                                ChatUtils.component("&7"),
                                ChatUtils.component("&cMaxed out!")).asGuiItem(e -> {
                            e.setCancelled(true);
                        }));
            }
        } else {
        gui.setItem(14, ItemBuilder.from(Material.POTION)
                .name(ChatUtils.component("&bSwiftness I"))
                .lore(ChatUtils.component("&7You gain speed I"),
                        ChatUtils.component("&7"),
                        ChatUtils.component("&7Cost: &b$" + 300),
                        ChatUtils.component("&7"),
                        ChatUtils.component("&bClick to purchase")).asGuiItem(e -> {
                    SpeedUpgrade speedUpgrade = new SpeedUpgrade();
                    hPlayer.getPlayerUpgrades().setSpeedUpgrade(speedUpgrade);
                    speedUpgrade.upgrade(hPlayer);
                }));
    }

        gui.setItem(15, ItemBuilder.from(Material.BARRIER).name(ChatUtils.component("&csoon!")).asGuiItem(e -> e.setCancelled(true)));

        gui.setItem(35, ItemBuilder.from(Material.DIAMOND).name(ChatUtils.component("&bSession Statistics"))
                .lore(ChatUtils.component("&7Hyions: &b$" + hPlayer.getSessionalStatistics().getHyions()),
                        ChatUtils.component("&7Kills: &b" + hPlayer.getSessionalStatistics().getKills())).asGuiItem(e -> e.setCancelled(true)));

    }

    private String intToRoman(int i) {
        if (i == 1) return "I";
        if (i == 2) return "II";
        if (i == 3) return "III";

        return "F";
    }
}
