package gg.steve.mc.pp.addons.tokens.events;

import gg.steve.mc.pp.addons.tokens.core.PlayerTokenBalances;
import gg.steve.mc.pp.addons.tokens.core.TokenPlayerManager;
import gg.steve.mc.pp.addons.tokens.core.TokenType;
import lombok.Data;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import java.util.UUID;

@Data
public class PlayerTokenPayEvent extends Event implements Cancellable {
    private static final HandlerList handlers = new HandlerList();

    private UUID fromPlayerId, toPlayerId;
    private TokenType tokenType;
    private int amount, fromOpeningBalance, fromClosingBalance, toOpeningBalance, toClosingBalance;
    private PlayerTokenBalances.Query query;
    private TokenBalanceUpdateMethod updateMethod;
    private boolean cancelled, successfullTransfer;

    public PlayerTokenPayEvent(UUID fromPlayerId, UUID toPlayerId, TokenType tokenType, int amount) {
        this.fromPlayerId = fromPlayerId;
        this.toPlayerId = toPlayerId;
        this.tokenType = tokenType;
        this.amount = amount;
        this.updateMethod = TokenBalanceUpdateMethod.PAYMENT;
        this.fromOpeningBalance = TokenPlayerManager.getInstance().getTokenBalanceForPlayer(fromPlayerId, tokenType);
        this.toOpeningBalance = TokenPlayerManager.getInstance().getTokenBalanceForPlayer(toPlayerId, tokenType);
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
