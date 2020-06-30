package gg.steve.mc.pp.framework.cmd;

import gg.steve.mc.pp.framework.message.DebugMessage;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

public abstract class MainCommand implements CommandExecutor {
    private List<SubCommand> subs;
    private SubCommand help;

    public MainCommand() {
        subs = new ArrayList<>();
    }

    public void addSubCommand(SubCommand sub) {
        subs.add(sub);
    }

    public void addHelpCommand(SubCommand sub) {
        if (!subs.contains(sub)) subs.add(sub);
        this.help = sub;
    }

    public SubCommand getHelp() {
        return this.help;
    }

    public boolean onCommand(CommandSender sender, String[] args) {
        if (args.length == 0) {
            return this.noArgsCommand(sender, args);
        }
        for (SubCommand subCommand : subs) {
            if (!subCommand.isExecutor(args[0])) continue;
            if (!subCommand.isValidCommand(sender, args)) return true;
            subCommand.onCommand(sender, args);
            return true;
        }
        DebugMessage.INVALID_COMMAND.message(sender);
        return true;
    }

    public abstract boolean noArgsCommand(CommandSender sender, String[] args);
}
