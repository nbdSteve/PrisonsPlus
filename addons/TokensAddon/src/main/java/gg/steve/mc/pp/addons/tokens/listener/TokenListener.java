package gg.steve.mc.pp.addons.tokens.listener;

import gg.steve.mc.pp.addons.tokens.core.PlayerTokenBalances;
import gg.steve.mc.pp.addons.tokens.core.TokenPlayerManager;
import gg.steve.mc.pp.addons.tokens.events.PlayerTokenPayEvent;
import gg.steve.mc.pp.addons.tokens.events.TokenBalanceUpdateEvent;
import gg.steve.mc.pp.addons.tokens.events.TokenBalanceUpdateMethod;
import gg.steve.mc.pp.message.MessageManager;
import gg.steve.mc.pp.utility.ColorUtil;
import gg.steve.mc.pp.utility.NumberFormatUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

public class TokenListener implements Listener {

    @EventHandler(priority = EventPriority.MONITOR)
    public void tokenUpdate(TokenBalanceUpdateEvent event) {
        if (event.isCancelled()) return;
        switch (event.getQuery()) {
            case INCREMENT:
                event.setClosingBalance(TokenPlayerManager.getInstance().give(event.getPlayerId(), event.getTokenType(), event.getChange()));
                break;
            case DECREMENT:
                event.setClosingBalance(TokenPlayerManager.getInstance().remove(event.getPlayerId(), event.getTokenType(), event.getChange()));
                break;
            case SET:
                event.setClosingBalance(TokenPlayerManager.getInstance().set(event.getPlayerId(), event.getTokenType(), event.getChange()));
                break;
            case RESET:
                event.setClosingBalance(TokenPlayerManager.getInstance().reset(event.getPlayerId(), event.getTokenType()));
                break;
            case RESET_ALL:
                TokenPlayerManager.getInstance().resetAll(event.getPlayerId());
                break;
        }
        Player player = Bukkit.getPlayer(event.getPlayerId());
        if (player == null || !player.isOnline()) return;
        if (event.isSendBalanceUpdateMessage() && !event.isCustomBalanceUpdateMessage()) {
            MessageManager.getInstance().sendMessage("receive-token-update-balance", Bukkit.getPlayer(event.getPlayerId()),
                    event.getTokenType().getNiceName(),
                    NumberFormatUtil.format(event.getClosingBalance()),
                    event.getUpdateMethod().getNiceName());
        } else if (event.isSendBalanceUpdateMessage() && event.isCustomBalanceUpdateMessage()) {
            Bukkit.getPlayer(event.getPlayerId()).sendMessage(ColorUtil.colorize(event.getCustomBalanceUpdateMessage()));
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void playerTokenPay(PlayerTokenPayEvent event) {
        if (event.isCancelled()) return;
        if (TokenPlayerManager.getInstance().getTokenBalanceForPlayer(event.getFromPlayerId(), event.getTokenType()) < event.getAmount())
            return;
        TokenBalanceUpdateEvent fromRemoveEvent = new TokenBalanceUpdateEvent(event.getFromPlayerId(), event.getTokenType(), event.getAmount(), PlayerTokenBalances.Query.DECREMENT, TokenBalanceUpdateMethod.PAYMENT, event.isSendEventMessage());
        TokenBalanceUpdateEvent toGiveEvent = new TokenBalanceUpdateEvent(event.getToPlayerId(), event.getTokenType(), event.getAmount(), PlayerTokenBalances.Query.INCREMENT, TokenBalanceUpdateMethod.PAYMENT, event.isSendEventMessage());
        Bukkit.getPluginManager().callEvent(fromRemoveEvent);
        Bukkit.getPluginManager().callEvent(toGiveEvent);
        event.setSuccessfullTransfer(true);
    }
}
