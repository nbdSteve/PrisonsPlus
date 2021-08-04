package gg.steve.mc.pp.cmd.prison;

import gg.steve.mc.pp.addon.PrisonAddonManager;
import gg.steve.mc.pp.addon.PrisonsPlusAddon;
import gg.steve.mc.pp.cmd.AbstractCommand;
import gg.steve.mc.pp.cmd.AbstractSubCommand;
import gg.steve.mc.pp.cmd.prison.subs.PrisonAddonSubCommand;
import gg.steve.mc.pp.cmd.prison.subs.PrisonHelpSubCommand;
import gg.steve.mc.pp.cmd.prison.subs.PrisonReloadSubCommand;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class PrisonCommand extends AbstractCommand {

    public PrisonCommand() {
        super("prison", "prison");
    }

    @Override
    public void registerAllSubCommands() {
        this.registerSubCommand(new PrisonHelpSubCommand(this));
        this.registerSubCommand(new PrisonReloadSubCommand(this));
        this.registerSubCommand(new PrisonAddonSubCommand(this));
    }

    @Override
    public void runOnNoArgsGiven(CommandSender sender) {
        if (this.getSubCommands() != null && this.getSubCommands().containsKey("help"))
            this.getSubCommands().get("help").execute(sender, new String[0]);
    }

    @Override
    public void registerAliases() {
        this.registerAlias("prisons");
        this.registerAlias("p");
        this.registerAlias("p+");
        this.registerAlias("prisons+");
        this.registerAlias("prison+");
        this.registerAlias("prisonsplus");
    }

    @Override
    public List<String> onTabComplete(CommandSender executor, String[] arguments) {
        List<String> options = new ArrayList<>();
        if (arguments.length < 2) {
            for (AbstractSubCommand subCommand : this.getSubCommands().values()) {
                options.add(subCommand.getCommand());
            }
        } else if (arguments.length == 2 && this.getSubCommands().get("addon").isExecutor(arguments[1])) {
            for (PrisonAddonSubCommand.Argument argument : PrisonAddonSubCommand.Argument.values()) {
                options.add(argument.name().toLowerCase(Locale.ROOT));
            }
        } else if (arguments.length == 3 && this.getSubCommands().get("addon").isExecutor(arguments[1])) {
            PrisonAddonSubCommand.Argument argument = PrisonAddonSubCommand.Argument.getArgumentFromString(arguments[2]);
            if (argument == null) return options;
            switch (argument) {
                case UNREGISTER:
                case RELOAD:
                    for (PrisonsPlusAddon addon : PrisonAddonManager.getInstance().getRegisteredAddons()) {
                        options.add(addon.getIdentifier().toLowerCase(Locale.ROOT));
                    }
                    break;
                case REGISTER:
                    options.addAll(PrisonAddonManager.getInstance().getUnregisteredAddonIdentifiers());
                    break;
                case INFO:
                    for (PrisonsPlusAddon addon : PrisonAddonManager.getInstance().getRegisteredAddons()) {
                        options.add(addon.getIdentifier().toLowerCase(Locale.ROOT));
                    }
                    options.addAll(PrisonAddonManager.getInstance().getUnregisteredAddonIdentifiers());
                    break;
            }
        }
        return options;
    }
}
