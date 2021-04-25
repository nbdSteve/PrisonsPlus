package gg.steve.mc.pp;

import gg.steve.mc.pp.addon.PrisonsAddonManager;
import gg.steve.mc.pp.db.SQLDatabaseHandler;
import gg.steve.mc.pp.economy.EconomyManager;
import gg.steve.mc.pp.file.FileManager;
import gg.steve.mc.pp.manager.AbstractManager;
import gg.steve.mc.pp.placeholder.PlaceholderManager;
import lombok.Data;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

@Data
public class SPlugin {
    private static SPlugin instance;
    private final JavaPlugin plugin;
    private List<PlaceholderExpansion> placeholderExpansions;
    // Store manager instances to be accessed by addons
    private final FileManager fileManager;
    private final SQLDatabaseHandler sqlDatabaseHandler;
    private final PrisonsAddonManager addonManager;
    private final EconomyManager economyManager;
    private final PlaceholderManager placeholderManager;

    public SPlugin(JavaPlugin plugin) {
        instance = this;
        this.plugin = plugin;
        // register managers
        this.fileManager = new FileManager(this.plugin);
        // commands, events managers still needed
        this.sqlDatabaseHandler = new SQLDatabaseHandler(this.plugin);
        this.addonManager = new PrisonsAddonManager(this.plugin);
        this.economyManager = new EconomyManager();
        this.placeholderManager = new PlaceholderManager();
        AbstractManager.loadManagers();
    }

    public void shutdown() {
        AbstractManager.shutdownManagers();
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