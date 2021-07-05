package gg.steve.mc.pp.cmd;

import gg.steve.mc.pp.SPlugin;
import gg.steve.mc.pp.message.MessageManager;
import gg.steve.mc.pp.permission.Permission;
import gg.steve.mc.pp.permission.PermissionManager;
import gg.steve.mc.pp.permission.exceptions.PermissionNotFoundException;
import gg.steve.mc.pp.utility.LogUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginIdentifiableCommand;
import org.bukkit.command.TabCompleter;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class AbstractCommand extends Command implements PluginIdentifiableCommand, TabCompleter {
    private Permission permission;
    private final String name;
    private Map<String, AbstractSubCommand> subCommands;
    private List<String> aliases;

    public AbstractCommand(String name, String permissionKey) {
        super(name);
        this.name = name;
        try {
            this.permission = PermissionManager.getInstance().getPermissionByKey(permissionKey);
        } catch (PermissionNotFoundException e) {
            LogUtil.warning(e.getDebugMessage());
            LogUtil.warning("Setting permission for command, " + name + " to default (no permission required)!");
            this.permission = PermissionManager.getInstance().getDefaultPermission();
            e.printStackTrace();
        }
        this.registerAliases();
        if (this.aliases != null) this.setAliases(this.aliases);
        if (this.subCommands == null) this.subCommands = new HashMap<>();
        this.registerAllSubCommands();
    }

    @Override
    public boolean execute(CommandSender sender, String commandLabel, String[] args) {
        if (this.isPermissionRequired() && !this.permission.hasPermission(sender)) {
            MessageManager.getInstance().sendMessage("no-permission", sender, this.permission.getPermission());
        } else {
            if (this.subCommands == null || this.subCommands.isEmpty()) {
                this.runOnNoArgsGiven(sender);
                return true;
            }
            if (args.length == 0) for (AbstractSubCommand subCommand : this.subCommands.values()) {
                if (subCommand.isNoArgCommand()) {
                    subCommand.execute(sender, args);
                    return true;
                }
            }
            if (args.length == 0) {
                this.runOnNoArgsGiven(sender);
                return true;
            }
            for (AbstractSubCommand subCommand : this.subCommands.values()) {
                if (subCommand.isExecutor(args[0])) {
                    subCommand.execute(sender, args);
                    return true;
                }
            }
            MessageManager.getInstance().sendMessage("invalid-command", sender);
        }
        return true;
    }

    @Override
    public Plugin getPlugin() {
        return SPlugin.getSPluginInstance().getPlugin();
    }

    public abstract void registerAllSubCommands();

    public abstract void runOnNoArgsGiven(CommandSender sender);

    public abstract void registerAliases();

    public boolean registerAlias(String alias) {
        if (this.aliases == null) this.aliases = new ArrayList<>();
        if (this.aliases.contains(alias)) return false;
        return this.aliases.add(alias);
    }

    public boolean registerSubCommand(AbstractSubCommand subCommand) {
        if (this.subCommands == null) this.subCommands = new HashMap<>();
        if (this.subCommands.containsKey(subCommand.getCommand())) return false;
        return this.subCommands.put(subCommand.getCommand(), subCommand) != null;
    }

    public boolean unregisterSubCommand(String command) {
        if (this.subCommands == null || this.subCommands.isEmpty()) return true;
        return this.subCommands.remove(command) != null;
    }

    public boolean isPermissionRequired() {
        return this.permission != null && !this.permission.getPermission().equalsIgnoreCase("");
    }

    public Map<String, AbstractSubCommand> getSubCommands() {
        return subCommands;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> options = new ArrayList<>();
        if (this.subCommands != null && !this.subCommands.isEmpty()) {
            for (AbstractSubCommand subCommand : this.subCommands.values()) {
                options.add(subCommand.getCommand());
            }
        }
        return options;
    }
}
