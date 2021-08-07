package gg.steve.mc.pp.addons.mines.box;

import gg.steve.mc.pp.addons.mines.coords.Coordinate;
import gg.steve.mc.pp.addons.mines.core.MiningAreaBlockConfiguration;

public class MiningAreaBoundingBox extends AbstractBoundingBox {
    private MiningAreaBlockConfiguration blockConfiguration;
    private int fillDelay;

    public MiningAreaBoundingBox(String world, Coordinate pos1, Coordinate pos2, int fillTimerDelay) {
        super(world, pos1, pos2);
        this.fillDelay = fillTimerDelay;
        this.generateCoordinateLocations(world, pos1, pos2);
    }

    public void setMiningBlockConfiguration(MiningAreaBlockConfiguration blockConfiguration) {
        this.blockConfiguration = blockConfiguration;
    }

    public MiningAreaBlockConfiguration getBlockConfiguration() {
        return blockConfiguration;
    }

    public void setFillDelay(int fillDelay) {
        this.fillDelay = fillDelay;
    }

    public long getFillDelay() {
        return fillDelay;
    }
}
