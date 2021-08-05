package gg.steve.mc.pp.addons.tokens.listener;

import gg.steve.mc.pp.addons.tokens.core.TokenPlayerManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerJoinListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        TokenPlayerManager.getInstance().registerTokenPlayer(event.getPlayer().getUniqueId());
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        TokenPlayerManager.getInstance().unregisterTokenPlayer(event.getPlayer().getUniqueId());
    }
}