package gg.steve.mc.pp.addons.tokens.gui;

import gg.steve.mc.pp.SPlugin;
import gg.steve.mc.pp.file.AbstractPluginFile;
import gg.steve.mc.pp.gui.AbstractGui;
import gg.steve.mc.pp.gui.exception.InvalidConfigurationFileTypeException;

public class TokenShopGui extends AbstractGui {

    public TokenShopGui(String guiUniqueName, AbstractPluginFile configuration, SPlugin sPlugin) throws InvalidConfigurationFileTypeException {
        super(guiUniqueName, configuration, sPlugin);
    }

    @Override
    public void refreshInventoryContents() {
        super.applyComponentsToInventory(super.getOwner());
    }

    @Override
    public AbstractGui createDuplicateGui() {
        try {
            return new TokenShopGui(super.getGuiUniqueName(), super.getConfiguration(), super.getSPlugin());
        } catch (InvalidConfigurationFileTypeException e) {
            e.printStackTrace();
        }
        return null;
    }
}
