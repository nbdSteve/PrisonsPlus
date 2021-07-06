package gg.steve.mc.pp.cmd.prison.subs;

import gg.steve.mc.pp.addon.PrisonAddonManager;
import gg.steve.mc.pp.addon.PrisonsPlusAddon;
import gg.steve.mc.pp.addon.exception.PrisonsPlusAddonNotFoundException;
import gg.steve.mc.pp.cmd.AbstractCommand;
import gg.steve.mc.pp.cmd.AbstractSubCommand;
import gg.steve.mc.pp.file.FileManager;
import gg.steve.mc.pp.message.MessageManager;
import gg.steve.mc.pp.utility.LogUtil;
import gg.steve.mc.pp.utility.NumberFormatUtil;
import org.bukkit.command.CommandSender;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class PrisonAddonSubCommand extends AbstractSubCommand {

    public PrisonAddonSubCommand(AbstractCommand parent) {
        super(parent, "addon", "addon", 2, 3);
        this.registerAlias("a");
        this.registerAlias("addons");
    }

    public enum Argument {
        REGISTER(new String[]{"register", "load"}),
        RELOAD(new String[]{"reload", "r"}),
        UNREGISTER(new String[]{"unregister", "unload"});

        private String[] aliases;

        Argument(String[] aliases) {
            this.aliases = aliases;
        }

        public static Argument getArgumentFromString(String query) {
            for (Argument argument : Argument.values()) {
                if (Arrays.stream(argument.getAliases()).anyMatch(s -> s.equalsIgnoreCase(query))) return argument;
            }
            return null;
        }

        public String[] getAliases() {
            return aliases;
        }
    }

    @Override
    public void run(CommandSender executor, String[] arguments) {
        // p addon register addon name
        // p addon reload
        // p addon reload addon
        // p addon unregister addon
        Argument argument = Argument.getArgumentFromString(arguments[1]);
        if (argument == null) {
            this.getParent().doInvalidCommandMessage(executor);
            return;
        }
        String identifier = null;
        if (arguments.length == 3) {
            identifier = arguments[2].toLowerCase(Locale.ROOT);
            try {
                PrisonAddonManager.getInstance().getAddon(identifier);
            } catch (PrisonsPlusAddonNotFoundException e){
                MessageManager.getInstance().sendMessage("addon-not-found", executor, identifier);
                LogUtil.warning(e.getDebugMessage());
                e.printStackTrace();
                return;
            }
        } else if (argument == Argument.REGISTER || argument == Argument.UNREGISTER) {
            this.getParent().doInvalidCommandMessage(executor);
        }
        switch (argument) {
            case RELOAD:
                if (arguments.length == 3) {
                    this.reload(executor, identifier);
                } else {
                    PrisonAddonManager.getInstance().unregisterAllAddons();
                    PrisonAddonManager.getInstance().registerAllAddons();
                    MessageManager.getInstance().sendMessage("reload-all-addons", executor, NumberFormatUtil.format(PrisonAddonManager.getInstance().getRegisteredAddons().size()));
                }
                break;
            case REGISTER:
                this.register(executor, identifier);
                break;
            case UNREGISTER:
                this.unregister(executor, identifier);
                break;
            default:
                this.getParent().doInvalidCommandMessage(executor);
        }
    }

    private void reload(CommandSender executor, String identifier) {
        if (identifier == null || identifier.equalsIgnoreCase("")) {
            this.getParent().doInvalidArgumentsMessage(executor);
            return;
        }
        if (!PrisonAddonManager.getInstance().reloadAddon(identifier)) {
            MessageManager.getInstance().sendMessage("error-reloading-addon", executor, identifier, NumberFormatUtil.format(PrisonAddonManager.getInstance().getRegisteredAddons().size()));
        } else {
            MessageManager.getInstance().sendMessage("reload-single-addon", executor, identifier, NumberFormatUtil.format(PrisonAddonManager.getInstance().getRegisteredAddons().size()));
        }
    }

    private void register(CommandSender executor, String identifier) {
        if (identifier == null || identifier.equalsIgnoreCase("")) {
            this.getParent().doInvalidArgumentsMessage(executor);
            return;
        }
        if (PrisonAddonManager.getInstance().isRegistered(identifier)) {
            MessageManager.getInstance().sendMessage("addon-already-registered", executor, identifier);
            return;
        }
        if (!PrisonAddonManager.getInstance().registerAddon(identifier)) {
            MessageManager.getInstance().sendMessage("error-registering-addon", executor, identifier);
        } else {
            MessageManager.getInstance().sendMessage("register-addon", executor, identifier, NumberFormatUtil.format(PrisonAddonManager.getInstance().getRegisteredAddons().size()));
        }
    }

    private void unregister(CommandSender executor, String identifier) {
        if (identifier == null || identifier.equalsIgnoreCase("")) {
            this.getParent().doInvalidArgumentsMessage(executor);
            return;
        }
        if (PrisonAddonManager.getInstance().isUnregistered(identifier)) {
            MessageManager.getInstance().sendMessage("addon-already-unregistered", executor, identifier);
            return;
        }
        if (!PrisonAddonManager.getInstance().unregisterAddon(identifier)) {
            MessageManager.getInstance().sendMessage("error-registering-addon", executor, identifier);
        } else {
            MessageManager.getInstance().sendMessage("unregister-addon", executor, identifier, NumberFormatUtil.format(PrisonAddonManager.getInstance().getRegisteredAddons().size()));
        }
    }
}
