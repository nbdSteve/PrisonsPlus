package gg.steve.mc.pp.framework.utils;

import org.bukkit.plugin.java.JavaPlugin;

public class LogUtil {
    private static JavaPlugin instance;
    private static boolean debug;

    public static void setInstance(JavaPlugin plugin, boolean debugMode) {
        instance = plugin;
        debug = debugMode;
    }

    public static void debug(String message) {
        if (!debug) return;
        instance.getLogger().info(ColorUtil.colorize(message));
    }

    public static void info(String message) {
        instance.getLogger().info(ColorUtil.colorize(message));
    }

    public static void warning(String message) {
        instance.getLogger().warning(ColorUtil.colorize(message));
    }
}
