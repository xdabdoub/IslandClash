package me.yhamarsheh.islandclash.guis;

import com.google.common.collect.Sets;
import dev.triumphteam.gui.components.InteractionModifier;
import dev.triumphteam.gui.guis.Gui;
import me.yhamarsheh.islandclash.IslandClash;
import me.yhamarsheh.islandclash.storage.objects.HPlayer;

import java.util.HashSet;
public class UpgradesGUI {
    
    private final IslandClash plugin;
    private final Gui gui;
    private final HPlayer hPlayer;

    public UpgradesGUI(IslandClash plugin, HPlayer hPlayer) {
        this.plugin = plugin;
        this.hPlayer = hPlayer;

        this.gui = new Gui(6, "Upgrades", Sets.newHashSet(InteractionModifier.PREVENT_ITEM_DROP, InteractionModifier.PREVENT_ITEM_TAKE
        , InteractionModifier.PREVENT_ITEM_PLACE, InteractionModifier.PREVENT_ITEM_SWAP));
    }

    private void setupGui() {

    }
}
