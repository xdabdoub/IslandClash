package me.yhamarsheh.islandclash.guis;

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

        this.gui = new Gui(6, "", new HashSet<>().);
    }
}
