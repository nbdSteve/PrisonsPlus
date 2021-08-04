package gg.steve.mc.pp.manager;

import gg.steve.mc.pp.utility.Log;
import lombok.Data;

import java.util.*;

@Data
public abstract class AbstractManager implements Loadable {
    private static Map<String, AbstractManager> managers;

    private static void initialiseManagerMap() {
        if (managers == null) managers = new LinkedHashMap<>();
    }

    public static void registerManager(AbstractManager manager) {
        initialiseManagerMap();
        if (managers.containsKey(manager.getManagerName())) return;
        managers.put(manager.getManagerName(), manager);
    }

    public static void loadManagers() {
        if (managers == null || managers.isEmpty()) return;
        for (AbstractManager manager : managers.values()) {
            Log.warning(manager.getManagerName());
            manager.onLoad();
        }
    }

    public static void shutdownManagers() {
        if (managers == null || managers.isEmpty()) return;
        List<AbstractManager> reverse = new LinkedList<>(managers.values());
        for (int i = managers.size() - 1; i >= 0; i--) {
            reverse.get(i).onShutdown();
        }
        managers.clear();
    }

    public static Collection<AbstractManager> getActiveManagers() {
        initialiseManagerMap();
        return managers.values();
    }

    protected abstract String getManagerName();
}
