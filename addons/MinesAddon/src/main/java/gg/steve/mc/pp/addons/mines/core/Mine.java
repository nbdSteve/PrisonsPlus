package gg.steve.mc.pp.addons.mines.core;

import gg.steve.mc.pp.SPlugin;
import gg.steve.mc.pp.addons.mines.box.MiningAreaBoundingBox;
import gg.steve.mc.pp.addons.mines.box.BorderBoundingBox;
import gg.steve.mc.pp.addons.mines.location.MineSpawnLocation;
import lombok.Data;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitTask;

import java.util.UUID;

@Data
public class Mine {
    private UUID mineId;
    private String name;
    private MineSpawnLocation spawnLocation;
    private BorderBoundingBox borderBoundingBox;
    private MiningAreaBoundingBox miningArea;

    public Mine(String name, MineSpawnLocation spawnLocation, BorderBoundingBox borderBoundingBox, MiningAreaBoundingBox miningArea) {
        this.mineId = UUID.randomUUID();
        this.name = name;
        this.spawnLocation = spawnLocation;
        this.borderBoundingBox = borderBoundingBox;
        this.miningArea = miningArea;
        this.registerMiningAreaFillTask();
    }

    public Mine(UUID mineId, String name, MineSpawnLocation spawnLocation, BorderBoundingBox borderBoundingBox, MiningAreaBoundingBox miningArea) {
        this.mineId = mineId;
        this.name = name;
        this.spawnLocation = spawnLocation;
        this.borderBoundingBox = borderBoundingBox;
        this.miningArea = miningArea;
        this.registerMiningAreaFillTask();
    }

    private BukkitTask registerMiningAreaFillTask() {
        return Bukkit.getScheduler().runTaskTimer(SPlugin.getSPluginInstance().getPlugin(), () -> {
            this.miningArea.getBlockConfiguration().fillMiningArea(this.miningArea);
        }, 0L, 100L);
    }
}
