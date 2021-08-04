package gg.steve.mc.pp.gui.action;

import gg.steve.mc.pp.gui.AbstractGui;
import lombok.Data;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

@Data
public abstract class AbstractInventoryClickAction {
    private final String uniqueName;
    private final int args;

    public AbstractInventoryClickAction(String uniqueName, int args) {
        this.uniqueName = uniqueName;
        this.args = args;
    }

    public ItemStack getRenderedItem(Player player, ConfigurationSection section) {
        return new ItemStack(Material.STONE);
    }

    public abstract void onClick(AbstractGui gui, Player player, ConfigurationSection section, int slot);

    public boolean isUniqueNameMatch(String query) {
        return this.uniqueName.equalsIgnoreCase(query);
    }

    public boolean isValidFormat(String actionFromConfig) {
        String[] parts = actionFromConfig.split(":");
        if (!isUniqueNameMatch(parts[0])) return false;
        return this.args == parts.length;
    }
}
