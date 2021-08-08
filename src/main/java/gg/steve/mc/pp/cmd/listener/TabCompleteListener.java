package gg.steve.mc.pp.cmd.listener;

import gg.steve.mc.pp.cmd.AbstractCommand;
import gg.steve.mc.pp.cmd.AbstractSubCommand;
import gg.steve.mc.pp.cmd.CommandManager;
import gg.steve.mc.pp.utility.Log;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.TabCompleteEvent;

import java.util.List;

public class TabCompleteListener implements Listener {

    @EventHandler
    public void onTabComplete(TabCompleteEvent event) {
        String current = event.getBuffer().substring(1, event.getBuffer().length() - 1);
        String[] arguments = current.split(" ");
        for (AbstractCommand command : CommandManager.getInstance().getCommands().values()) {
            if (command.getName().contains(arguments[0]) || command.getAliases().contains(arguments[0])) {
                if (arguments.length > 1) {
                    for (AbstractSubCommand subCommand : command.getSubCommands().values()) {
                        if (subCommand.isExecutor(arguments[1])) {
                            List<String> completions = subCommand.setTabCompletion(event.getSender(), arguments);
                            if (completions != null) event.setCompletions(completions);
                        }
                    }
                } else {
                    event.setCompletions(command.onTabComplete(event.getSender(), arguments));
                }
            }
        }
    }
}