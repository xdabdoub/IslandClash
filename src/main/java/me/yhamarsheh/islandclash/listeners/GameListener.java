package me.yhamarsheh.islandclash.listeners;

import com.mewin.WGRegionEvents.events.RegionEnterEvent;
import dev.triumphteam.gui.builder.item.ItemBuilder;
import me.clip.placeholderapi.PlaceholderAPI;
import me.yhamarsheh.islandclash.IslandClash;
import me.yhamarsheh.islandclash.leveling.Rank;
import me.yhamarsheh.islandclash.locale.Messages;
import me.yhamarsheh.islandclash.lootboxes.LootBox;
import me.yhamarsheh.islandclash.storage.objects.HPlayer;
import me.yhamarsheh.islandclash.utilities.ChatUtils;
import me.yhamarsheh.islandclash.utilities.RewardsUtils;
import net.minecraft.server.v1_8_R3.*;
import org.bukkit.*;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_8_R3.CraftServer;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.*;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Random;

public class GameListener implements Listener {

    private IslandClash plugin;
    public GameListener(IslandClash plugin) {
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent e) {
        Entity damaged = e.getEntity();
        Entity damager = e.getDamager();

        if (!(damaged instanceof Player)) return;
        if (!(damager instanceof Player)) return;

        Player player = (Player) damaged;
        Player pDamager = (Player) damager;

        HPlayer hPlayer = plugin.getPlayersManager().getPlayerMap().get(player.getUniqueId());
        HPlayer hDamager = plugin.getPlayersManager().getPlayerMap().get(pDamager.getUniqueId());

        plugin.getPlayersManager().getDamageManager().handle(hDamager, hPlayer);

        if (player.getHealth() - e.getDamage() < 1) {
            e.setCancelled(true);

            player.teleport(plugin.getGameManager().getGame().getSpawnLocation());
            hPlayer.addDeath();
            hDamager.addKill();

            hDamager.addXP((hDamager.getStreak() > 1 ? 10 : 5));
            int xp = hDamager.getXP();
            if (xp >= 1000) {
                // Call the ICPlayerLevelUpEvent
                int oldLevel = hDamager.getLevel();

                int left = 1000 - xp;
                if (left < 0) {
                    left = ~left;
                }

                hDamager.addLevel();
                hDamager.setXP(left);

                if (hDamager.getLevel() == 101) {
                    Rank oldRank = hDamager.getRank();
                    if (hDamager.rankUp()) {

                        hDamager.sendMessage(String.format(Messages.RANKUP_MESSAGE.toString(),
                                oldRank.toString(), hDamager.getRank().toString()));
                    }
                }

                hDamager.sendMessage(String.format(Messages.LEVEL_UP_MESSAGE.toString(),
                        oldLevel, hDamager.getLevel()));
                RewardsUtils.giveLevelingUpRewards(hDamager);
            }

            // Call the ICPlayerKillEvent
            // Call the ICPlayerDeathEvent - Cause.PLAYER

            plugin.getPlayersManager().getDamageManager().remove(hDamager);

            String killerRank = extractColorCode(PlaceholderAPI.setPlaceholders(pDamager, "%luckperms_prefix%"));
            String killedRank = extractColorCode(PlaceholderAPI.setPlaceholders(player, "%luckperms_prefix%"));

            plugin.getGameManager().getGame().announceMessage(String.format(Messages.KILL.toString(),
                    killedRank, hPlayer.getName(), killerRank, hDamager.getName(), (hDamager.getStreak() >= 5 ? String.format(Messages.STREAK.toString(), hDamager.getStreak()) : "")));
        }
    }

    @EventHandler
    public void onFall(PlayerMoveEvent e) {
        Player player = e.getPlayer();
        if (player.getLocation().getY() > 25) return;

        HPlayer hPlayer = plugin.getPlayersManager().getPlayerMap().get(player.getUniqueId());
        HPlayer lastDamager = plugin.getPlayersManager().getDamageManager().getLastDamager(hPlayer);

        hPlayer.teleport(plugin.getGameManager().getGame().getSpawnLocation());

        String killedRank = extractColorCode(PlaceholderAPI.setPlaceholders(player, "%luckperms_prefix%"));

        if (lastDamager == null) {
            // Call the ICPlayerDeathEvent - Cause.VOID

            hPlayer.addDeath();

            plugin.getGameManager().getGame().announceMessage(String.format(Messages.FELL_INTO_THE_VOID.toString(),
                    killedRank, hPlayer.getName()));
            return;
        }
        hPlayer.addDeath();

        lastDamager.addXP((lastDamager.getStreak() > 1 ? 10 : 1));
        int xp = lastDamager.getXP();
        if (xp >= 1000) {
            // Call the ICPlayerLevelUpEvent
            int oldLevel = lastDamager.getLevel();

            int left = 1000 - xp;
            if (left < 0) {
                left = ~left;
            }

            lastDamager.addLevel();
            lastDamager.setXP(left);

            if (lastDamager.getLevel() == 101) {
                Rank oldRank = lastDamager.getRank();
                if (lastDamager.rankUp()) {

                    lastDamager.sendMessage(String.format(Messages.RANKUP_MESSAGE.toString(),
                            oldRank.toString(), lastDamager.getRank().toString()));
                }
            }

            lastDamager.sendMessage(String.format(Messages.LEVEL_UP_MESSAGE.toString(),
                    oldLevel, lastDamager.getLevel()));
            RewardsUtils.giveLevelingUpRewards(lastDamager);
        }

        // Call the ICPlayerKillEvent
        // Call the ICPlayerDeathEvent - Cause.PLAYER

        String killerRank = extractColorCode(PlaceholderAPI.setPlaceholders(lastDamager.getPlayer(), "%luckperms_prefix%"));
        plugin.getGameManager().getGame().announceMessage(String.format(Messages.PUSHED_INTO_THE_VOID.toString(),
                killedRank, hPlayer.getName(), killerRank, lastDamager.getName(), (lastDamager.getStreak() > 5 ?
                        String.format(Messages.STREAK.toString(), lastDamager.getStreak()) : "")));

        plugin.getPlayersManager().getDamageManager().remove(lastDamager);
    }

