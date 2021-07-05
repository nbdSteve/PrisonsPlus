package gg.steve.mc.pp.cmd.prison.subs;

import gg.steve.mc.pp.cmd.AbstractCommand;
import gg.steve.mc.pp.cmd.AbstractSubCommand;
import gg.steve.mc.pp.cmd.exception.AliasAlreadyRegisteredException;
import gg.steve.mc.pp.message.MessageManager;
import gg.steve.mc.pp.utility.LogUtil;
import org.bukkit.command.CommandSender;

public class PrisonHelpSubCommand extends AbstractSubCommand {

    public PrisonHelpSubCommand(AbstractCommand parent) {
        super(parent, "help", "help", 0, 1);
        try {
            this.registerAlias("h");
        } catch (AliasAlreadyRegisteredException e) {
            LogUtil.warning(e.getDebugMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void run(CommandSender executor, String[] arguments) {
        MessageManager.getInstance().sendMessage("help", executor);
    }
}
