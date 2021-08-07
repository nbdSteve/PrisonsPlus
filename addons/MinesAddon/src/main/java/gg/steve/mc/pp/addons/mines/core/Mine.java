package gg.steve.mc.pp.addons.mines.core;

import gg.steve.mc.pp.SPlugin;
import gg.steve.mc.pp.addons.mines.box.BorderBoundingBox;
import gg.steve.mc.pp.addons.mines.box.MiningAreaBoundingBox;
import gg.steve.mc.pp.addons.mines.coords.Coordinate;
import gg.steve.mc.pp.addons.mines.file.MinePluginFile;
import gg.steve.mc.pp.addons.mines.location.MineSpawnLocation;
import gg.steve.mc.pp.file.FileManager;
import gg.steve.mc.pp.utility.ColorUtil;
import gg.steve.mc.pp.utility.Log;
import gg.steve.mc.pp.xseries.XItemStack;
import lombok.Data;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitTask;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
public class Mine {
    private UUID mineId;
    private String name;
    private String displayName;
    private MineSpawnLocation spawnLocation;
    private BorderBoundingBox borderBoundingBox;
    private MiningAreaBoundingBox miningArea;
    private BukkitTask miningAreaFillTask;
    private boolean enabled;
    private boolean permissionBasedWarp;
    private boolean filling;

    public Mine(String name, String displayName, MineSpawnLocation spawnLocation, BorderBoundingBox borderBoundingBox, MiningAreaBoundingBox miningArea) {
        this.mineId = UUID.randomUUID();
        this.name = name;
        this.displayName = displayName;
        this.spawnLocation = spawnLocation;
        this.borderBoundingBox = borderBoundingBox;
        this.miningArea = miningArea;
        this.miningAreaFillTask = this.registerMiningAreaFillTask();
    }

    public Mine(MinePluginFile minePluginFile) {
        YamlConfiguration config = minePluginFile.get();
        this.mineId = UUID.fromString(config.getString("mine-id"));
        this.name = config.getString("name");
        this.displayName = config.getString("display-name");
        String worldName = config.getString("world-name");
        Coordinate spawnCoordinate = new Coordinate(config.getInt("mine-spawn-location.x"),
                config.getInt("mine-spawn-location.y"),
                config.getInt("mine-spawn-location.z"));
        this.spawnLocation = new MineSpawnLocation(new Location(Bukkit.getWorld(worldName), spawnCoordinate.getX(), spawnCoordinate.getY(), spawnCoordinate.getZ()));
        Coordinate borderBoundingBoxPosition1 = new Coordinate(config.getInt("border-bounding-box.position-1.x"),
                config.getInt("border-bounding-box.position-1.y"),
                config.getInt("border-bounding-box.position-1.z"));
        Coordinate borderBoundingBoxPosition2 = new Coordinate(config.getInt("border-bounding-box.position-2.x"),
                config.getInt("border-bounding-box.position-2.y"),
                config.getInt("border-bounding-box.position-2.z"));
        this.borderBoundingBox = new BorderBoundingBox(worldName, borderBoundingBoxPosition1, borderBoundingBoxPosition2);
        Coordinate miningAreaPosition1 = new Coordinate(config.getInt("mining-area-bounding-box.position-1.x"),
                config.getInt("mining-area-bounding-box.position-1.y"),
                config.getInt("mining-area-bounding-box.position-1.z"));
        Coordinate miningAreaPosition2 = new Coordinate(config.getInt("mining-area-bounding-box.position-2.x"),
                config.getInt("mining-area-bounding-box.position-2.y"),
                config.getInt("mining-area-bounding-box.position-2.z"));
        this.miningArea = new MiningAreaBoundingBox(worldName, miningAreaPosition1, miningAreaPosition2, config.getInt("mining-area-fill-delay"));
        List<ItemStack> items = new ArrayList<>();
        for (String key : config.getConfigurationSection("block-configuration").getKeys(false)) {
            items.add(XItemStack.deserialize(config.getConfigurationSection("block-configuration." + key)));
        }
        MiningAreaBlockConfiguration miningAreaBlockConfiguration = new MiningAreaBlockConfiguration();
        miningAreaBlockConfiguration.setMaterialAndSpawnRateMap(items);
        this.miningArea.setMiningBlockConfiguration(miningAreaBlockConfiguration);
        this.miningAreaFillTask = this.registerMiningAreaFillTask();
    }

    private BukkitTask registerMiningAreaFillTask() {
        return Bukkit.getScheduler().runTaskTimer(SPlugin.getSPluginInstance().getPlugin(),
                () -> this.miningArea.getBlockConfiguration().fillMiningArea(this.miningArea), 0L, this.miningArea.getFillDelay());
    }

    public boolean save() {
        if (this.miningAreaFillTask != null) {
            this.miningAreaFillTask.cancel();
            this.miningAreaFillTask = null;
        }
        MinePluginFile file = new MinePluginFile();
        File raw = new File(SPlugin.getSPluginInstance().getPlugin().getDataFolder() + File.separator + "mines" + File.separator + "mine-data", this.name + ".yml");
        if (raw.exists()) {
            file = (MinePluginFile) FileManager.getInstance().getFiles().get("mines-mine-data-" + this.name);
        } else {
            try {
                raw.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            file.loadFromFile("mines-mine-data-" + this.name, raw);
        }
        YamlConfiguration config = file.get();
        config.set("file-type", String.valueOf("mine"));
        config.set("mine-id", String.valueOf(this.mineId));
        config.set("name", this.name);
        config.set("display-name", this.displayName);
        config.set("mining-area-fill-delay", this.miningArea.getFillDelay());
        config.set("world-name", this.miningArea.getWorld());
        config.set("border-bounding-box.position-1.x", this.borderBoundingBox.getPos1().getX());
        config.set("border-bounding-box.position-1.y", this.borderBoundingBox.getPos1().getY());
        config.set("border-bounding-box.position-1.z", this.borderBoundingBox.getPos1().getZ());
        config.set("border-bounding-box.position-2.x", this.borderBoundingBox.getPos2().getX());
        config.set("border-bounding-box.position-2.y", this.borderBoundingBox.getPos2().getY());
        config.set("border-bounding-box.position-2.z", this.borderBoundingBox.getPos2().getZ());
        config.set("mining-area-bounding-box.position-1.x", this.miningArea.getPos1().getX());
        config.set("mining-area-bounding-box.position-1.y", this.miningArea.getPos1().getY());
        config.set("mining-area-bounding-box.position-1.z", this.miningArea.getPos1().getZ());
        config.set("mining-area-bounding-box.position-2.x", this.miningArea.getPos2().getX());
        config.set("mining-area-bounding-box.position-2.y", this.miningArea.getPos2().getY());
        config.set("mining-area-bounding-box.position-2.z", this.miningArea.getPos2().getZ());
        config.set("mine-spawn-location.x", this.spawnLocation.getCoordinate().getX());
        config.set("mine-spawn-location.y", this.spawnLocation.getCoordinate().getY());
        config.set("mine-spawn-location.z", this.spawnLocation.getCoordinate().getZ());
        int index = 1;
        for (ItemStack itemStack : this.miningArea.getBlockConfiguration().getMaterialAndSpawnRate().keySet()) {
            config.createSection("block-configuration." + index);
            ConfigurationSection section = config.getConfigurationSection("block-configuration." + index);
            XItemStack.serialize(itemStack, section);
            index++;
        }
        file.save();
        return true;
    }

    public String getDisplayName() {
        return ColorUtil.colorize(displayName);
    }
}
