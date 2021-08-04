package gg.steve.mc.pp.db.sql;

import gg.steve.mc.pp.SPlugin;
import gg.steve.mc.pp.db.DatabaseImplementation;
import gg.steve.mc.pp.file.FileManager;
import lombok.Data;

import java.sql.Connection;

@Data
public abstract class AbstractDatabaseInjector {
    private Connection connection;
    private final SPlugin sPlugin;
    private final DatabaseImplementation implementation;
    private String host, port, database, user, password;
    private boolean ssl;

    public AbstractDatabaseInjector(DatabaseImplementation implementation, SPlugin sPlugin) {
        this.implementation = implementation;
        this.sPlugin = sPlugin;
    }

    public void setDatabaseCredentials() {
        this.host = FileManager.CoreFiles.CONFIG.get().getString("database.host");
        this.port = FileManager.CoreFiles.CONFIG.get().getString("database.port");
        this.database = FileManager.CoreFiles.CONFIG.get().getString("database.database");
        this.user = FileManager.CoreFiles.CONFIG.get().getString("database.username");
        this.password = FileManager.CoreFiles.CONFIG.get().getString("database.password");
        this.ssl =FileManager.CoreFiles.CONFIG.get().getBoolean("database.ssl");
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
