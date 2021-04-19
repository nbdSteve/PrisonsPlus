package gg.steve.mc.pp.db;

import gg.steve.mc.pp.manager.AbstractManager;
import lombok.Data;

import java.sql.ResultSet;

@Data
public abstract class AbstractDatabaseHandler implements DatabaseHandler {
    private DatabaseInjector injector;

    public AbstractDatabaseHandler(DatabaseImplementation implementation) {
        this.injector = DatabaseImplementation.getInjectorInstanceForImplementation(implementation);
    }

    @Override
    public ResultSet query(String sql) {
        return null;
    }

    @Override
    public
}
