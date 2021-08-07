package gg.steve.mc.pp.addons.mines.core;

import gg.steve.mc.pp.SPlugin;
import gg.steve.mc.pp.addons.mines.box.BorderBoundingBox;
import gg.steve.mc.pp.addons.mines.box.MiningAreaBoundingBox;
import gg.steve.mc.pp.addons.mines.create.MineCreationBuilder;
import gg.steve.mc.pp.manager.AbstractManager;
import gg.steve.mc.pp.manager.ManagerClass;
import gg.steve.mc.pp.utility.Log;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
@ManagerClass
public final class MineManager extends AbstractManager {
    private static MineManager instance;
    private Map<UUID, Mine> mines;

    public MineManager() {
        instance = this;
        AbstractManager.registerManager(instance);
    }

    @Override
    protected String getManagerName() {
        return "Mine";
    }

    @Override
    public void onLoad() {
        File mineDataFolder = new File(SPlugin.getSPluginInstance().getPlugin().getDataFolder() + File.separator + "mines", "mine-data");
        if (!mineDataFolder.exists()) {
            mineDataFolder.mkdirs();
        }
    }

    @Override
    public void onShutdown() {
        if (this.mines == null || this.mines.isEmpty()) return;
        for (Mine mine : this.mines.values()) {
            mine.save();
        }
        this.mines.clear();
    }

    public static MineManager getInstance() {
        return instance;
    }

    public boolean createAndRegisterMineFromCreationBuilder(MineCreationBuilder creationBuilder) {
        if (creationBuilder.getBorderPosition1() == null
                || creationBuilder.getBorderPosition2() == null
                || creationBuilder.getMiningPosition1() == null
                || creationBuilder.getMiningPosition2() == null
                || creationBuilder.getMineSpawnLocation() == null
                || creationBuilder.getWorldName().equalsIgnoreCase("")
                || creationBuilder.getName().equalsIgnoreCase("")) {
            Log.warning("Tried to create and register a mine but one or more of the selections required were null.");
            return false;
        }
        creationBuilder.setBorderBoundingBox(new BorderBoundingBox(creationBuilder.getWorldName(), creationBuilder.getBorderPosition1(), creationBuilder.getBorderPosition2()));
        creationBuilder.setMiningAreaBoundingBox(new MiningAreaBoundingBox(creationBuilder.getWorldName(), creationBuilder.getMiningPosition1(), creationBuilder.getMiningPosition2()));
        MiningAreaBlockConfiguration miningAreaBlockConfiguration = new MiningAreaBlockConfiguration();
        miningAreaBlockConfiguration.convertContainerToItemsMap(creationBuilder.getBlockConfigurationInventory());
        creationBuilder.getMiningAreaBoundingBox().setMiningBlockConfiguration(miningAreaBlockConfiguration);
        Mine mine = new Mine(creationBuilder.getName(), creationBuilder.getMineSpawnLocation(), creationBuilder.getBorderBoundingBox(), creationBuilder.getMiningAreaBoundingBox());
        if (this.mines == null) this.mines = new HashMap<>();
        if (this.mines.containsKey(mine.getMineId())) {
            Log.severe("Tried to input a mine into the internal map but the UUID is already in use, this is critical.");
            return false;
        }
        return this.mines.put(mine.getMineId(), mine) != null;
    }

//    public boolean registerMine()
}
