package gg.steve.mc.pp.addons.tokens.events;

import gg.steve.mc.pp.addons.tokens.core.PlayerTokenBalances;
import gg.steve.mc.pp.addons.tokens.core.TokenType;
import lombok.Data;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import java.util.UUID;

@Data
public class TokenBalanceUpdateEvent extends Event implements Cancellable {
    private static final HandlerList handlers = new HandlerList();

    private UUID playerId;
    private TokenType type;
    private int change;
    private PlayerTokenBalances.Query query;
    private TokenBalanceUpdateMethod updateMethod;
    private boolean cancelled;

    public TokenBalanceUpdateEvent(UUID playerId, TokenType type, int change, PlayerTokenBalances.Query query, TokenBalanceUpdateMethod updateMethod) {
        this.playerId = playerId;
        this.type = type;
        this.change = change;
        this.query = query;
        this.updateMethod = updateMethod;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    @Override
    public boolean isCancelled() {
        return this.cancelled;
    }

    @Override
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }
}
