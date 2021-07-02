package gg.steve.mc.pp.cmd;

import gg.steve.mc.pp.SPlugin;
import gg.steve.mc.pp.permission.Permission;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginIdentifiableCommand;
import org.bukkit.command.TabCompleter;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractCommand extends Command implements PluginIdentifiableCommand, TabCompleter {
    private Permission permission;
    private Map<String, AbstractSubCommand> subCommands;

    public AbstractCommand(String name, Permission permission) {
        super(name);
        this.permission = permission;
    }

    @Override
    public boolean execute(CommandSender sender, String commandLabel, String[] args) {
        if (!this.permission.hasPermission(sender)) {
            // send the player a message
        } else {
            if (this.subCommands != null && !this.subCommands.isEmpty()) {

            }
            run(sender, commandLabel, args);
        }
        return true;
    }

    @Override
    public Plugin getPlugin() {
        return SPlugin.getSPluginInstance().getPlugin();
    }

    protected abstract void run(CommandSender executor, String command, String[] arguments);

    public abstract void registerAllSubCommands();

    public boolean registerSubCommand(AbstractSubCommand subCommand) {
        if (this.subCommands == null) this.subCommands = new HashMap<>();
        if (this.subCommands.containsKey(subCommand.getCommand())) return false;
        return this.subCommands.put(subCommand.getCommand(), subCommand) != null;
    }

    public boolean unregisterSubCommand(String command) {
        if (this.subCommands == null || this.subCommands.isEmpty()) return true;
        return this.subCommands.remove(command) != null;
    }

    public Map<String, AbstractSubCommand> getSubCommands() {
        return subCommands;
    }
}
