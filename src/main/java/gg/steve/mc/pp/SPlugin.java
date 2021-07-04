package gg.steve.mc.pp;

import gg.steve.mc.pp.addon.PrisonsAddonManager;
import gg.steve.mc.pp.cmd.CommandManager;
import gg.steve.mc.pp.db.SQLDatabaseHandler;
import gg.steve.mc.pp.economy.EconomyManager;
import gg.steve.mc.pp.event.EventManager;
import gg.steve.mc.pp.file.FileManager;
import gg.steve.mc.pp.gui.GuiManager;
import gg.steve.mc.pp.gui.action.InventoryClickActionManager;
import gg.steve.mc.pp.manager.AbstractManager;
import gg.steve.mc.pp.message.MessageManager;
import gg.steve.mc.pp.permission.PermissionManager;
import gg.steve.mc.pp.placeholder.PlaceholderManager;
import lombok.Data;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

@Data
public class SPlugin {
    private static SPlugin instance;
    // Store the main instance of the plugin
    private final JavaPlugin plugin;
    // Store the name of the plugin
    private final String pluginName;
    // Store manager instances to be accessed by addons
    private final MessageManager messageManager;
    private final CommandManager commandManager;
    private final PermissionManager permissionManager;
    private final EventManager eventManager;
    private final EconomyManager economyManager;
    private final PlaceholderManager placeholderManager;
    private final InventoryClickActionManager inventoryClickActionManager;
    private final GuiManager guiManager;
    private final FileManager fileManager;
    // Custom manager classes
    private final PrisonsAddonManager addonManager;
    // Any other handler classes which are not managers
    private final SQLDatabaseHandler sqlDatabaseHandler;

    public SPlugin(JavaPlugin plugin) {
        instance = this;
        this.plugin = plugin;
        // Name
        this.pluginName = "PrisonsPlus";
        // register managers
        this.messageManager = new MessageManager();
        this.commandManager = new CommandManager(instance);
        this.permissionManager = new PermissionManager();
        this.economyManager = new EconomyManager(instance);
        this.placeholderManager = new PlaceholderManager();
        this.eventManager = new EventManager(instance);
        this.inventoryClickActionManager = new InventoryClickActionManager();
        this.guiManager = new GuiManager();
        this.fileManager = new FileManager(instance);
        // Custom manager classes
        this.addonManager = new PrisonsAddonManager(instance);
        // load manager classes
        AbstractManager.loadManagers();
        // set up remaining core
        this.sqlDatabaseHandler = new SQLDatabaseHandler(instance);
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

    public static void disable() {
        Bukkit.getPluginManager().disablePlugin(SPlugin.getSPluginInstance().getPlugin());
    }

    public static SPlugin getSPluginInstance() {
        return instance;
    }
}