package gg.steve.mc.pp.addons.mines.tool;

import gg.steve.mc.pp.file.FileManager;
import gg.steve.mc.pp.nbt.NBTItem;
import gg.steve.mc.pp.xseries.XItemStack;
import lombok.Data;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

@Data
public final class CreationTool {
    private static ItemStack tool;

    public CreationTool() {
        try {
            tool = XItemStack.deserialize(FileManager.getInstance().get("mines").getConfigurationSection("creation-tool"));
            NBTItem item = new NBTItem(tool);
            item.setBoolean("is-mine-creation-tool", true);
            tool = item.getItem();
        } catch (Exception e) {
            tool = new ItemStack(Material.STICK);
        }
    }

    public static ItemStack getTool() {
        if (tool != null) return tool;
        return new ItemStack(Material.STICK);
    }
}
