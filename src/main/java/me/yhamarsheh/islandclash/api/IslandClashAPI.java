package me.yhamarsheh.islandclash.api;

import me.yhamarsheh.islandclash.game.Game;
import me.yhamarsheh.islandclash.storage.objects.HPlayer;

import java.util.UUID;

public interface IslandClashAPI {

    HPlayer getPlayer(UUID uuid);
    Game getActiveGame();
}
