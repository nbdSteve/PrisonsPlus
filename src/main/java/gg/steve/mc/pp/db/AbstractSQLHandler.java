package gg.steve.mc.pp.db;

import gg.steve.mc.pp.SPlugin;
import gg.steve.mc.pp.db.sql.AbstractDatabaseInjector;
import gg.steve.mc.pp.manager.AbstractManager;
import gg.steve.mc.pp.manager.ManagerClass;
import gg.steve.mc.pp.utility.Log;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.bukkit.Bukkit;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@ManagerClass
public abstract class AbstractSQLHandler extends AbstractManager implements DatabaseHandler {
    private final SPlugin sPlugin;
    private AbstractDatabaseInjector injector;
    private DatabaseImplementation implementation;

    public AbstractSQLHandler(SPlugin sPlugin) {
        this.sPlugin = sPlugin;
    }

    public void setDatabaseImplementation(DatabaseImplementation implementation) {
        this.implementation = implementation;
    }

    public void initialiseInjector() {
        this.injector = DatabaseImplementation.getInjectorInstanceForImplementation(this.implementation, this.sPlugin);
        this.injector.setDatabaseCredentials();
    }

    @Override
    public String query(String sql, String field) {
        Connection connection = injector.getConnection();
        String result = "";
        synchronized (this.sPlugin.getPlugin()) {
            try {
                PreparedStatement statement = connection.prepareStatement(sql);
                ResultSet rs = statement.executeQuery();
                while (rs.next()) {
                    result = rs.getString(field);
                }
                statement.close();
            } catch (SQLException e) {
                Log.warning("An error occurred while trying to execute an sql query. SQL: "+ sql);
            }
        }
        return result;
    }

    @Override
    public void update(String sql) {
        Connection connection = injector.getConnection();
        Bukkit.getScheduler().runTaskAsynchronously(this.sPlugin.getPlugin(), () -> {
            try {
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.executeUpdate();
                statement.close();
            } catch (SQLException e) {
                Log.warning("An error occurred while trying to execute an sql update statement.");
            }
        });
    }

    @Override
    public void delete(String sql) {
        Connection connection = injector.getConnection();
        Bukkit.getScheduler().runTaskAsynchronously(this.sPlugin.getPlugin(), () -> {
            try {
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.executeUpdate();
                statement.close();
            } catch (SQLException e) {
                Log.warning("An error occurred while trying to execute an sql delete statement.");
            }
        });
    }

    @Override
    public void insert(String sql, String... values) {
        Connection connection = injector.getConnection();
        Bukkit.getScheduler().runTaskAsynchronously(this.sPlugin.getPlugin(), () -> {
            try {
                PreparedStatement statement = connection.prepareStatement(sql);
                if (values != null) {
                    List<String> replacements = Arrays.asList(values);
                    for (int i = 1; i <= replacements.size(); i++) {
                        statement.setString(i, replacements.get(i - 1));
                    }
                }
                statement.executeUpdate();
                statement.close();
            } catch (SQLException e) {
                Log.warning("An error occurred while trying to execute an sql insert statement.");
            }
        });
    }

    @Override
    public void execute(String sql) {
        Connection connection = injector.getConnection();
        Bukkit.getScheduler().runTaskAsynchronously(this.sPlugin.getPlugin(), () -> {
            try {
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.execute();
                statement.close();
            } catch (SQLException e) {
                Log.warning("An error occurred while trying to execute an sql execute statement.");
            }
        });
    }

    @Override
    public void synchronousUpdate(String sql) {
        Connection connection = injector.getConnection();
        synchronized (this.sPlugin.getPlugin()) {
            try {
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.executeUpdate();
                statement.close();
            } catch (SQLException e) {
                Log.warning("An error occurred while trying to execute an sql update statement.");
            }
        }
    }

    @Override
    public void synchronousDelete(String sql) {
        Connection connection = injector.getConnection();
        synchronized (this.sPlugin.getPlugin()) {
            try {
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.executeUpdate();
                statement.close();
            } catch (SQLException e) {
                Log.warning("An error occurred while trying to execute an sql delete statement.");
            }
        }
    }

    @Override
    public void synchronousInsert(String sql, String... values) {
        Connection connection = injector.getConnection();
        synchronized (this.sPlugin.getPlugin()) {
            try {
                PreparedStatement statement = connection.prepareStatement(sql);
                if (values != null) {
                    List<String> replacements = Arrays.asList(values);
                    for (int i = 1; i <= replacements.size(); i++) {
                        statement.setString(i, replacements.get(i - 1));
                    }
                }
                statement.executeUpdate();
                statement.close();
            } catch (SQLException e) {
                Log.warning("An error occurred while trying to execute an sql insert statement.");
            }
        }
    }

    @Override
    public void synchronousExecute(String sql) {
        Connection connection = injector.getConnection();
        synchronized (this.sPlugin.getPlugin()) {
            try {
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.execute();
                statement.close();
            } catch (SQLException e) {
                Log.warning("An error occurred while trying to execute an sql execute statement.");
            }
        }
    }
}
