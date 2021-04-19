package gg.steve.mc.pp.db.implementations;

import gg.steve.mc.pp.db.DatabaseImplementation;
import gg.steve.mc.pp.db.DatabaseInjector;
import gg.steve.mc.pp.db.DatabaseManager;
import gg.steve.mc.pp.framework.utils.LogUtil;

import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQLInjector extends DatabaseInjector {

    public MySQLInjector() {
        super(DatabaseImplementation.MYSQL);
    }

    @Override
    public void connect() {
        try {
            this.setConnection(DriverManager.getConnection("jdbc:mysql://" + this.getHost() + ":" + this.getPort() + "/" + this.getDatabase() + "?autoReconnect=true&useSSL=" + this.isSsl(), this.getUser(), this.getPassword()));
            DatabaseManager.generateTables();
        } catch (Exception e) {
            e.printStackTrace();
            LogUtil.info("There was an error connecting to the VaultedTags SQL database: " + e.getMessage());
        }
        LogUtil.info("Successfully connected to the VaultedTags SQL database.");
    }

    @Override
    public void disconnect() {
        try {
            if (this.getConnection() != null && !this.getConnection().isClosed()) this.getConnection().close();
            LogUtil.info("Successfully disconnected from the VaultedTags SQL database.");
        } catch (SQLException e) {
            LogUtil.info("There was an error disconnecting from the VaultedTags SQL database: " + e.getMessage());
        }
    }
}
