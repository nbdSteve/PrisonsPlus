package gg.steve.mc.pp.gui;

import gg.steve.mc.pp.manager.AbstractManager;
import gg.steve.mc.pp.manager.ManagerClass;

@ManagerClass
public class GuiManager extends AbstractManager {
    private static GuiManager instance;

    public GuiManager() {
        instance = this;
        AbstractManager.registerManager(instance);
    }

    @Override
    public void onLoad() {

    }

    @Override
    public void onShutdown() {

    }

    @Override
    protected String getManagerName() {
        return "Gui";
    }
}
