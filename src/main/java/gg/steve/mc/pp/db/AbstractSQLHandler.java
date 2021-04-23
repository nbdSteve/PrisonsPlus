package gg.steve.mc.pp.db;

import gg.steve.mc.pp.db.sql.AbstractDatabaseInjector;
import gg.steve.mc.pp.manager.AbstractManager;
import gg.steve.mc.pp.sapi.utils.LogUtil;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@EqualsAndHashCode(callSuper = true)
@Data
public abstract class AbstractSQLHandler extends AbstractManager implements DatabaseHandler {
    private final AbstractDatabaseInjector injector;
    private final JavaPlugin plugin;

    public AbstractSQLHandler(DatabaseImplementation implementation, JavaPlugin plugin) {
        this.injector = DatabaseImplementation.getInjectorInstanceForImplementation(implementation, plugin);
        this.plugin = plugin;
    }

    @Override
    public ResultSet query(String sql) {
        Connection connection = injector.getConnection();
        ResultSet result = null;
        synchronized (plugin) {
            try {
                PreparedStatement statement = connection.prepareStatement(sql);
                result = statement.executeQuery();
                statement.close();
            } catch (SQLException e) {
                LogUtil.warning("An error occurred while trying to execute an sql query.");
            }
        }
        return result;
    }

    @Override
    public void update(String sql) {
        Connection connection = injector.getConnection();
        Bukkit.getScheduler().runTaskAsynchronously(this.plugin, () -> {
            try {
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.executeUpdate();
                statement.close();
            } catch (SQLException e) {
                LogUtil.warning("An error occurred while trying to execute an sql update statement.");
            }
        });
    }

    @Override
    public void delete(String sql) {
        Connection connection = injector.getConnection();
        Bukkit.getScheduler().runTaskAsynchronously(this.plugin, () -> {
            try {
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.executeUpdate();
                statement.close();
            } catch (SQLException e) {
                LogUtil.warning("An error occurred while trying to execute an sql delete statement.");
            }
        });
    }

    @Override
    public void insert(String sql) {
        Connection connection = injector.getConnection();
        Bukkit.getScheduler().runTaskAsynchronously(this.plugin, () -> {
            try {
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.executeUpdate();
                statement.close();
            } catch (SQLException e) {
                LogUtil.warning("An error occurred while trying to execute an sql insert statement.");
            }
        });
    }

    @Override
    public void execute(String sql) {
        Connection connection = injector.getConnection();
        Bukkit.getScheduler().runTaskAsynchronously(this.plugin, () -> {
            try {
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.execute();
                statement.close();
            } catch (SQLException e) {
                LogUtil.warning("An error occurred while trying to execute an sql execute statement.");
            }
        });
    }
}
