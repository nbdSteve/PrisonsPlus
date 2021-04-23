package gg.steve.mc.pp;

import gg.steve.mc.pp.db.SQLDatabaseHandler;
import gg.steve.mc.pp.manager.AbstractManager;
import gg.steve.mc.pp.sapi.utils.LogUtil;
import gg.steve.mc.pp.sapi.yml.Files;
import gg.steve.mc.pp.sapi.yml.utils.FileManagerUtil;
import lombok.Data;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

@Data
public class PrisonsPlus {
    private final JavaPlugin plugin;
    private final FileManagerUtil fileManagerUtil;
    private List<PlaceholderExpansion> placeholderExpansions;

    public PrisonsPlus(JavaPlugin plugin) {
        this.plugin = plugin;
        this.fileManagerUtil = new FileManagerUtil(plugin);
        setupFiles();
        registerCommands();
        registerEvents();
        loadPluginCache();
    }

    /**
     * Loads the files into the file manager
     */
    public void setupFiles() {
        Files.CONFIG.load(this.fileManagerUtil);
//        Files.PERMISSIONS.load(this.fileManagerUtil);
//        Files.DEBUG.load(this.fileManagerUtil);
//        Files.MESSAGES.load(this.fileManagerUtil);
    }

    public void registerCommands() {
    }

    public void registerEvents() {
        PluginManager pm = this.plugin.getServer().getPluginManager();
    }

    public void registerEvent(Listener listener) {
        this.plugin.getServer().getPluginManager().registerEvents(listener, this.plugin);
    }

    public void registerManagers() {
        new SQLDatabaseHandler(this.plugin);
    }

    public void registerPlaceholderExpansions() {
        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            LogUtil.info("PlaceholderAPI found, registering expansions with it now...");
            for (PlaceholderExpansion expansion : placeholderExpansions) {
                expansion.register();
            }
        }
    }

    public void addPlaceholderExpansion(PlaceholderExpansion expansion) {
        placeholderExpansions.add(expansion);
    }

    public void loadPluginCache() {
        // modules
        placeholderExpansions = new ArrayList<>();
        registerManagers();
        AbstractManager.loadManagers();
    }

    public void shutdownPluginCache() {
        AbstractManager.shutdownManagers();
        if (placeholderExpansions != null && !placeholderExpansions.isEmpty()) placeholderExpansions.clear();
    }

//    public static void setupMetrics(JavaPlugin instance, int id) {
//        Metrics metrics = new Metrics(instance, id);
//        metrics.addCustomChart(new Metrics.MultiLineChart("players_and_servers", () -> {
//            Map<String, Integer> valueMap = new HashMap<>();
//            valueMap.put("servers", 1);
//            valueMap.put("players", Bukkit.getOnlinePlayers().size());
//            return valueMap;
//        }));
//    }
}
