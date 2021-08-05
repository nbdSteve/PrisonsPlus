package gg.steve.mc.pp.addons.mines.coords;

import lombok.Data;

@Data
public class Coordinate {
    private double x, y, z;

    public Coordinate(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
}
