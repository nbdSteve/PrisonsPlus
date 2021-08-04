package gg.steve.mc.pp.gui.action.types;

import gg.steve.mc.pp.gui.AbstractGui;
import gg.steve.mc.pp.gui.GuiManager;
import gg.steve.mc.pp.gui.action.AbstractInventoryClickAction;
import gg.steve.mc.pp.gui.exception.AbstractGuiNotFoundException;
import gg.steve.mc.pp.utility.Log;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

public class BackInventoryClickAction extends AbstractInventoryClickAction {

    public BackInventoryClickAction() {
        super("back", 1);
    }

    @Override
    public void onClick(AbstractGui gui, Player player, ConfigurationSection section, int slot) {
        if (!gui.isHasParentGui()) return;
        gui.close(player);
        try {
            GuiManager.getInstance().openGui(player, gui.getParentGuiUniqueName());
        } catch (AbstractGuiNotFoundException e) {
            Log.warning(e.getDebugMessage());
            e.printStackTrace();
        }
    }
}