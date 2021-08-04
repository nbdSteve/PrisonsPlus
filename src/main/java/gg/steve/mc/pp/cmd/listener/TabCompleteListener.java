package gg.steve.mc.pp.cmd.listener;

import gg.steve.mc.pp.cmd.AbstractCommand;
import gg.steve.mc.pp.cmd.CommandManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.TabCompleteEvent;

public class TabCompleteListener implements Listener {

    @EventHandler
    public void onTabComplete(TabCompleteEvent event) {
        String current = event.getBuffer().substring(1, event.getBuffer().length() - 1);
        String[] arguments = current.split(" ");
        for (AbstractCommand command : CommandManager.getInstance().getCommands().values()) {
            if (command.getAliases().contains(arguments[0]))
                event.setCompletions(command.onTabComplete(event.getSender(), arguments));
        }
    }
}
