package gg.steve.mc.pp.tokens.db;

import gg.steve.mc.pp.PrisonsPlus;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager {
    int port;
    private Connection connection;
    private String host, username, password, database;

    public ConnectionManager(String host, String database, String username, String password, int port) {
        this.host = host;
        this.username = username;
        this.password = password;
        this.database = database;
        this.port = port;
    }

    public void connect() {
        synchronized (PrisonsPlus.getInstance()) {
            try {
                connection = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database, username, password);
            } catch (SQLException e) {
                e.printStackTrace();
                // Could not connect?
            }
        }
    }

    public void disconnect() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            // Could not close?
        }
    }

    public synchronized Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) connect();
        } catch (SQLException e) {
            e.printStackTrace();
            // Could not open/connect/get?
        }
        return connection;
    }
}
