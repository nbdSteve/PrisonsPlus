package gg.steve.mc.pp.gui.listener;

import gg.steve.mc.pp.gui.AbstractGui;
import gg.steve.mc.pp.gui.GuiManager;
import gg.steve.mc.pp.gui.exception.AbstractGuiNotFoundException;
import gg.steve.mc.pp.utility.Log;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class GuiEventListener implements Listener {
    private AbstractGui gui;

    public GuiEventListener(AbstractGui gui) {
        this.gui = gui;
    }

    @EventHandler
    public void guiItemClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        if (this.gui.getViewers().contains(player.getUniqueId()) && event.getRawSlot() < this.gui.getSize()) {
            event.setCancelled(true);
            if (!this.gui.getComponents().containsKey(event.getSlot())) return;
            this.gui.getComponents().get(event.getSlot()).onClick(player);
        }
    }

    @EventHandler
    public void guiClose(InventoryCloseEvent event) {
        Player player = (Player) event.getPlayer();
        if (this.gui.getViewers().contains(player.getUniqueId()) &&
                this.gui.isHasParentGui()) {
            this.gui.close(player);
            try {
                GuiManager.getInstance().openGui(player, this.gui.getParentGuiUniqueName());
            } catch (AbstractGuiNotFoundException e) {
                Log.warning(e.getDebugMessage());
                e.printStackTrace();
            }
        } else {
            this.gui.getViewers().remove(player.getUniqueId());
        }
    }

    @EventHandler
    public void guiCloseByQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        this.gui.getViewers().remove(player.getUniqueId());
        GuiManager.getInstance().removePlayerFromGuiMap(player);
    }
}
