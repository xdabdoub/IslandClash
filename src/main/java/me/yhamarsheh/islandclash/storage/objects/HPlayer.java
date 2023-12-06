package me.yhamarsheh.islandclash.storage.objects;

import me.yhamarsheh.islandclash.IslandClash;
import me.yhamarsheh.islandclash.leveling.Rank;
import me.yhamarsheh.islandclash.storage.SQLDatabase;
import me.yhamarsheh.islandclash.utilities.ChatUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class HPlayer {

    // Main Attributes
    private final IslandClash plugin;
    private UUID uuid;
    private Player player;
    private final SQLDatabase sql;

    // Player Statistics
    private int kills;
    private int deaths;
    private int streak;
    private int collectedBoxes;
    private Rank rank;
    private int level;
    private int xp;
    private int hyions;

    public HPlayer(IslandClash plugin, UUID uuid) {
        this.plugin = plugin;
        this.rank = Rank.UNRANKED;

        this.sql = plugin.getSQLDatabase();
        Bukkit.getScheduler().runTaskAsynchronously(plugin, this::create);
    }

    /*
     * Class Functions
     */
    public void addKill() {
        setKills(kills + 1);
        addStreak();
    }

    public void setKills(int kills) {
        this.kills = kills;
    }

    public int getKills() {
        return kills;
    }

    public void addDeath() {
        setDeaths(deaths + 1);
        setStreak(0);
    }

    public void setDeaths(int deaths) {
        this.deaths = deaths;
    }

    public int getDeaths() {
        return deaths;
    }

    public void addStreak() {
        setStreak(streak + 1);
    }

    public void setStreak(int streak) {
        this.streak = streak;
    }

    public int getStreak() {
        return streak;
    }

    public void addCollectedBox() {
        setCollectedBoxes(collectedBoxes + 1);
    }

    public void setCollectedBoxes(int collectedBoxes) {
        this.collectedBoxes = collectedBoxes;
    }

    public int getCollectedBoxes() {
        return collectedBoxes;
    }

    public void addLevel() {
        setLevel(level + 1);
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getLevel() {
        return level;
    }

    public boolean rankUp() {
        for (Rank rank : Rank.values()) {
            if (rank.getId() == this.rank.getId() + 1) {
                setRank(rank);
                return true;
            }
        }

        return false; // Couldn't find a higher rank for the player. AKA, the player is at the highest rank.
    }

    public void setRank(Rank rank) {
        this.rank = rank;
    }

    public Rank getRank() {
        return rank;
    }

    public void addXP(int xp) {
        setXP(this.xp + xp);
    }

    public void setXP(int xp) {
        this.xp = xp;
    }

    public int getXP() {
        return xp;
    }

    public void addHyions(int hyions) {
        setHyions(this.hyions + hyions);
    }

    public void setHyions(int hyions) {
        this.hyions = hyions;
    }

    public int getHyions() {
        return hyions;
    }

    /*
     * Bukkit Functions
     */

    public void sendMessage(String s) {
        player.sendMessage(ChatUtils.color(s));
    }

    public void teleport(Location location) {
        player.teleport(location);
    }

    public void playSound(Sound sound) {
        player.playSound(player.getLocation(), sound, 1f, 1f);
    }

    public String getName() {
        return player.getName();
    }

    public UUID getUniqueId() {
        return uuid;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

    /*
     * SQL Functions
     */

    private void create() {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            UUID uuid = getUniqueId();
            connection = sql.getConnection();
            if (!sql.exists(uuid)) {
                statement = connection.prepareStatement("INSERT IGNORE INTO player_data (UUID, KILLS, DEATHS, STREAK, " +
                        "COLLECTED_BOXES, RANK, LEVEL, XP, HYIONS)" +
                        "VALUES ('" + uuid + "',0,0,0,0,'UNRANKED',1,0,0)");
                statement.executeUpdate();
            } else {
                loadData();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            sql.close(connection, statement, null);
        }
    }

    private void loadData() {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        try {
            connection = sql.getConnection();
            statement = connection.prepareStatement("SELECT * FROM player_data WHERE UUID=?");
            statement.setString(1, getUniqueId().toString());
            rs = statement.executeQuery();

            if (rs.next()) {
                this.kills = rs.getInt("KILLS");
                this.deaths = rs.getInt("DEATHS");
                this.streak = rs.getInt("STREAK");
                this.collectedBoxes = rs.getInt("COLLECTED_BOXES");
                this.rank = Rank.valueOf(rs.getString("RANK"));
                this.level = rs.getInt("LEVEL");
                this.xp = rs.getInt("XP");
                this.hyions = rs.getInt("HYIONS");
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            sql.close(connection, statement, rs);
        }
    }

    public void saveData() {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = sql.getConnection();
            statement = connection.prepareStatement(
                    "REPLACE INTO quests_data (UUID, KILLS, DEATHS, STREAK, COLLECTED_BOXES, RANK, LEVEL, XP, HYIONS) VALUES " +
                            "('" + uuid + "',"
                            + kills + ","
                            + deaths + ","
                            + collectedBoxes + ",'"
                            + rank.name() + "',"
                            + level + ","
                            + xp + ","
                            + hyions + ")"
            );

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            sql.close(connection, statement, null);
        }
    }

}
