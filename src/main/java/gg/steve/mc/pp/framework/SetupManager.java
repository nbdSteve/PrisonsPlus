package gg.steve.mc.pp.framework;

import gg.steve.mc.pp.framework.gui.GuiClickListener;
import gg.steve.mc.pp.framework.yml.Files;
import gg.steve.mc.pp.framework.yml.utils.FileManagerUtil;
import gg.steve.mc.pp.tokens.db.DatabaseUtil;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.awt.image.DataBufferShort;

/**
 * Class that handles setting up the plugin on start
 */
public class SetupManager {
    private static FileManagerUtil fileManager;

    private SetupManager() throws IllegalAccessException {
        throw new IllegalAccessException("Manager class cannot be instantiated.");
    }

    /**
     * Loads the files into the file manager
     */
    public static void setupFiles(FileManagerUtil fm) {
        fileManager = fm;
//        Files.CONFIG.load(fm);
//        Files.PERMISSIONS.load(fm);
//        Files.DEBUG.load(fm);
//        Files.MESSAGES.load(fm);
    }

    public static void registerCommands(JavaPlugin instance) {
    }

    /**
     * Register all of the events for the plugin
     *
     * @param instance Plugin, the main plugin instance
     */
    public static void registerEvents(JavaPlugin instance) {
        PluginManager pm = instance.getServer().getPluginManager();
        pm.registerEvents(new GuiClickListener(), instance);
    }

    public static void registerEvent(JavaPlugin instance, Listener listener) {
        instance.getServer().getPluginManager().registerEvents(listener, instance);
    }

    public static void loadPluginCache() {
        AbstractManager.initialiseManagers();
        DatabaseUtil.setupConnection();
    }

    public static void shutdownPluginCache() {
        DatabaseUtil.saveTokenData();
        AbstractManager.shutdownManagers();
    }

    public static FileManagerUtil getFileManager() {
        return fileManager;
    }
}
