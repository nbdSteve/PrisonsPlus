package gg.steve.mc.pp.db;

import gg.steve.mc.pp.db.sql.AbstractDatabaseInjector;
import gg.steve.mc.pp.file.PluginFile;
import lombok.EqualsAndHashCode;
import org.bukkit.plugin.java.JavaPlugin;

@EqualsAndHashCode(callSuper = true)
public class SQLDatabaseHandler extends AbstractSQLHandler {
    private static SQLDatabaseHandler instance;
    private static AbstractDatabaseInjector dbInjector;

    public SQLDatabaseHandler(JavaPlugin plugin) {
        super(DatabaseImplementation.getImplementationUsed(PluginFile.CONFIG.get().getString("database.implementation")), plugin);
        instance = this;
        DatabaseImplementation implementation = DatabaseImplementation.getImplementationUsed(PluginFile.CONFIG.get().getString("database.implementation"));
        dbInjector = DatabaseImplementation.getInjectorInstanceForImplementation(implementation, plugin);
    }

    @Override
    public void onLoad() {
        dbInjector.connect();
    }

    @Override
    public void onShutdown() {
        dbInjector.disconnect();
    }

    public static SQLDatabaseHandler getInstance() {
        return instance;
    }

    public static AbstractDatabaseInjector getDbInjector() {
        return dbInjector;
    }
}