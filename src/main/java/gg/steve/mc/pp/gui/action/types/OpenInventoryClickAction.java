package gg.steve.mc.pp.gui.action.types;

import gg.steve.mc.pp.gui.AbstractGui;
import gg.steve.mc.pp.gui.GuiManager;
import gg.steve.mc.pp.gui.action.AbstractInventoryClickAction;
import gg.steve.mc.pp.gui.exception.AbstractGuiNotFoundException;
import gg.steve.mc.pp.utility.Log;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

public class OpenInventoryClickAction extends AbstractInventoryClickAction {

    public OpenInventoryClickAction() {
        super("open", 2);
    }

    @Override
    public void onClick(AbstractGui gui, Player player, ConfigurationSection section, int slot) {
        gui.close(player);
        try {
            GuiManager.getInstance().openGui(player, getGuiUniqueName(section.getString("action")));
        } catch (AbstractGuiNotFoundException e) {
            Log.warning(e.getDebugMessage());
            e.printStackTrace();
        }
    }

    public String getGuiUniqueName(String actionFromConfig) {
        return actionFromConfig.split(":")[1];
    }
}
