package gg.steve.mc.pp.cmd;

import gg.steve.mc.pp.cmd.exception.AliasAlreadyRegisteredException;
import gg.steve.mc.pp.message.MessageManager;
import gg.steve.mc.pp.permission.Permission;
import gg.steve.mc.pp.permission.PermissionManager;
import gg.steve.mc.pp.permission.exceptions.PermissionNotFoundException;
import gg.steve.mc.pp.utility.LogUtil;
import lombok.Data;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

@Data
public abstract class AbstractSubCommand {
    private final AbstractCommand parent;
    private final String command;
    private Permission permission;
    private final int minArgLength, maxArgLength;
    private boolean isNoArgCommand;
    private List<String> aliases;

    public AbstractSubCommand(AbstractCommand parent, String command, String permissionKey, int minArgLength, int maxArgLength) {
        this.parent = parent;
        this.command = command;
        try {
            this.permission = PermissionManager.getInstance().getPermissionByKey(permissionKey);
        } catch (PermissionNotFoundException e) {
            LogUtil.warning(e.getDebugMessage());
            LogUtil.warning("Setting permission for command, " + this.command + " to default (no permission required)!");
            this.permission = PermissionManager.getInstance().getDefaultPermission();
            e.printStackTrace();
        }
        this.minArgLength = minArgLength;
        this.maxArgLength = maxArgLength;
        if (this.minArgLength == 0) this.isNoArgCommand = true;
    }

    public void execute(CommandSender executor, String[] arguments) {
        if (!this.hasPermission(executor)) {
            MessageManager.getInstance().sendMessage("no-permission", executor, this.permission.getPermission());
            return;
        }
        if (!this.isValidArgs(arguments)) {
            this.getParent().doInvalidArgumentsMessage(executor);
            return;
        }
        this.run(executor, arguments);
    }

    public abstract void run(CommandSender executor, String[] arguments);

    public boolean registerAlias(String alias) {
        if (this.aliases == null) this.aliases = new ArrayList<>();
        if (this.aliases.contains(alias)) return false;
        // Stops people from adding duplicate aliases
        for (AbstractSubCommand subCommand : this.parent.getSubCommands().values()) {
            if (subCommand.getCommand().equalsIgnoreCase(this.command)) continue;
            try {
                if (subCommand.getAliases().contains(alias)) throw new AliasAlreadyRegisteredException(alias, this);
            } catch (AliasAlreadyRegisteredException e) {
                LogUtil.warning(e.getDebugMessage());
                e.printStackTrace();
            }
        }
        return this.aliases.add(alias);
    }

    public boolean unregisterAlias(String alias) {
        if (this.aliases == null || this.aliases.isEmpty()) return true;
        return this.aliases.remove(alias);
    }

    public boolean hasPermission(CommandSender executor) {
        if (this.permission == null) return true;
        return executor.hasPermission(this.permission.getPermission());
    }

    public boolean isValidArgs(String[] arguments) {
        return this.minArgLength <= arguments.length && this.maxArgLength >= arguments.length;
    }

    public boolean isExecutor(String command) {
        if (this.command.equalsIgnoreCase(command)) return true;
        for (String alias : this.aliases) {
            if (alias.equalsIgnoreCase(command)) return true;
        }
        return false;
    }
}
