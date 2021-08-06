package gg.steve.mc.pp.addons.mines.box;

import gg.steve.mc.pp.addons.mines.coords.Coordinate;
import gg.steve.mc.pp.addons.mines.core.MiningAreaBlockConfiguration;
import lombok.Data;

public class MiningAreaBoundingBox extends AbstractBoundingBox {
    private MiningAreaBlockConfiguration blockConfiguration;

    public MiningAreaBoundingBox(String world, Coordinate pos1, Coordinate pos2) {
        super(world, pos1, pos2);
        this.generateCoordinateLocations(world, pos1, pos2);
    }

    public void setMiningBlockConfiguration(MiningAreaBlockConfiguration blockConfiguration) {
        this.blockConfiguration = blockConfiguration;
    }

    public MiningAreaBlockConfiguration getBlockConfiguration() {
        return blockConfiguration;
    }
}
