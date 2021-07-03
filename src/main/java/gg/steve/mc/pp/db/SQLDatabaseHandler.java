package gg.steve.mc.pp.db;

import gg.steve.mc.pp.SPlugin;
import gg.steve.mc.pp.db.sql.AbstractDatabaseInjector;
import gg.steve.mc.pp.file.FileManager;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
public class SQLDatabaseHandler extends AbstractSQLHandler {
    private static SQLDatabaseHandler instance;
    private static AbstractDatabaseInjector dbInjector;

    public SQLDatabaseHandler(SPlugin sPlugin) {
        super(DatabaseImplementation.getImplementationUsed(FileManager.CoreFiles.CONFIG.get().getString("database.implementation")), sPlugin);
        instance = this;
        DatabaseImplementation implementation = DatabaseImplementation.getImplementationUsed(FileManager.CoreFiles.CONFIG.get().getString("database.implementation"));
        dbInjector = DatabaseImplementation.getInjectorInstanceForImplementation(implementation, sPlugin);
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

    @Override
    protected String getManagerName() {
        return "SQL";
    }
}