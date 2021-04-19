package gg.steve.mc.pp.db.implementations;

import gg.steve.mc.pp.VaultedTagsPlugin;
import gg.steve.mc.pp.db.DatabaseImplementation;
import gg.steve.mc.pp.db.DatabaseInjector;
import gg.steve.mc.pp.db.DatabaseManager;
import gg.steve.mc.pp.framework.utils.LogUtil;

import java.io.File;
import java.io.IOException;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLiteInjector extends DatabaseInjector {

    public SQLiteInjector() {
        super(DatabaseImplementation.SQLITE);
    }

    @Override
    public void connect() {
        try {
            File dataFolder = new File(VaultedTagsPlugin.getInstance().getDataFolder(), "data");
            if (!dataFolder.exists()) {
                dataFolder.mkdirs();
            }
            File folder = new File(VaultedTagsPlugin.getInstance().getDataFolder() + File.separator + "data", this.getDatabase() + ".db");
            if (!folder.exists()) {
                try {
                    folder.createNewFile();
                } catch (IOException e) {
                    LogUtil.warning("Error creating sql lite db file");
                }
            }
            Class.forName("org.sqlite.JDBC");
            this.setConnection(DriverManager.getConnection("jdbc:sqlite:" + folder));
            DatabaseManager.generateTables();
        } catch (Exception e) {
            e.printStackTrace();
            LogUtil.info("There was an error connecting to the VaultedTags SQLite database: " + e.getMessage());
        }
        LogUtil.info("Successfully connected to the VaultedTags SQLite database.");
    }

    @Override
    public void disconnect() {
        try {
            if (this.getConnection() != null && !this.getConnection().isClosed()) this.getConnection().close();
            LogUtil.info("Successfully disconnected from the VaultedTags SQLite database.");
        } catch (SQLException e) {
            LogUtil.info("There was an error disconnecting from the VaultedTags SQLite database: " + e.getMessage());
        }
    }
}
