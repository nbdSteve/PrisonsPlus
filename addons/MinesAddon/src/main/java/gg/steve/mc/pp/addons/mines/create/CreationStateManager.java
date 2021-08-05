package gg.steve.mc.pp.addons.mines.create;

import gg.steve.mc.pp.SPlugin;
import gg.steve.mc.pp.addons.mines.coords.Coordinate;
import gg.steve.mc.pp.manager.AbstractManager;
import gg.steve.mc.pp.manager.ManagerClass;
import gg.steve.mc.pp.utility.ColorUtil;
import gg.steve.mc.pp.utility.Log;
import gg.steve.mc.pp.xseries.messages.ActionBar;
import lombok.Data;
import lombok.EqualsAndHashCode;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
@ManagerClass
public final class CreationStateManager extends AbstractManager {
    private static CreationStateManager instance;
    private Map<UUID, MineCreationBuilder> creationStatePlayers;

    public CreationStateManager() {
        instance = this;
        AbstractManager.registerManager(instance);
    }

    @Override
    protected String getManagerName() {
        return "Creation State";
    }

    @Override
    public void onLoad() {
        this.registerActionBarTask();
    }

    @Override
    public void onShutdown() {
        if (this.creationStatePlayers != null && !this.creationStatePlayers.isEmpty()) this.creationStatePlayers.clear();
    }

    public static CreationStateManager getInstance() {
        return instance;
    }

    public void registerActionBarTask() {
        Bukkit.getScheduler().runTaskTimer(SPlugin.getSPluginInstance().getPlugin(), () -> {
            if (this.creationStatePlayers == null || this.creationStatePlayers.isEmpty()) return;
            for (UUID playerId : this.creationStatePlayers.keySet()) {
                Player player = Bukkit.getPlayer(playerId);
                if (player == null || !player.isOnline()) continue;
                ActionBar.sendActionBar(player, ColorUtil.colorize("&c&lYOU ARE IN MINE CREATION MODE!"));
            }
        }, 0L, 20L);
    }

    public boolean registerCreationStatePlayer(UUID playerId, Location borderPosition1) {
        if (this.creationStatePlayers == null) this.creationStatePlayers = new HashMap<>();
        Coordinate coordinate = new Coordinate(borderPosition1.getX(), borderPosition1.getY(), borderPosition1.getZ());
        return this.creationStatePlayers.put(playerId, new MineCreationBuilder(playerId, coordinate)) != null;
    }

    public boolean unregisterCreationStatePlayer(UUID playerId) {
        if (this.creationStatePlayers == null || this.creationStatePlayers.isEmpty()) return false;
        return this.creationStatePlayers.remove(playerId) != null;
    }

    public MineCreationBuilder getCreationBuilderForStatePlayer(UUID playerId) {
        if (!this.isRegisteredCreationStatePlayer(playerId)) return null;
        return this.creationStatePlayers.get(playerId);
    }

    public boolean isRegisteredCreationStatePlayer(UUID playerId) {
        if (this.creationStatePlayers == null || this.creationStatePlayers.isEmpty()) return false;
        return this.creationStatePlayers.containsKey(playerId);
    }
}
