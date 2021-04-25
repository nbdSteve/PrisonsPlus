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
public abstract class PrisonsPlusAddon implements Loadable {
    private final String identifier;
    private final PrisonsPlusPlugin prisonsPlusPlugin;
    private String name;
    private List<Listener> listeners;
    private Map<String, TabCompleter> commands;

    public PrisonsPlusAddon(String identifier) {
        this.identifier = identifier;
        this.prisonsPlusPlugin = PrisonsPlusPlugin.getInstance();
        this.listeners = new ArrayList<>();
        this.commands = new HashMap<>();
        this.name = name;
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
        if (this.commands != null && !this.commands.isEmpty()) this.commands.clear();
        if (this.listeners != null && !this.listeners.isEmpty()) this.listeners.clear();
    }

    public void addCommand(String command, TabCompleter completer) {
        if (this.commands == null) this.commands = new HashMap<>();
        this.commands.put(command, completer);
    }

    public void removeCommand(String command) {
        if (this.commands == null || this.commands.isEmpty()) return;
        this.commands.remove(command);
    }

    public void addListener(Listener listener) {
        if (this.listeners == null) this.listeners = new ArrayList<>();
        if (this.listeners.contains(listener)) return;
        this.listeners.add(listener);
    }

    public void removeListener(Listener listener) {
        if (this.listeners == null || this.listeners.isEmpty()) return;
        this.listeners.remove(listener);
    }

    public abstract String getVersion();

    public abstract String getAuthor();
}