package gg.steve.mc.pp.db.sql;

import gg.steve.mc.pp.db.DatabaseImplementation;
import gg.steve.mc.pp.sapi.yml.Files;
import lombok.Data;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.Connection;

@Data
public abstract class AbstractDatabaseInjector {
    private Connection connection;
    private final DatabaseImplementation implementation;
    private final JavaPlugin plugin;
    private final String host, port, database, user, password;
    private final boolean ssl;

    public AbstractDatabaseInjector(DatabaseImplementation implementation, JavaPlugin plugin) {
        this.implementation = implementation;
        this.plugin = plugin;
        this.host = Files.CONFIG.get().getString("database.host");
        this.port = Files.CONFIG.get().getString("database.port");
        this.database = Files.CONFIG.get().getString("database.database");
        this.user = Files.CONFIG.get().getString("database.username");
        this.password = Files.CONFIG.get().getString("database.password");
        this.ssl = Files.CONFIG.get().getBoolean("database.ssl");
    }

    public abstract void connect();

    public abstract void disconnect();

    private void ping() {
        try {
            connection.prepareStatement("/* ping */ SELECT 1").execute();
        } catch (Exception e) {
            connect();
        }
    }

    public Connection getConnection() {
        ping();
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }
}
