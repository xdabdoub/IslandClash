package me.yhamarsheh.islandclash.guis;

import dev.triumphteam.gui.builder.item.ItemBuilder;
import dev.triumphteam.gui.components.InteractionModifier;
import dev.triumphteam.gui.guis.Gui;
import me.yhamarsheh.islandclash.IslandClash;
import me.yhamarsheh.islandclash.storage.objects.HPlayer;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.material.Wool;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;

public class LayoutGUI {

    private final Gui gui;
    private final IslandClash plugin;
    public LayoutGUI(Player player, IslandClash plugin) {
        this.gui = new Gui(1, "Layout Editor", Collections.unmodifiableSet(new HashSet<>(Collections.singletonList(
                InteractionModifier.PREVENT_ITEM_DROP))));
        this.plugin = plugin;

        setupGui(player);
        gui.open(player);
    }

    private void setupGui(Player player) {
        HPlayer hPlayer = plugin.getPlayer(player.getUniqueId());
        int[] layout = hPlayer.getLayout().getNLayout();

        gui.setOutsideClickAction(e -> e.setCancelled(true));
        gui.setDefaultClickAction(e -> {
            if (e.getClick() == ClickType.NUMBER_KEY) e.setCancelled(true);
            if (e.getClickedInventory() instanceof PlayerInventory) e.setCancelled(true);
        });

        gui.setItem(layout[0], ItemBuilder.from(Material.STONE_SWORD).asGuiItem());
        gui.setItem(layout[1], ItemBuilder.from(Material.WOOL).asGuiItem());
        gui.setItem(layout[2], ItemBuilder.from(Material.WOOD).asGuiItem());
        gui.setItem(layout[3], ItemBuilder.from(Material.FISHING_ROD).asGuiItem());
        gui.setItem(layout[4], ItemBuilder.from(Material.POTION).asGuiItem());
        gui.setItem(layout[5], ItemBuilder.from(Material.GOLDEN_APPLE).asGuiItem());
        gui.setItem(layout[6], ItemBuilder.from(Material.LADDER).asGuiItem());
        gui.setItem(layout[7], ItemBuilder.from(Material.EGG).asGuiItem());
        gui.setItem(layout[8], ItemBuilder.from(Material.SNOW_BALL).asGuiItem());

        gui.setCloseGuiAction(e -> {
            Inventory inventory = e.getInventory();

            if (player.getItemOnCursor() != null && player.getItemOnCursor().getType() != Material.AIR) {
                inventory.addItem(player.getItemOnCursor());
                player.setItemOnCursor(null);
            }

            for (int i = 0; i < inventory.getSize(); i++) {
                ItemStack itemStack = inventory.getItem(i);

                if (itemStack == null || itemStack.getType() == null) {
                    hPlayer.getLayout().setNLayout(new int[] {0, 1, 2, 3, 4, 5, 6, 7, 8});
                    hPlayer.sendMessage("&b&lIsland Clash &7- &cCould not update your hotbar layout, some of the items seem to be missing.");
                    return;
                }

                switch (itemStack.getType()) {
                    case STONE_SWORD:
                        layout[0] = i;
                        break;
                    case WOOL:
                        layout[1] = i;
                        break;
                    case WOOD:
                        layout[2] = i;
                        break;
                    case FISHING_ROD:
                        layout[3] = i;
                        break;
                    case POTION:
                        layout[4] = i;
                        break;
                    case GOLDEN_APPLE:
                        layout[5] = i;
                        break;
                    case LADDER:
                        layout[6] = i;
                        break;
                    case EGG:
                        layout[7] = i;
                        break;
                    case SNOW_BALL:
                        layout[8] = i;
                        break;
                    default:
                         hPlayer.getLayout().setNLayout(new int[] {0, 1, 2, 3, 4, 5, 6, 7, 8});
                         hPlayer.sendMessage("&b&lIsland Clash &7- &cCould not update your hotbar layout, some of the items seem to be missing.");
                        return;
                }
            }

            player.getInventory().clear();
            player.updateInventory();
            hPlayer.getLayout().setNLayout(layout);
            hPlayer.sendMessage("&b&lIsland Clash &7- Updated your hotbar layout!");
        });
    }
}
