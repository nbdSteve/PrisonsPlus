package gg.steve.mc.pp.addons.mines.listener;

import gg.steve.mc.pp.addons.mines.coords.Coordinate;
import gg.steve.mc.pp.addons.mines.core.MineManager;
import gg.steve.mc.pp.addons.mines.create.CreationStateManager;
import gg.steve.mc.pp.addons.mines.create.MineCreationBuilder;
import gg.steve.mc.pp.addons.mines.create.MineCreationState;
import gg.steve.mc.pp.addons.mines.tool.CreationTool;
import gg.steve.mc.pp.message.MessageManager;
import gg.steve.mc.pp.nbt.NBTItem;
import gg.steve.mc.pp.utility.ColorUtil;
import gg.steve.mc.pp.xseries.XBlock;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.InventoryHolder;
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
            MessageManager.getInstance().sendMessage("enter-mine-creation-state", event.getPlayer());
        }
        MineCreationBuilder creationBuilder = CreationStateManager.getInstance().getCreationBuilderForStatePlayer(event.getPlayer().getUniqueId());
        if (creationBuilder.getWorldName() == null || creationBuilder.getWorldName().equalsIgnoreCase("")) {
            creationBuilder.setWorldName(event.getClickedBlock().getWorld().getName());
        }
        if (creationBuilder.getCurrentState().getPosition() < 4) {
            Coordinate coordinate = new Coordinate(event.getClickedBlock().getX(), event.getClickedBlock().getY(), event.getClickedBlock().getZ());
            creationBuilder.doClickSelection(coordinate);
        } else if (creationBuilder.getCurrentState() == MineCreationState.SELECTING_MINING_AREA_BLOCKS_CHEST) {
            if (!XBlock.isContainer(event.getClickedBlock()) || !(event.getClickedBlock().getState() instanceof InventoryHolder)) {
                event.getPlayer().sendMessage(ChatColor.RED + "Please select a chest or trapped chest.");
                return;
            }
            creationBuilder.doBlockConfigurationSelection(((InventoryHolder) event.getClickedBlock().getState()).getInventory());
        } else if (creationBuilder.getCurrentState() == MineCreationState.SELECTING_SPAWN_LOCATION) {
            creationBuilder.doSpawnSelection(event.getClickedBlock().getLocation());
        }
        MessageManager.getInstance().sendMessage("progress-mine-creation-state", event.getPlayer(), creationBuilder.getCurrentState().getNiceName());
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onRightClick(PlayerInteractEvent event) {
        if (!(event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK)) return;
        ItemStack hand = event.getPlayer().getItemInHand();
        if (hand.getType() != CreationTool.getTool().getType()) return;
        NBTItem item = new NBTItem(hand);
        if (Boolean.FALSE.equals(item.getBoolean("is-mine-creation-tool"))) return;
        event.setCancelled(true);
        if (!CreationStateManager.getInstance().isRegisteredCreationStatePlayer(event.getPlayer().getUniqueId()))
            return;
        MineCreationBuilder creationBuilder = CreationStateManager.getInstance().getCreationBuilderForStatePlayer(event.getPlayer().getUniqueId());
        creationBuilder.undoSelection();
        MessageManager.getInstance().sendMessage("revert-selection", event.getPlayer(), creationBuilder.getCurrentState().getNiceName());
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onExit(AsyncPlayerChatEvent event) {
        if (!CreationStateManager.getInstance().isRegisteredCreationStatePlayer(event.getPlayer().getUniqueId()) || !event.getMessage().contains("exit"))
            return;
        event.setCancelled(true);
        CreationStateManager.getInstance().unregisterCreationStatePlayer(event.getPlayer().getUniqueId());
        MessageManager.getInstance().sendMessage("exit-mine-creation-state", event.getPlayer());
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onNameSelect(AsyncPlayerChatEvent event) {
        if (!CreationStateManager.getInstance().isRegisteredCreationStatePlayer(event.getPlayer().getUniqueId()))
            return;
        MineCreationBuilder creationBuilder = CreationStateManager.getInstance().getCreationBuilderForStatePlayer(event.getPlayer().getUniqueId());
        if (creationBuilder.getCurrentState() == MineCreationState.SELECTING_NAME) {
            creationBuilder.doNameSelection(ColorUtil.strip(event.getMessage()));
        } else if (creationBuilder.getCurrentState() == MineCreationState.SELECTING_MINING_AREA_FILL_DELAY) {
            int delay;
            try {
                delay = Integer.parseInt(event.getMessage());
                if (delay <= 0) throw new NumberFormatException();
            } catch (NumberFormatException e) {
                MessageManager.getInstance().sendMessage("invalid-amount", event.getPlayer());
                return;
            }
            creationBuilder.doTimerDelaySelection(delay);
        } else if (creationBuilder.getCurrentState() == MineCreationState.SELECTING_DISPLAY_NAME) {
            creationBuilder.doDisplayNameSelection(event.getMessage());
        } else {
            return;
        }
        event.setCancelled(true);
        MessageManager.getInstance().sendMessage("progress-mine-creation-state", event.getPlayer(), creationBuilder.getCurrentState().getNiceName());
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onCreate(AsyncPlayerChatEvent event) {
        if (!CreationStateManager.getInstance().isRegisteredCreationStatePlayer(event.getPlayer().getUniqueId()) || !event.getMessage().contains("confirm"))
            return;
        MineCreationBuilder creationBuilder = CreationStateManager.getInstance().getCreationBuilderForStatePlayer(event.getPlayer().getUniqueId());
        if (creationBuilder.getCurrentState() != MineCreationState.SELECTION_COMPLETE) return;
        event.setCancelled(true);
        MineManager.getInstance().createAndRegisterMineFromCreationBuilder(creationBuilder);
        CreationStateManager.getInstance().unregisterCreationStatePlayer(event.getPlayer().getUniqueId());
        event.getPlayer().sendMessage("You have just created that mine.");
    }
}
