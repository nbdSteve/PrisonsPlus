package gg.steve.mc.pp.addon;

import gg.steve.mc.pp.PrisonsPlusPlugin;
import gg.steve.mc.pp.manager.Loadable;
import lombok.Data;
import org.bukkit.Bukkit;
import org.bukkit.command.TabCompleter;
import org.bukkit.event.Listener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public abstract class PrisonPlusAddon implements Loadable {
    private final String identifier;
    private List<Listener> listeners;
    private Map<String, TabCompleter> commands;
    private final PrisonsPlusPlugin prisonsPlusPlugin;

    public PrisonPlusAddon(String identifier) {
        this.identifier = identifier;
        this.listeners = new ArrayList<>();
        this.commands = new HashMap<>();
        this.prisonsPlusPlugin = PrisonsPlusPlugin.getInstance();
    }

    public void register() {
        this.onLoad();
        this.commands.forEach((command, completer) -> {
            this.getPrisonsPlusPlugin().getCommand(command).setTabCompleter(completer);
        });
        this.listeners.forEach(listener -> {
            Bukkit.getServer().getPluginManager().registerEvents(listener, this.getPrisonsPlusPlugin());
        });
    }

    public void unregister() {
        this.onShutdown();
    }

    public abstract String getVersion();

    public abstract String getAuthor();


}
