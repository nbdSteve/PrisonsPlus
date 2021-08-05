package gg.steve.mc.pp.addons.tokens.cmd.subs;

import gg.steve.mc.pp.cmd.AbstractCommand;
import gg.steve.mc.pp.cmd.AbstractSubCommand;
import gg.steve.mc.pp.cmd.SubCommandClass;
import gg.steve.mc.pp.gui.GuiManager;
import gg.steve.mc.pp.gui.exception.AbstractGuiNotFoundException;
import gg.steve.mc.pp.utility.Log;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@SubCommandClass
public class TokensShopGuiSubCommand extends AbstractSubCommand {

    public TokensShopGuiSubCommand(AbstractCommand parent) {
        super(parent, "shop", "token-shop", 1, 2);
        this.registerAlias("s");
        this.registerAlias("gui");
    }

    @Override
    public void run(CommandSender executor, String[] arguments) {
        if (!(executor instanceof Player)) {
            Log.info("Only players are allowed to open to the token shop gui, go in-game and try /token shop.");
            return;
        }
        try {
            GuiManager.getInstance().openGui((Player) executor, "token-shop-gui");
        } catch (AbstractGuiNotFoundException e) {
            e.printStackTrace();
        }
    }
}
