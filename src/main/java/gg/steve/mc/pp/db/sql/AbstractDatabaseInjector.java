package gg.steve.mc.pp.db.sql;

import gg.steve.mc.pp.SPlugin;
import gg.steve.mc.pp.db.DatabaseImplementation;
import gg.steve.mc.pp.file.PluginFile;
import lombok.Data;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.Connection;

@Data
public abstract class AbstractDatabaseInjector {
    private Connection connection;
    private final DatabaseImplementation implementation;
    private final SPlugin sPlugin;
    private final String host, port, database, user, password;
    private final boolean ssl;

    public AbstractDatabaseInjector(DatabaseImplementation implementation, SPlugin sPlugin) {
        this.implementation = implementation;
        this.sPlugin = sPlugin;
        this.host = PluginFile.CONFIG.get().getString("database.host");
        this.port = PluginFile.CONFIG.get().getString("database.port");
        this.database = PluginFile.CONFIG.get().getString("database.database");
        this.user = PluginFile.CONFIG.get().getString("database.username");
        this.password = PluginFile.CONFIG.get().getString("database.password");
        this.ssl = PluginFile.CONFIG.get().getBoolean("database.ssl");
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
