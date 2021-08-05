package gg.steve.mc.pp.addons.mines.listener;

import gg.steve.mc.pp.addons.mines.coords.Coordinate;
import gg.steve.mc.pp.addons.mines.create.CreationStateManager;
import gg.steve.mc.pp.addons.mines.create.MineCreationBuilder;
import gg.steve.mc.pp.addons.mines.create.MineCreationState;
import gg.steve.mc.pp.addons.mines.tool.CreationTool;
import gg.steve.mc.pp.nbt.NBTItem;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class CreationToolInteractionListener implements Listener {

    @EventHandler(priority = EventPriority.MONITOR)
    public void onLeftClick(PlayerInteractEvent event) {
        if (event.getAction() != Action.LEFT_CLICK_BLOCK) return;
        ItemStack hand = event.getPlayer().getItemInHand();
        if (hand.getType() != CreationTool.getTool().getType()) return;
        NBTItem item = new NBTItem(hand);
        if (Boolean.FALSE.equals(item.getBoolean("is-mine-creation-tool"))) return;
        event.setCancelled(true);
        if (!CreationStateManager.getInstance().isRegisteredCreationStatePlayer(event.getPlayer().getUniqueId())) {
            CreationStateManager.getInstance().registerCreationStatePlayer(event.getPlayer().getUniqueId(), event.getClickedBlock().getLocation());
            event.getPlayer().sendMessage("You are now in the mine creation state, position 1 has been selected.");
            return;
        }
        MineCreationBuilder creationBuilder = CreationStateManager.getInstance().getCreationBuilderForStatePlayer(event.getPlayer().getUniqueId());
        if (creationBuilder.getCurrentState().getPosition() < 4) {
            Coordinate coordinate = new Coordinate(event.getClickedBlock().getX(), event.getClickedBlock().getY(), event.getClickedBlock().getZ());
            creationBuilder.doClickSelection(coordinate);
            event.getPlayer().sendMessage("Successfully selected coordinate, you have progress to state: " + creationBuilder.getCurrentState().name());
            return;
        }
        if (creationBuilder.getCurrentState() == MineCreationState.SELECTING_SPAWN_LOCATION) {
            creationBuilder.doSpawnSelection(event.getClickedBlock().getLocation());
            event.getPlayer().sendMessage("You have successfully selected the spawn block for this mine.");
        }
        if (creationBuilder.getCurrentState() == MineCreationState.SELECTING_NAME) {
            event.getPlayer().sendMessage("Please type a name for this mine in chat.");
            return;
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onRightClick(PlayerInteractEvent event) {
        if (!(event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK)) return;
        ItemStack hand = event.getPlayer().getItemInHand();
        if (hand.getType() != CreationTool.getTool().getType()) return;
        NBTItem item = new NBTItem(hand);
        if (Boolean.FALSE.equals(item.getBoolean("is-mine-creation-tool"))) return;
        event.setCancelled(true);
        if (!CreationStateManager.getInstance().isRegisteredCreationStatePlayer(event.getPlayer().getUniqueId())) return;
        MineCreationBuilder creationBuilder = CreationStateManager.getInstance().getCreationBuilderForStatePlayer(event.getPlayer().getUniqueId());
        creationBuilder.undoSelection();
        event.getPlayer().sendMessage("You have successfully undone that selection, and reverted to the previous state: " + creationBuilder.getCurrentState().name());
    }
}
