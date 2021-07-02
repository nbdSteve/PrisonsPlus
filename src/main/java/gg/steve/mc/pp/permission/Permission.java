package gg.steve.mc.pp.permission;

import lombok.Data;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@Data
public class Permission {
    private String permission;
    private PermissionType type;

    public Permission(String permission, PermissionType type) {
        this.permission = permission;
        this.type = type;
    }

    public boolean hasPermission(Player player) {
        return player.hasPermission(this.permission);
    }

    public boolean hasPermission(CommandSender sender) {
        return sender.hasPermission(this.permission);
    }
}
