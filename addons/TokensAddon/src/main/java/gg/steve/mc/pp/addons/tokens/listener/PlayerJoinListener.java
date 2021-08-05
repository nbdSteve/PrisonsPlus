package gg.steve.mc.pp.addons.tokens.listener;

import gg.steve.mc.pp.addons.tokens.core.TokenPlayerManager;
import gg.steve.mc.pp.addons.tokens.core.TokenType;
import gg.steve.mc.pp.utility.Log;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerJoinListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        TokenPlayerManager.getInstance().registerTokenPlayer(event.getPlayer().getUniqueId());
        Log.info("Mine tokens for player " + event.getPlayer().getName() + ": " +
                TokenPlayerManager.getInstance().getTokenBalanceForPlayer(event.getPlayer().getUniqueId(), TokenType.MINE));
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        TokenPlayerManager.getInstance().unregisterTokenPlayer(event.getPlayer().getUniqueId());
    }

    @EventHandler
    public void mine(BlockBreakEvent event) {
        TokenPlayerManager.getInstance().give(event.getPlayer().getUniqueId(), TokenType.MINE, 1);
        Log.info("Mine tokens for player " + event.getPlayer().getName() + ": " +
                TokenPlayerManager.getInstance().getTokenBalanceForPlayer(event.getPlayer().getUniqueId(), TokenType.MINE));
    }
}
