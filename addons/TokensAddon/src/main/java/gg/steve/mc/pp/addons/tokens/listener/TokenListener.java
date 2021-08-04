package gg.steve.mc.pp.addons.tokens.listener;

import gg.steve.mc.pp.addons.tokens.core.TokenPlayerManager;
import gg.steve.mc.pp.addons.tokens.events.TokenBalanceUpdateEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

public class TokenListener implements Listener {

    @EventHandler(priority = EventPriority.MONITOR)
    public void tokenUpdate(TokenBalanceUpdateEvent event) {
        if (event.isCancelled()) return;
        TokenPlayerManager.getInstance().getTokenPlayer(event.getPlayerId()).getTokenBalances().update(event.getType(), event.getQuery(), event.getChange());
    }
}
