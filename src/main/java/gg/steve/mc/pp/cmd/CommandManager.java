package gg.steve.mc.pp.cmd;

import gg.steve.mc.pp.SPlugin;
import gg.steve.mc.pp.manager.AbstractManager;
import gg.steve.mc.pp.manager.ManagerClass;
import gg.steve.mc.pp.utility.LogUtil;
import org.bukkit.command.SimpleCommandMap;
import org.bukkit.plugin.SimplePluginManager;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@ManagerClass
public class CommandManager extends AbstractManager {
    private static CommandManager instance;
    private SPlugin sPlugin;
    private SimpleCommandMap commandMap;
    private Map<String, AbstractCommand> commands;

    public CommandManager(SPlugin sPlugin) {
        instance = this;
        this.sPlugin = sPlugin;
        AbstractManager.addManager(instance);
    }

    @Override
    public void onLoad() {
        SimplePluginManager pluginManager = (SimplePluginManager) this.sPlugin.getPlugin().getServer().getPluginManager();
        Field map;
        try {
            map = pluginManager.getClass().getDeclaredField("commandMap");
        } catch (NoSuchFieldException e) {
            LogUtil.warning("Unable to locate command map class, unable to register custom plugin commands.");
            e.printStackTrace();
            return;
        }
        map.setAccessible(true);
        try {
            this.commandMap = (SimpleCommandMap) map.get(pluginManager);
        } catch (IllegalAccessException e) {
            LogUtil.warning("Exception thrown whilst trying to assign command map, unable to register custom plugin commands.");
            e.printStackTrace();
        }
    }

    @Override
    public void onShutdown() {
        if (this.commandMap != null && this.commands != null && !this.commands.isEmpty()) {
            this.commands.forEach((s, command) ->  {
                if (this.commandMap.getCommand(s) != null) Objects.requireNonNull(this.commandMap.getCommand(s)).unregister(this.commandMap);
            });
        }
        if (this.commands != null && !this.commands.isEmpty()) this.commands.clear();
    }

    @Override
    protected String getManagerName() {
        return "Command";
    }

    public boolean registerCommand(AbstractCommand command) {
        if (this.commandMap == null) return false;
        if (this.commands == null) this.commands = new HashMap<>();
        if (this.commands.containsKey(command.getName())) return false;
        this.commands.put(command.getName(), command);
        return this.commandMap.register(this.sPlugin.getPluginName(), command);
    }

    public boolean unregisterCommand(String command) {
        if (this.commandMap == null || this.commands == null || this.commands.isEmpty()) return true;
        if (!this.commands.containsKey(command)) return false;
        this.commands.remove(command);
        if (this.commandMap.getCommand(command) == null) return true;
        return Objects.requireNonNull(this.commandMap.getCommand(command)).unregister(this.commandMap);
    }

    public static CommandManager getInstance() {
        return instance;
    }
}