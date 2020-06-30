package gg.steve.mc.pp.tokens.listener;

import gg.steve.mc.pp.PrisonsPlus;
import gg.steve.mc.pp.tokens.TokenGeneralMessage;
import gg.steve.mc.pp.tokens.event.AddMethodType;
import gg.steve.mc.pp.tokens.event.PreTokenAddEvent;
import gg.steve.mc.pp.tokens.event.TokenAddEvent;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

public class TokenListener implements Listener {

    @EventHandler(priority = EventPriority.MONITOR)
    public void preAdd(PreTokenAddEvent event) {
        if (event.isCancelled()) return;
        Bukkit.getPluginManager().callEvent(new TokenAddEvent(event.getPlayer(), event.getType(), event.getAmount(), event.getAddMethod()));
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void add(TokenAddEvent event) {
        if (event.isCancelled()) return;
        double boost = 1;
//        if (BoostersApi.isBoosterActive(event.getPlayer().getPlayer().getPlayer(), BoosterType.TOKEN)) {
//            boost = BoostersApi.getBoostAmount(event.getPlayer().getPlayer().getPlayer(), BoosterType.TOKEN);
//        }
        event.getPlayer().addTokens(event.getType(), (int) Math.round(event.getAmount() * boost));
        if (event.getAddMethod().equals(AddMethodType.MINE)) {
            TokenGeneralMessage.MINE_ADD_TOKENS.message(event.getPlayer().getPlayer().getPlayer(),
                    PrisonsPlus.format((int) Math.round(event.getAmount() * boost)),
                    event.getType().name(),
                    PrisonsPlus.format(event.getPlayer().getTokens(event.getType())));
        } else if (event.getAddMethod().equals(AddMethodType.TOKENATOR)) {
            TokenGeneralMessage.TOKENATOR_ADD_TOKENS.message(event.getPlayer().getPlayer().getPlayer(),
                    PrisonsPlus.format((int) Math.round(event.getAmount() * boost)),
                    event.getType().name(),
                    PrisonsPlus.format(event.getPlayer().getTokens(event.getType())));
        }
    }
}
