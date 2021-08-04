package gg.steve.mc.pp.gui;

import gg.steve.mc.pp.SPlugin;
import gg.steve.mc.pp.file.types.GuiPluginFile;
import gg.steve.mc.pp.gui.exception.AbstractGuiNotFoundException;
import gg.steve.mc.pp.gui.exception.InvalidConfigurationFileTypeException;
import gg.steve.mc.pp.manager.AbstractManager;
import gg.steve.mc.pp.manager.ManagerClass;
import org.bukkit.entity.Player;

import java.util.*;

@ManagerClass
public class GuiManager extends AbstractManager {
    private static GuiManager instance;
    private Map<String, gg.steve.mc.pp.gui.AbstractGui> guis;
    private Map<UUID, List<gg.steve.mc.pp.gui.AbstractGui>> playerGuis;

    public GuiManager() {
        instance = this;
        AbstractManager.registerManager(instance);
    }

    @Override
    public void onLoad() {
        this.playerGuis = new HashMap<>();
    }

    @Override
    public void onShutdown() {
        if (this.guis != null && !this.guis.isEmpty()) {
            this.guis.forEach((s, abstractGui) -> abstractGui.onShutdown());
            this.guis.clear();
        }
        if (this.playerGuis != null && !this.playerGuis.isEmpty()) this.playerGuis.clear();
    }

    @Override
    protected String getManagerName() {
        return "Gui";
    }

    public boolean guiExists(String guiUniqueName) {
        if (guis == null || guis.isEmpty()) return false;
        return guis.containsKey(guiUniqueName);
    }

    public boolean registerGuiFromFile(GuiPluginFile file) {
        String unique = file.get().getString("unique-name");
        gg.steve.mc.pp.gui.AbstractGui gui = null;
        try {
            gui = new gg.steve.mc.pp.gui.SGui(unique, file, SPlugin.getSPluginInstance());
        } catch (InvalidConfigurationFileTypeException e) {
            e.printStackTrace();
            return false;
        }
        return this.registerGui(gui);
    }

    public boolean registerGui(gg.steve.mc.pp.gui.AbstractGui gui) {
        if (this.guis == null) this.guis = new HashMap<>();
        if (this.guis.containsKey(gui.getGuiUniqueName())) return false;
        return this.guis.put(gui.getGuiUniqueName(), gui) != null;
    }

    public boolean unregisterGui(String guiUniqueName) {
        if (this.guis == null || this.guis.isEmpty()) return true;
        if (!this.guis.containsKey(guiUniqueName)) return false;
        return this.guis.remove(guiUniqueName) != null;
    }

    public boolean removePlayerFromGuiMap(Player player) {
        if (this.playerGuis == null || this.playerGuis.isEmpty()) return true;
        return this.playerGuis.remove(player.getUniqueId()) != null;
    }

    public void openGui(Player player, String guiUniqueName) throws AbstractGuiNotFoundException {
        if (!this.guiExists(guiUniqueName)) throw new AbstractGuiNotFoundException(guiUniqueName);
        if (this.playerGuis.containsKey(player.getUniqueId())) {
            for (gg.steve.mc.pp.gui.AbstractGui gui : this.playerGuis.get(player.getUniqueId())) {
                if (gui.getGuiUniqueName().equalsIgnoreCase(guiUniqueName)) {
                    gui.open();
                    return;
                }
            }
        } else {
            this.playerGuis.put(player.getUniqueId(), new ArrayList<>());
        }
        gg.steve.mc.pp.gui.AbstractGui gui = this.guis.get(guiUniqueName).createDuplicateGui();
        gui.setOwner(player);
        this.playerGuis.get(player.getUniqueId()).add(gui);
        gui.open();
    }

    public static GuiManager getInstance() {
        return instance;
    }
}
