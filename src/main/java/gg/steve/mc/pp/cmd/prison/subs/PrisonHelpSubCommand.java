package gg.steve.mc.pp.cmd.prison.subs;

import gg.steve.mc.pp.cmd.AbstractCommand;
import gg.steve.mc.pp.cmd.AbstractSubCommand;
import gg.steve.mc.pp.cmd.SubCommandClass;
import gg.steve.mc.pp.message.MessageManager;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

@SubCommandClass
public class PrisonHelpSubCommand extends AbstractSubCommand {

    public PrisonHelpSubCommand(AbstractCommand parent) {
        super(parent, "help", "help", 0, 1);
        this.registerAlias("h");
    }

    @Override
    public List<String> setTabCompletion(CommandSender executor, String[] arguments) {
        return new ArrayList<>();
    }

    @Override
    public void run(CommandSender executor, String[] arguments) {
        MessageManager.getInstance().sendMessage("help", executor);
    }
}
