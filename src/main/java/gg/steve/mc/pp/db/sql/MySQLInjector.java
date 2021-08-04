package gg.steve.mc.pp.db.sql;

import gg.steve.mc.pp.SPlugin;
import gg.steve.mc.pp.db.DatabaseImplementation;
import gg.steve.mc.pp.utility.Log;

import java.sql.DriverManager;
import java.sql.SQLException;

@DatabaseInjectorClass
public class MySQLInjector extends AbstractDatabaseInjector {

    public MySQLInjector(SPlugin sPlugin) {
        super(DatabaseImplementation.MYSQL, sPlugin);
    }

    @Override
    public void connect() {
        try {
            this.setConnection(DriverManager.getConnection("jdbc:mysql://" + this.getHost() + ":" + this.getPort() + "/" + this.getDatabase() + "?autoReconnect=true&useSSL=" + this.isSsl(), this.getUser(), this.getPassword()));
        } catch (Exception e) {
            e.printStackTrace();
            Log.warning("There was an error connecting to the Prisons+ SQL database: " + e.getMessage());
        }
        Log.debug("Successfully connected to the Prisons+ SQL database.");
    }

    @Override
    public void disconnect() {
        try {
            if (this.getConnection() != null && !this.getConnection().isClosed()) this.getConnection().close();
            Log.debug("Successfully disconnected from the Prisons+ SQL database.");
        } catch (SQLException e) {
            Log.debug("There was an error disconnecting from the Prisons+ SQL database: " + e.getMessage());
        }
    }
}
