package gg.steve.mc.pp.addons.mines.core;

import gg.steve.mc.pp.addons.mines.location.MineSpawnLocation;
import lombok.Data;

import java.util.UUID;

@Data
public class Mine {
    private UUID mineId;
    private String name;
    private MineSpawnLocation spawnLocation;
}
