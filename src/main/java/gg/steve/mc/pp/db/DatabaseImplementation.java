package gg.steve.mc.pp.db;

import gg.steve.mc.pp.db.sql.AbstractDatabaseInjector;
import gg.steve.mc.pp.db.sql.MySQLInjector;
import gg.steve.mc.pp.db.sql.SQLiteInjector;
import org.bukkit.plugin.java.JavaPlugin;

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

    public static AbstractDatabaseInjector getInjectorInstanceForImplementation(DatabaseImplementation implementation, JavaPlugin plugin) {
        switch (implementation) {
            case MYSQL:
                return new MySQLInjector(plugin);
            case SQLITE:
                return new SQLiteInjector(plugin);
        }
        return new SQLiteInjector(plugin);
    }
}
