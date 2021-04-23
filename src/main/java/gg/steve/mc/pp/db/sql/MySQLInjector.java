package gg.steve.mc.pp.db.sql;

import gg.steve.mc.pp.db.DatabaseImplementation;
import gg.steve.mc.pp.sapi.utils.LogUtil;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQLInjector extends AbstractDatabaseInjector {

    public MySQLInjector(JavaPlugin plugin) {
        super(DatabaseImplementation.MYSQL, plugin);
    }

    @Override
    public void connect() {
        try {
            this.setConnection(DriverManager.getConnection("jdbc:mysql://" + this.getHost() + ":" + this.getPort() + "/" + this.getDatabase() + "?autoReconnect=true&useSSL=" + this.isSsl(), this.getUser(), this.getPassword()));
        } catch (Exception e) {
            e.printStackTrace();
            LogUtil.info("There was an error connecting to the Prisons+ SQL database: " + e.getMessage());
        }
        LogUtil.info("Successfully connected to the Prisons+ SQL database.");
    }

    @Override
    public void disconnect() {
        try {
            if (this.getConnection() != null && !this.getConnection().isClosed()) this.getConnection().close();
            LogUtil.info("Successfully disconnected from the Prisons+ SQL database.");
        } catch (SQLException e) {
            LogUtil.info("There was an error disconnecting from the Prisons+ SQL database: " + e.getMessage());
        }
    }
}
