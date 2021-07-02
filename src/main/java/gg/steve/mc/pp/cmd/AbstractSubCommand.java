package gg.steve.mc.pp.cmd;

import gg.steve.mc.pp.cmd.exception.AliasAlreadyRegisteredException;
import gg.steve.mc.pp.permission.Permission;
import lombok.Data;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

@Data
public abstract class AbstractSubCommand {
    private AbstractCommand parent;
    private String command;
    private Permission permission;
    private int minArgLength, maxArgLength;
    private List<String> aliases;

    public AbstractSubCommand(AbstractCommand parent, String command, Permission permission, int minArgLength, int maxArgLength) {
        this.parent = parent;
        this.command = command;
        this.permission = permission;
        this.minArgLength = minArgLength;
        this.maxArgLength = maxArgLength;
    }

    public void execute(CommandSender executor, String command, String[] arguments) {
        if (!this.hasPermission(executor)) {

            return;
        }
        if (!this.isValidArgs(arguments)) {

            return;
        }
        this.run(executor, command, arguments);
    }

    public abstract void run(CommandSender executor, String command, String[] arguments);

    public boolean registerAlias(String alias) throws AliasAlreadyRegisteredException {
        if (this.aliases == null) this.aliases = new ArrayList<>();
        if (this.aliases.contains(alias)) return false;
        // Stops people from adding duplicate aliases
        for (AbstractSubCommand subCommand : this.parent.getSubCommands().values()) {
            if (subCommand.getCommand().equalsIgnoreCase(this.command)) continue;
            if (subCommand.getAliases().contains(alias)) throw new AliasAlreadyRegisteredException(alias, this);
        }
        return this.aliases.add(alias);
    }

    public boolean unregisterAlias(String alias) {
        if (this.aliases == null || this.aliases.isEmpty()) return true;
        return this.aliases.remove(alias);
    }

    public boolean hasPermission(CommandSender executor) {
        return executor.hasPermission(this.permission.getPermission());
    }

    public boolean isValidArgs(String[] arguments) {
        return this.minArgLength <= arguments.length && this.maxArgLength >= arguments.length;
    }
}
