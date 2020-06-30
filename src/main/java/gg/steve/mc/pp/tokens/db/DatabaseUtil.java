package gg.steve.mc.pp.tokens.db;

import gg.steve.mc.pp.PrisonsPlus;
import gg.steve.mc.pp.framework.utils.LogUtil;
import gg.steve.mc.pp.framework.yml.Files;
import gg.steve.mc.pp.tokens.player.PlayerTokenManager;
import gg.steve.mc.pp.tokens.player.TokenType;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class DatabaseUtil {
    private static ConnectionManager connectionManager;

    public static ConnectionManager setupConnection() {
        ConfigurationSection section = Files.CONFIG.get().getConfigurationSection("database");
        connectionManager = new ConnectionManager(section.getString("host"),
                section.getString("database"),
                section.getString("username"),
                section.getString("password"),
                section.getInt("port"));
        connectionManager.connect();
        generateTables();
        loadTokenData();
        LogUtil.info("Successfully connected to the SQL database, and loaded online player data.");
        return connectionManager;
    }

    public static void generateTables() {
        Connection connection = connectionManager.getConnection();
        try {
            PreparedStatement tokens =
                    connection.prepareStatement("CREATE TABLE IF NOT EXISTS tokens(uuid VARCHAR(255) NOT NULL, balance INT NOT NULL, prestige INT NOT NULL, PRIMARY KEY (uuid))");
            tokens.execute();
            tokens.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void loadTokenData() {
        PlayerTokenManager.initialise();
        if (Bukkit.getOnlinePlayers().isEmpty()) return;
        Connection connection = connectionManager.getConnection();
        for (Player player : Bukkit.getOnlinePlayers()) {
            try {
                PreparedStatement query = connection.prepareStatement("SELECT * FROM `tokens` WHERE uuid='" + player.getUniqueId() + "'");
                ResultSet rs = query.executeQuery();
                if (rs.next()) {
                    PlayerTokenManager.addTokenPlayer(player.getUniqueId(), rs.getInt("balance"), rs.getInt("prestige"));
                } else {
                    PlayerTokenManager.addTokenPlayer(player.getUniqueId(), 0, 0);
                }
                query.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        connectionManager.disconnect();
    }

    public static void saveTokenData() {
        synchronized (PrisonsPlus.getInstance()) {
            Connection connection = connectionManager.getConnection();
            for (UUID uuid : PlayerTokenManager.getTokenPlayers().keySet()) {
                try {
                    PreparedStatement set = connection.prepareStatement("REPLACE INTO tokens(uuid, balance, prestige) VALUES (?, ?, ?);");
                    set.setString(1, String.valueOf(uuid));
                    set.setInt(2, PlayerTokenManager.getTokens(uuid, TokenType.TOKEN));
                    set.setInt(3, PlayerTokenManager.getTokens(uuid, TokenType.PRESTIGE));
                    set.executeUpdate();
                    set.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            connectionManager.disconnect();
            PlayerTokenManager.clearPlayerMap();
            LogUtil.info("All token data for players who have not yet disconnected has been saved.");
        }
    }

    public static void loadPlayerTokenData(UUID playerId) {
        Bukkit.getScheduler().runTaskAsynchronously(PrisonsPlus.getInstance(), () -> {
            Connection connection = connectionManager.getConnection();
            try {
                PreparedStatement query = connection.prepareStatement("SELECT * FROM `tokens` WHERE uuid='" + playerId + "'");
                ResultSet rs = query.executeQuery();
                if (rs.next()) {
                    PlayerTokenManager.addTokenPlayer(playerId, rs.getInt("balance"), rs.getInt("prestige"));
                } else {
                    PlayerTokenManager.addTokenPlayer(playerId, 0, 0);
                }
                query.close();
                LogUtil.info("Successfully loaded token data for player: " + playerId + ".");
            } catch (SQLException e) {
                e.printStackTrace();
            }
            connectionManager.disconnect();
        });
    }

    public static void savePlayerTokenData(UUID playerId) {
        Bukkit.getScheduler().runTaskAsynchronously(PrisonsPlus.getInstance(), () -> {
            Connection connection = connectionManager.getConnection();
            try {
                PreparedStatement set = connection.prepareStatement("REPLACE INTO tokens(uuid, balance, prestige) VALUES (?, ?, ?);");
                set.setString(1, String.valueOf(playerId));
                set.setInt(2, PlayerTokenManager.getTokens(playerId, TokenType.TOKEN));
                set.setInt(3, PlayerTokenManager.getTokens(playerId, TokenType.PRESTIGE));
                set.executeUpdate();
                set.close();
                LogUtil.info("Successfully saved token data for player: " + playerId + ".");
            } catch (SQLException e) {
                e.printStackTrace();
            }
            connectionManager.disconnect();
            PlayerTokenManager.removeTokenPlayer(playerId);
        });
    }
}
