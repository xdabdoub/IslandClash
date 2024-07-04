package me.yhamarsheh.islandclash.managers;

import me.yhamarsheh.islandclash.IslandClash;
import me.yhamarsheh.islandclash.storage.objects.HPlayer;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayersManager {

    private final IslandClash plugin;
    private Map<UUID, HPlayer> playerMap;

    private DamageManager damageManager;

    public PlayersManager(IslandClash plugin) {
        this.plugin = plugin;
        this.playerMap = new HashMap<>();
        this.damageManager = new DamageManager();
    }

    public void disable() {
        for (HPlayer hPlayer : playerMap.values()) {
            hPlayer.saveData();
        }

        playerMap.clear();
        damageManager.disable();
    }

    public void addPlayer(UUID uuid, HPlayer hPlayer) {
        playerMap.put(uuid, hPlayer);
    }

    public void removePlayer(UUID uuid) {
        playerMap.remove(uuid);
    }

    public Map<UUID, HPlayer> getPlayerMap() {
        return playerMap;
    }
    public DamageManager getDamageManager() {
        return damageManager;
    }

    public class DamageManager {
        private Map<HPlayer, HPlayer> damageMap;

        public DamageManager() {
            this.damageMap = new HashMap<>();
        }

        public void disable() {
            damageMap.clear();
        }

        public void handle(HPlayer damager, HPlayer damaged) {
            if (damageMap.containsValue(damaged) &&
                    damageMap.get(damager) == damaged) return;

            if (damageMap.containsValue(damaged)) {
                for (HPlayer hPlayer : damageMap.keySet()) {
                    if (damageMap.get(hPlayer) == damaged) {
                        damageMap.remove(hPlayer);
                        damageMap.put(damager, damaged);
                        return;
                    }
                }
                return;
            }

            damageMap.put(damager, damaged);
        }

        public void remove(HPlayer hPlayer) {
            damageMap.remove(hPlayer);
        }

        public HPlayer getLastDamager(HPlayer hPlayer) {
            for (HPlayer hDamager : damageMap.keySet()) {
                if (damageMap.get(hDamager) == hPlayer) {
                    return hDamager;
                }
            }
            return null;
        }
    }
}
