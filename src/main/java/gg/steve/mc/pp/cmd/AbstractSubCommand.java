package gg.steve.mc.pp.cmd;

import gg.steve.mc.pp.cmd.exception.AliasAlreadyRegisteredException;
import lombok.Data;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

@Data
public abstract class AbstractSubCommand {
    private AbstractCommand parent;
    private String command;
    private List<String> aliases;

    public AbstractSubCommand(AbstractCommand parent, String command) {
        this.parent = parent;
        this.command = command;
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
}
