package gg.steve.mc.pp;

import gg.steve.mc.pp.addon.PrisonAddonManager;
import gg.steve.mc.pp.cmd.CommandManager;
import gg.steve.mc.pp.cmd.listener.TabCompleteListener;
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
import gg.steve.mc.pp.utility.Log;
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
    // Store if the plugin is in debug mode
    private final boolean debugMode;
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
    private final PrisonAddonManager addonManager;
    // Any other handler classes which are not managers
    private final SQLDatabaseHandler sqlDatabaseHandler;

    public SPlugin(JavaPlugin plugin) {
        instance = this;
        this.plugin = plugin;
        // Name
        this.pluginName = this.plugin.getName();
        // Set debug mode
        this.debugMode = true;
        // Register managers
        this.messageManager = new MessageManager();
        this.economyManager = new EconomyManager(instance);
        this.placeholderManager = new PlaceholderManager();
        this.eventManager = new EventManager(instance);
        this.inventoryClickActionManager = new InventoryClickActionManager();
        this.guiManager = new GuiManager();
        this.fileManager = new FileManager(instance);
        this.permissionManager = new PermissionManager();
        this.commandManager = new CommandManager(instance);
        this.sqlDatabaseHandler = new SQLDatabaseHandler(instance);
        // Custom manager classes
        this.addonManager = new PrisonAddonManager(instance);
        // load manager classes
        this.enable();
        // set up remaining core
        this.doLoadDebug();
    }

    public void enable() {
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

    public void reload() {
        shutdown();
    }

    public static void disable() {
        Bukkit.getPluginManager().disablePlugin(SPlugin.getSPluginInstance().getPlugin());
    }

    public static SPlugin getSPluginInstance() {
        return instance;
    }

    private void doLoadDebug() {
        this.eventManager.registerListener(new TabCompleteListener());
        Log.info("<-------------------------------=+=-------------------------------->");
        for(String line : FileManager.CoreFiles.CONFIG.get().getStringList("logo")){
            Log.info(line);
        }
        Log.info(" ");
        Log.info("Messages loaded: " + this.messageManager.getMessages().size());
        Log.info("Files loaded: " + this.fileManager.getFiles().size());
        Log.info("Addons loaded: " + this.addonManager.getRegisteredAddons().size());
        Log.info("SQL Connected: " + (this.sqlDatabaseHandler.getInjector().getConnection() != null));
        Log.info("<-------------------------------=+=-------------------------------->");
    }
}