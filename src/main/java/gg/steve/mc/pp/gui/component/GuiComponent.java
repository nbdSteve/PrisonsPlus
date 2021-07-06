package gg.steve.mc.pp.gui.component;

import gg.steve.mc.pp.gui.AbstractGui;
import gg.steve.mc.pp.gui.Clickable;
import gg.steve.mc.pp.gui.action.AbstractInventoryClickAction;
import gg.steve.mc.pp.gui.action.InventoryClickActionManager;
import gg.steve.mc.pp.gui.exception.InventoryClickActionNotFoundException;
import gg.steve.mc.pp.utility.LogUtil;
import lombok.Data;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

@Data
public class GuiComponent implements Clickable {
    private AbstractGui gui;
    private ConfigurationSection section;
    private int slot;
    private AbstractInventoryClickAction clickAction;
    private ItemStack item;

    public GuiComponent(AbstractGui gui, ConfigurationSection section, int slot) {
        this.gui = gui;
        this.section = section;
        this.slot = slot;
        try {
            this.clickAction = InventoryClickActionManager.getInstance().getClickActionByUniqueName(section.getString("action"));
        } catch (InventoryClickActionNotFoundException e) {
            this.item = new ItemStack(Material.STONE);
            LogUtil.warning(e.getDebugMessage());
            e.printStackTrace();
        }
    }

    public ItemStack getRenderedItem(Player player) {
        if (this.clickAction != null) return this.clickAction.getRenderedItem(player, this.section);
        return this.item;
    }

    @Override
    public void onClick(Player player) {
        this.clickAction.onClick(this.gui, player, this.section, this.slot);
    }

    @Override
    public void onClick(AbstractGui gui, Player player, ConfigurationSection section, int slot) {
        this.clickAction.onClick(this.gui, player, this.section, this.slot);
    }
}
