package gg.steve.mc.pp.db.sql;

import gg.steve.mc.pp.SPlugin;
import gg.steve.mc.pp.db.DatabaseImplementation;
import gg.steve.mc.pp.utility.Log;

import java.io.File;
import java.io.IOException;
import java.sql.DriverManager;
import java.sql.SQLException;

@DatabaseInjectorClass
public class SQLiteInjector extends AbstractDatabaseInjector {

    public SQLiteInjector(SPlugin sPlugin) {
        super(DatabaseImplementation.SQLITE, sPlugin);
    }

    @Override
    public void connect() {
        try {
            File dataFolder = new File(super.getSPlugin().getPlugin().getDataFolder(), "data");
            if (!dataFolder.exists()) {
                dataFolder.mkdirs();
            }
            File folder = new File(super.getSPlugin().getPlugin().getDataFolder() + File.separator + "data", super.getDatabase() + ".db");
            if (!folder.exists()) {
                try {
                    folder.createNewFile();
                } catch (IOException e) {
                    Log.warning("Error creating the Prisons+ SQLite db file");
                }
            }
            Class.forName("org.sqlite.JDBC");
            super.setConnection(DriverManager.getConnection("jdbc:sqlite:" + folder));
        } catch (Exception e) {
            e.printStackTrace();
            Log.debug("There was an error connecting to the Prisons+ SQLite database: " + e.getMessage());
        }
        Log.debug("Successfully connected to the Prisons+ SQLite database.");
    }

    @Override
    public void disconnect() {
        try {
            if (super.getConnection() != null && !super.getConnection().isClosed()) super.getConnection().close();
            Log.debug("Successfully disconnected from the Prisons+ SQLite database.");
        } catch (SQLException e) {
            Log.debug("There was an error disconnecting from the Prisons+ SQLite database: " + e.getMessage());
        }
    }
}