    @EventHandler
    public void onDrop(PlayerDropItemEvent e) {
        Player player = e.getPlayer();
        if (player.hasPermission("islandclash.admin")) return;

        e.setCancelled(true);
    }

    @EventHandler
    public void onDropToArena(RegionEnterEvent e) {
        Player player = e.getPlayer();
        if (!e.getRegion().getId().equals("drop")) return;

        HPlayer hPlayer = plugin.getPlayersManager().getPlayerMap().get(player.getUniqueId());
        plugin.getKitManager().giveKit(hPlayer);
    }

    @EventHandler
    public void onWeatherChange(WeatherChangeEvent e) {
        e.setCancelled(true);
    }

    @EventHandler
    public void onHunger(FoodLevelChangeEvent e) {
        e.setCancelled(true);
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent e) {
        Player player = e.getPlayer();
        Block block = e.getBlock();

        if (player.hasPermission("islandclash.admin") && player.getGameMode() != GameMode.SURVIVAL) return;
        plugin.getBlocks().add(block);
        displayBlockBreakAnimation(block);
    }

    @EventHandler
    public void onInteractionWithLootBox(PlayerInteractAtEntityEvent e) {
        Entity entity = e.getRightClicked();
        Player player = e.getPlayer();

        if (!(entity instanceof ArmorStand)) return;
        ArmorStand armorStand = (ArmorStand) e.getRightClicked();

        if (!plugin.getLootBoxesManager().isLootBox(armorStand)) return;
        e.setCancelled(true);

        player.playSound(player.getLocation(), Sound.EXPLODE, 1f, 1f);
        player.playEffect(armorStand.getEyeLocation(), Effect.EXPLOSION_HUGE, 1);

        LootBox lootBox = plugin.getLootBoxesManager().getByArmorStand(armorStand);
        player.getWorld().dropItemNaturally(armorStand.getLocation(), lootBox.getLoot());

        armorStand.remove();
        plugin.getLootBoxesManager().remove(lootBox);

        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            plugin.getLootBoxesManager().createByType(lootBox.getType()).spawn(armorStand.getLocation());
        },20L * 350);
    }

    @EventHandler
    public void onPickUp(PlayerPickupItemEvent e) {
        Player player = e.getPlayer();
        Item item = e.getItem();
        if (item == null || item.getType() == null) return;

        ItemStack itemStack = e.getItem().getItemStack();
        if (itemStack == null || itemStack.getType() == Material.AIR) return;
        HPlayer hPlayer = plugin.getPlayer(player.getUniqueId());

        if (itemStack.getType().name().contains("SWORD")) {
            ItemBuilder sword = ItemBuilder.from(itemStack)
                    .unbreakable();
            if (hPlayer.hasSharpnessUpgrade()) {
                sword.enchant(Enchantment.DAMAGE_ALL, 1);
            }

            e.getItem().setItemStack(sword.build());
        } else if (itemStack.getType().name().contains("HELMET") || itemStack.getType().name().contains("LEGGINGS")
        || itemStack.getType().name().contains("CHESTPLATE") || itemStack.getType().name().contains("BOOTS")) {
            ItemBuilder armor = ItemBuilder.from(itemStack).unbreakable();

            if (hPlayer.hasProtectionUpgrade()) {
                armor.enchant(Enchantment.PROTECTION_ENVIRONMENTAL, hPlayer.getPlayerUpgrades().getProtectionUpgrade().getLevel());
            }

            e.getItem().setItemStack(armor.build());
        }

    }

    private void displayBlockBreakAnimation(Block placedBlock) {
        new BukkitRunnable() {
            int a = 0;
            int i = 0;

            @Override
            public void run() {
                i += 2;
                a++;

                PacketPlayOutBlockBreakAnimation packet = new PacketPlayOutBlockBreakAnimation(new Random().nextInt(5000), new BlockPosition(placedBlock.getX(), placedBlock.getY(), placedBlock.getZ()), i);

                for (Player p : placedBlock.getLocation().getWorld().getPlayers()) {
                    if (p.getLocation().distance(placedBlock.getLocation()) < 64) {
                        ((org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);
                    }
                }

                if (a == 5) {
                    this.cancel();
                    placedBlock.setType(Material.AIR);
                    plugin.getBlocks().remove(placedBlock);
                }
            }
        }.runTaskTimer(plugin, 0, 15);
    }

    @EventHandler
    public void onBreak(BlockBreakEvent e) {
        Player player = e.getPlayer();
        if (player.hasPermission("islandclash.admin") && player.getGameMode() != GameMode.SURVIVAL) return;
        e.setCancelled(true);
    }

    private String extractColorCode(String rank) {
        char[] characters = rank.toCharArray();
        if (characters.length >= 2) {
            return "&" + characters[1];
        } else {
            return "&7";
        }
    }
}
