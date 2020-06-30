package gg.steve.mc.pp.tokens;

import gg.steve.mc.pp.framework.utils.ColorUtil;
import gg.steve.mc.pp.framework.utils.actionbarapi.ActionBarAPI;
import gg.steve.mc.pp.framework.yml.Files;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;
import java.util.List;

public enum TokenGeneralMessage {
    PAY_PAYER("pay-payer", "{player}", "{amount}", "{token-type}"),
    PAY_RECEIVER("give-piece-receiver", "{player}", "{amount}", "{token-type}"),
    INSUFFICIENT_TOKENS("insufficient-tokens", "{token-type}"),
    BALANCE("balance", "{token-type}", "{amount}"),
    MINE_ADD_TOKENS("mine-add-tokens", "{found}", "{token-type}", "{amount}"),
    TOKENATOR_ADD_TOKENS("tokenator-add-tokens", "{found}", "{token-type}", "{amount}"),
    HELP("help"),
    // admin messages
    BALANCE_UPDATE_GIVER("balance-update-giver", "{player}", "{token-type}", "{amount}"),
    BALANCE_UPDATE_RECEIVER("balance-update-receiver", "{token-type}", "{amount}"),
    BALANCE_QUERY("balance-query", "{player}", "{token-type}", "{amount}");

    private String path;
    private boolean actionBar;
    private List<String> placeholders;

    TokenGeneralMessage(String path, String... placeholders) {
        this.path = path;
        this.placeholders = Arrays.asList(placeholders);
        this.actionBar = Files.TOKEN_MESSAGES.get().getBoolean(this.path + ".action-bar");
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void message(Player receiver, String... replacements) {
        List<String> data = Arrays.asList(replacements);
        if (this.actionBar) {
            for (String line : Files.TOKEN_MESSAGES.get().getStringList(this.path + ".text")) {
                for (int i = 0; i < this.placeholders.size(); i++) {
                    line = line.replace(this.placeholders.get(i), data.get(i));
                }
                ActionBarAPI.sendActionBar(receiver, ColorUtil.colorize(line));
            }
        } else {
            for (String line : Files.TOKEN_MESSAGES.get().getStringList(this.path + ".text")) {
                for (int i = 0; i < this.placeholders.size(); i++) {
                    line = line.replace(this.placeholders.get(i), data.get(i));
                }
                receiver.sendMessage(ColorUtil.colorize(line));
            }
        }
    }

    public void message(CommandSender receiver, String... replacements) {
        List<String> data = Arrays.asList(replacements);
        if (this.actionBar && receiver instanceof Player) {
            for (String line : Files.TOKEN_MESSAGES.get().getStringList(this.path + ".text")) {
                for (int i = 0; i < this.placeholders.size(); i++) {
                    line = line.replace(this.placeholders.get(i), data.get(i));
                }
                ActionBarAPI.sendActionBar((Player) receiver, ColorUtil.colorize(line));
            }
        } else {
            for (String line : Files.TOKEN_MESSAGES.get().getStringList(this.path + ".text")) {
                for (int i = 0; i < this.placeholders.size(); i++) {
                    line = line.replace(this.placeholders.get(i), data.get(i));
                }
                receiver.sendMessage(ColorUtil.colorize(line));
            }
        }
    }

    public void broadcast(JavaPlugin instance, String... replacements) {
        List<String> data = Arrays.asList(replacements);
        if (this.actionBar) {
            for (String line : Files.TOKEN_MESSAGES.get().getStringList(this.path + ".text")) {
                for (int i = 0; i < this.placeholders.size(); i++) {
                    line = line.replace(this.placeholders.get(i), data.get(i));
                }
                ActionBarAPI.sendActionBarToAllPlayers(ColorUtil.colorize(line), instance);
            }
        } else {
            for (String line : Files.TOKEN_MESSAGES.get().getStringList(this.path + ".text")) {
                for (int i = 0; i < this.placeholders.size(); i++) {
                    line = line.replace(this.placeholders.get(i), data.get(i));
                }
                Bukkit.broadcastMessage(ColorUtil.colorize(line));
            }
        }
    }

    public static void doMessage(Player receiver, List<String> lines) {
        for (String line : lines) {
            receiver.sendMessage(ColorUtil.colorize(line));
        }
    }
}
