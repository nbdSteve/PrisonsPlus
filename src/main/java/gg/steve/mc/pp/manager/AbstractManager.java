package gg.steve.mc.pp.manager;

import lombok.Data;

import java.util.*;

@Data
public abstract class AbstractManager implements Loadable {
    private static Map<String, AbstractManager> managers;

    private static void initialiseManagerList() {
        if (managers == null) managers = new LinkedHashMap<>();
    }

    public static void addManager(AbstractManager manager) {
        initialiseManagerList();
        if (managers.containsKey(manager.getManagerName())) return;
        managers.put(manager.getManagerName(), manager);
    }

    public static void loadManagers() {
        if (managers == null || managers.isEmpty()) return;
        for (AbstractManager manager : managers.values()) {
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
        initialiseManagerList();
        return managers.values();
    }

    protected abstract String getManagerName();
}
