package gg.steve.mc.pp.utility;

import gg.steve.mc.pp.SPlugin;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

@UtilityClass
public final class Log {
    private static JavaPlugin plugin;

    public static void setPluginInstance(JavaPlugin instance) {
        plugin = instance;
    }

    public static void debug(String message) {
        if (SPlugin.getSPluginInstance() != null && SPlugin.getSPluginInstance().isDebugMode()) plugin.getLogger().info(ChatColor.GREEN + "[DEBUG] " + message);
    }

    public static void info(String message) {
        plugin.getLogger().info(message);
    }

    public static void warning(String message) {
        plugin.getLogger().warning(message);
    }

    public static void severe(String message) {
        plugin.getLogger().severe(message);
    }
}
