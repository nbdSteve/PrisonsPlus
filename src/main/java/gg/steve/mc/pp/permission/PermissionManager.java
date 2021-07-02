package gg.steve.mc.pp.permission;

import gg.steve.mc.pp.file.PluginFile;
import gg.steve.mc.pp.manager.AbstractManager;
import gg.steve.mc.pp.manager.ManagerClass;
import gg.steve.mc.pp.permission.exceptions.PermissionNotFoundException;
import gg.steve.mc.pp.utility.LogUtil;
import org.bukkit.configuration.file.YamlConfiguration;

import java.util.HashMap;
import java.util.Map;

@ManagerClass
public class PermissionManager extends AbstractManager {
    private static PermissionManager instance;
    private Map<String, Permission> permissions;

    public PermissionManager() {
        instance = this;
        AbstractManager.addManager(instance);
    }

    @Override
    public void onLoad() {
        YamlConfiguration config = PluginFile.PERMISSIONS.get();
        for (String key : config.getKeys(false)) {
            PermissionType type = PermissionType.getPermissionTypeByIdentifier(key);
            if (type == PermissionType.DEFAULT) {
                LogUtil.warning("Unable to find permission type, " + key + ", setting to default");
            }
            for (String permission : config.getConfigurationSection(key).getKeys(false)) {
                this.registerPermission(permission, config.getConfigurationSection(key).getString(permission), type);
            }
        }
    }

    @Override
    public void onShutdown() {
        if (permissions != null && !permissions.isEmpty()) permissions.clear();
    }

    public boolean registerPermission(String permission, String node, PermissionType type) {
        if (this.permissions == null) this.permissions = new HashMap<>();
        if (this.permissions.containsKey(permission)) return false;
        return this.permissions.put(permission, new Permission(node, type)) != null;
    }

    public boolean unregisterPermission(String permission) {
        if (this.permissions == null || !this.permissions.containsKey(permission)) return false;
        return this.permissions.remove(permission) != null;
    }

    public Permission getPermissionByKey(String permission) throws PermissionNotFoundException {
        if (!this.permissions.containsKey(permission)) throw new PermissionNotFoundException(permission);
        return this.permissions.get(permission);
    }

    public static PermissionManager getInstance() {
        return instance;
    }

    @Override
    protected String getManagerName() {
        return "Permission";
    }
}
