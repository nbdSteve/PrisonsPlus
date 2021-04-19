package gg.steve.mc.pp.db;

import gg.steve.mc.pp.PrisonsPlusPlugin;
import gg.steve.mc.pp.db.implementations.MySQLInjector;
import gg.steve.mc.pp.db.implementations.SQLiteInjector;
import gg.steve.mc.pp.manager.AbstractManager;
import gg.steve.mc.pp.sapi.utils.LogUtil;
import org.bukkit.Bukkit;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class DatabaseManager extends AbstractManager {
    private static DatabaseManager dbInstance;
    private static DatabaseInjector dbInjector;

    public DatabaseManager() {
        dbInstance = this;
        DatabaseImplementation implementation = DatabaseImplementation.getImplementationUsed();
        switch (implementation) {
            case MYSQL:
                dbInjector = new MySQLInjector();
                break;
            case SQLITE:
                dbInjector = new SQLiteInjector();
                break;
        }
    }

    @Override
    public void onLoad() {
        dbInjector.connect();
    }

    @Override
    public void onShutdown() {
        dbInjector.disconnect();
    }

    public static DatabaseManager getDbInstance() {
        return dbInstance;
    }

    public static DatabaseInjector getDbInjector() {
        return dbInjector;
    }

    public static void generateTables() {
        Connection connection = DatabaseManager.getDbInjector().getConnection();
        Bukkit.getScheduler().runTaskAsynchronously(PrisonsPlusPlugin.getInstance(), () -> {
            try {
                PreparedStatement table =
                        connection.prepareStatement("CREATE TABLE IF NOT EXISTS player_tags " +
                                "(player_id VARCHAR(36) NOT NULL," +
                                "selected_tag_id VARCHAR(255) NOT NULL, " +
                                "PRIMARY KEY (player_id))");
                table.execute();
                table.close();
            } catch (SQLException e) {
                e.printStackTrace();
                LogUtil.warning("SQLite error creating the player tags table.");
            }
        });
    }

    public static String getSelectedTagForPlayer(UUID playerId) {
        Connection connection = DatabaseManager.getDbInjector().getConnection();
        String tagId = "";
        synchronized (PrisonsPlusPlugin.getInstance()) {
            try {
                PreparedStatement query =
                        connection.prepareStatement("SELECT * FROM player_tags WHERE player_id='" + String.valueOf(playerId) + "'");
                ResultSet rs = query.executeQuery();
                while (rs.next()) {
                    tagId = rs.getString("selected_tag_id");
                }
                query.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return tagId;
    }

    public static void setSelectedTagForPlayer(UUID playerId, String tagId) {
//        if (!getSelectedTagForPlayer(playerId).equalsIgnoreCase("")) deleteSelectedTagForPlayer(playerId);
        Connection connection = DatabaseManager.getDbInjector().getConnection();
        Bukkit.getScheduler().runTaskAsynchronously(PrisonsPlusPlugin.getInstance(), () -> {
            if (!getSelectedTagForPlayer(playerId).equalsIgnoreCase("")) {
                try {
                    PreparedStatement set =
                            connection.prepareStatement("UPDATE player_tags SET selected_tag_id='" + tagId + "' WHERE player_id='" + String.valueOf(playerId) + "'");
                    set.executeUpdate();
                    set.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    PreparedStatement set =
                            connection.prepareStatement("INSERT INTO player_tags (player_id, selected_tag_id) VALUES (?, ?);");
                    set.setString(1, String.valueOf(playerId));
                    set.setString(2, tagId);
                    set.executeUpdate();
                    set.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public static void deleteSelectedTagForPlayer(UUID playerId) {
        Connection connection = DatabaseManager.getDbInjector().getConnection();
        Bukkit.getScheduler().runTaskAsynchronously(PrisonsPlusPlugin.getInstance(), () -> {
            try {
                PreparedStatement set =
                        connection.prepareStatement("DELETE FROM player_tags WHERE player_id='" + String.valueOf(playerId) + "'");
                set.executeUpdate();
                set.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    public static boolean hasTagSelected(UUID playerId) {
        return !getSelectedTagForPlayer(playerId).equalsIgnoreCase("");
    }
}
