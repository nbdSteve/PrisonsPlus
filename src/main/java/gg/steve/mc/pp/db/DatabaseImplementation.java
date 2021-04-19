package gg.steve.mc.pp.db;

import gg.steve.mc.pp.db.implementations.MySQLInjector;
import gg.steve.mc.pp.db.implementations.SQLiteInjector;

import java.util.Locale;

public enum DatabaseImplementation {
    SQLITE(),
    MYSQL(),
    ;

    public static DatabaseImplementation getImplementationUsed(String implementation) {
        try {
            return DatabaseImplementation.valueOf(implementation.toUpperCase(Locale.ROOT));
        } catch (Exception e) {
            return SQLITE;
        }
    }

    public static DatabaseInjector getInjectorInstanceForImplementation(DatabaseImplementation implementation) {
        switch (implementation) {
            case MYSQL:
                return new MySQLInjector();
            case SQLITE:
                return new SQLiteInjector();
        }
        return new SQLiteInjector();
    }
}
