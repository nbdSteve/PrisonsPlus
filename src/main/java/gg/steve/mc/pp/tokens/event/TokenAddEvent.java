package gg.steve.mc.pp.tokens.event;

import gg.steve.mc.pp.tokens.player.TokenPlayer;
import gg.steve.mc.pp.tokens.player.TokenType;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class TokenAddEvent extends Event implements Cancellable {
    private static final HandlerList handlers = new HandlerList();

    private TokenPlayer player;
    private TokenType type;
    private int amount;
    private AddMethodType addMethod;
    private boolean cancel;

    public TokenAddEvent(TokenPlayer player, TokenType type, int amount, AddMethodType addMethod) {
        this.player = player;
        this.type = type;
        this.amount = amount;
        this.addMethod = addMethod;
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
        return cancel;
    }

    @Override
    public void setCancelled(boolean cancel) {
        this.cancel = cancel;
    }

    public TokenPlayer getPlayer() {
        return player;
    }

    public int getAmount() {
        return amount;
    }

    public AddMethodType getAddMethod() {
        return addMethod;
    }

    public TokenType getType() {
        return type;
    }
}
