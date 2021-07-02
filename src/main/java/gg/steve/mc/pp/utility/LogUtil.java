package gg.steve.mc.pp.utility;

import org.bukkit.plugin.java.JavaPlugin;

@UtilityClass
public class LogUtil {
    private static JavaPlugin plugin;

    public static void setPluginInstance(JavaPlugin instance) {
        plugin = instance;
    }

    public static void info(String message) {
        plugin.getLogger().info(message);
    }

    public static void warning(String message) {
        plugin.getLogger().warning(message);
    }
}
