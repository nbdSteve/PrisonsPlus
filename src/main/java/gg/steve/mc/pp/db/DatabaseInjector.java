package gg.steve.mc.pp.db;

import gg.steve.mc.pp.framework.yml.Files;

import java.sql.Connection;

public abstract class DatabaseInjector {
    private Connection connection;
    private final DatabaseImplementation implementation;
    private final String host, port, database, user, password;
    private final boolean ssl;

    public DatabaseInjector(DatabaseImplementation implementation) {
        this.implementation = implementation;
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

    public DatabaseImplementation getImplementation() {
        return implementation;
    }

    public String getHost() {
        return host;
    }

    public String getPort() {
        return port;
    }

    public String getDatabase() {
        return database;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }

    public boolean isSsl() {
        return ssl;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }
}
