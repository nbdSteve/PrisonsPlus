package gg.steve.mc.pp.gui.action.types;

import gg.steve.mc.pp.gui.AbstractGui;
import gg.steve.mc.pp.gui.action.AbstractInventoryClickAction;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class PermissionInventoryClickAction extends AbstractInventoryClickAction {

    public PermissionInventoryClickAction() {
        super("permission", 2);
    }

    @Override
    public ItemStack getRenderedItem(Player player, ConfigurationSection section) {
        boolean hasPermission = player.hasPermission(getNodeFromString(section.getString("action")));
        if (hasPermission) {

        }
        return null;
    }

    @Override
    public void onClick(AbstractGui gui, Player player, ConfigurationSection section, int slot) {

    }

    public String getNodeFromString(String actionFromConfig) {
        String[] parts = actionFromConfig.split(":");
        return parts[1];
    }
}
