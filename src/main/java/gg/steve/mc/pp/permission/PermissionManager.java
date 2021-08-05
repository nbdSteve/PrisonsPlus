package gg.steve.mc.pp.permission;

import gg.steve.mc.pp.file.types.PermissionPluginFile;
import gg.steve.mc.pp.manager.AbstractManager;
import gg.steve.mc.pp.manager.ManagerClass;
import gg.steve.mc.pp.permission.exceptions.PermissionNotFoundException;
import gg.steve.mc.pp.utility.Log;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

@EqualsAndHashCode(callSuper = true)
@Data
@ManagerClass
public final class PermissionManager extends AbstractManager {
    private static PermissionManager instance;
    private Map<String, Permission> permissions;
    private final String defaultPermissionKey;
    private final Permission defaultPermission;

    public PermissionManager() {
        instance = this;
        this.defaultPermissionKey = "default";
        this.defaultPermission = this.registerDefaultPermission(this.defaultPermissionKey, "prisons.default");
        AbstractManager.registerManager(instance);
    }

    @Override
    public void onLoad() {
        this.registerPermission("default", "prisons.default", PermissionType.DEFAULT);
    }

    @Override
    public void onShutdown() {
        if (permissions != null && !permissions.isEmpty()) permissions.clear();
    }

    @Override
    public String getManagerName() {
        return "Permission";
    }

    public void registerPermissionsFromFile(PermissionPluginFile file) {
        for (String key : file.get().getKeys(false)) {
            if (key.equalsIgnoreCase("file-type")) continue;
            PermissionType type = PermissionType.getPermissionTypeByIdentifier(key);
            if (type == PermissionType.DEFAULT)
                Log.warning("Unable to find permission type, " + key + ", setting to default");
            for (String permission : file.get().getConfigurationSection(key).getKeys(false)) {
                this.registerPermission(permission, file.get().getConfigurationSection(key).getString(permission), type);
            }
        }
    }

    public boolean registerPermission(String permission, String node, PermissionType type) {
        if (this.permissions == null) this.permissions = new HashMap<>();
        if (this.permissions.containsKey(permission)) return false;
        return this.permissions.put(permission, new Permission(node, type)) != null;
    }

    private Permission registerDefaultPermission(String permission, String node) {
        if (this.permissions == null) this.permissions = new HashMap<>();
        if (this.permissions.containsKey(permission)) return null;
        return this.permissions.put(permission, new Permission(node, PermissionType.DEFAULT));
    }

    public boolean unregisterPermission(String permission) {
        if (this.permissions == null || !this.permissions.containsKey(permission)) return false;
        return this.permissions.remove(permission) != null;
    }

    public Permission getPermissionByKey(String permission) throws PermissionNotFoundException {
        if (!this.permissions.containsKey(permission)) throw new PermissionNotFoundException(permission);
        return this.permissions.get(permission);
    }

    public Permission getDefaultPermission() {
        return this.defaultPermission;
    }

    public boolean hasPermission(Player player, String permissionKey) throws PermissionNotFoundException {
        return player.hasPermission(this.getPermissionByKey(permissionKey).getPermission());
    }

    public boolean hasPermission(CommandSender executor, String permissionKey) throws PermissionNotFoundException {
        return executor.hasPermission(this.getPermissionByKey(permissionKey).getPermission());
    }

    public static PermissionManager getInstance() {
        return instance;
    }
}
