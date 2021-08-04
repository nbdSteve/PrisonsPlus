package gg.steve.mc.pp.gui.action.types;

import gg.steve.mc.pp.gui.AbstractGui;
import gg.steve.mc.pp.gui.action.AbstractInventoryClickAction;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

public class DefaultClickAction extends AbstractInventoryClickAction {

    public DefaultClickAction() {
        super("none", 1);
    }

    @Override
    public void onClick(AbstractGui gui, Player player, ConfigurationSection section, int slot) {

    }
}
