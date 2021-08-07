package gg.steve.mc.pp.gui;

import gg.steve.mc.pp.SPlugin;
import gg.steve.mc.pp.event.EventManager;
import gg.steve.mc.pp.file.AbstractPluginFile;
import gg.steve.mc.pp.gui.component.GuiComponent;
import gg.steve.mc.pp.gui.exception.InvalidConfigurationFileTypeException;
import gg.steve.mc.pp.gui.exception.UnableToCreateBukkitInventoryException;
import gg.steve.mc.pp.gui.listener.GuiEventListener;
import gg.steve.mc.pp.manager.Loadable;
import gg.steve.mc.pp.utility.ColorUtil;
import gg.steve.mc.pp.utility.Log;
import gg.steve.mc.pp.utility.SoundUtil;
import lombok.Data;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;

import java.util.*;

@Data
public abstract class AbstractGui implements Loadable {
    private UUID guiId;
    private String guiUniqueName, inventoryName, parentGuiUniqueName;
    private int size, totalPages, currentPage;
    private boolean hasParentGui, playersCanTakeItems;
    private Inventory inventory;
    private InventoryType type;
    private Map<Integer, GuiComponent> components;
    private List<UUID> viewers;
    private AbstractPluginFile configuration;
    private Player owner;
    private SPlugin sPlugin;
    private GuiEventListener listener;

    public AbstractGui(String guiUniqueName, AbstractPluginFile configuration, SPlugin sPlugin) throws InvalidConfigurationFileTypeException {
        if (!configuration.getKey().equalsIgnoreCase("gui")) {
            throw new InvalidConfigurationFileTypeException(configuration);
        }
        this.guiId = UUID.randomUUID();
        this.guiUniqueName = guiUniqueName;
        this.configuration = configuration;
        this.sPlugin = sPlugin;
        this.size = this.configuration.get().getInt("size");
        this.totalPages = this.configuration.get().getInt("pages");
        this.currentPage = 0;
        this.hasParentGui = !this.configuration.get().getString("parent-gui").equalsIgnoreCase("none");
        if (this.hasParentGui) {
            this.parentGuiUniqueName = this.configuration.get().getString("parent-gui");
        }
        this.inventoryName = ColorUtil.colorize(this.configuration.get().getString("inventory-name"));
        this.playersCanTakeItems = this.configuration.get().getBoolean("players-can-take-items");
        this.components = new HashMap<>();
        this.viewers = new ArrayList<>();
        this.listener = new GuiEventListener(this);
        EventManager.getInstance().registerListener(this.listener);
    }

    public abstract void refreshInventoryContents();

    public abstract AbstractGui createDuplicateGui();

    public boolean setComponentInSlot(int slot, GuiComponent component) {
        if (this.components == null) this.components = new HashMap<>();
        return this.components.put(slot, component) != null;
    }

    public void applyComponentsToInventory(Player viewer) {
        if (this.inventory == null) return;
        this.inventory.clear();
        for (int slot : this.components.keySet()) {
            this.inventory.setItem(slot, this.components.get(slot).getRenderedItem(viewer));
        }
    }

    public void setInventoryName(List<String> placeholders, List<String> replacements) {
        String name = this.configuration.get().getString("inventory-name");
        this.setInventoryName(name, placeholders, replacements);
    }

    public void setInventoryName(String name, List<String> placeholders, List<String> replacements) {
        if (name == null || name.equalsIgnoreCase("")) return;
        for (int i = 0; i < placeholders.size(); i++) {
            try {
                replacements.get(i);
            } catch (IndexOutOfBoundsException e) {
                Log.warning("Unable to perform replacement for placeholder: " + placeholders.get(i) + ", because the replacement does not exist.");
                break;
            }
            name.replaceAll(placeholders.get(i), replacements.get(i));
        }
        this.inventoryName = ColorUtil.colorize(name);
    }

    public void open() {
        if (this.owner.isOnline()) {
            this.open(this.owner);
        }
    }

    public void open(Player player) {
        if (this.inventory == null) {
            try {
                this.createInventory();
            } catch (UnableToCreateBukkitInventoryException e) {
                e.printStackTrace();
            }
        }
        this.applyComponentsToInventory(player);
        this.viewers.add(player.getUniqueId());
        player.openInventory(this.inventory);
        SoundUtil.playSound(this.configuration.get().getConfigurationSection("sounds"), "open", player);
    }

    public void close(Player player) {
        this.viewers.remove(player.getUniqueId());
        player.closeInventory();
        SoundUtil.playSound(this.configuration.get().getConfigurationSection("sounds"), "close", player);
    }

    public void close() {
        if (this.owner != null && this.owner.isOnline()) this.close(this.owner);
    }

    public boolean createInventory() throws UnableToCreateBukkitInventoryException {
        try {
            this.type = InventoryType.valueOf(this.configuration.get().getString("type").toUpperCase(Locale.ROOT));
            this.inventory = Bukkit.createInventory(null, this.type, this.inventoryName);
        } catch (Exception e) {
            this.inventory = Bukkit.createInventory(null, this.size, this.inventoryName);
        }
        for (String key : this.configuration.get().getKeys(false)) {
            try {
                Integer.parseInt(key);
            } catch (NumberFormatException e) {
                continue;
            }
            ConfigurationSection section = this.configuration.get().getConfigurationSection(key);
            for (int slot : section.getIntegerList("slots")) {
                this.setComponentInSlot(slot, new GuiComponent(this, section, slot));
            }
        }
        return true;
    }

    public boolean nextPage() {
        if (this.currentPage + 1 > this.totalPages) return false;
        this.currentPage++;
        refreshInventoryContents();
        SoundUtil.playSound(this.configuration.get().getConfigurationSection("sounds"), "next", this.owner);
        return true;
    }

    public boolean previousPage() {
        if (this.currentPage - 1 < 0) return false;
        this.currentPage--;
        refreshInventoryContents();
        SoundUtil.playSound(this.configuration.get().getConfigurationSection("sounds"), "previous", this.owner);
        return true;
    }

    @Override
    public void onLoad() {
    }

    @Override
    public void onShutdown() {
        if (this.viewers != null && !this.viewers.isEmpty()) {
            this.viewers.forEach(uuid -> Bukkit.getPlayer(uuid).closeInventory());
            this.viewers.clear();
        }
        if (this.components != null && !this.components.isEmpty()) this.components.clear();
    }
}
