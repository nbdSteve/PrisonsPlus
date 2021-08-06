package gg.steve.mc.pp.addons.mines.box;

import gg.steve.mc.pp.SPlugin;
import gg.steve.mc.pp.addons.mines.coords.Coordinate;
import lombok.Data;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.LinkedHashMap;
import java.util.Map;

@Data
public abstract class AbstractBoundingBox {
    String world;
    private Coordinate pos1, pos2;
    Map<Coordinate, Location> coordinates;
    private boolean loaded;

    public AbstractBoundingBox(String world, Coordinate pos1, Coordinate pos2) {
        this.world = world;
        this.pos1 = pos1;
        this.pos2 = pos2;
        this.coordinates = new LinkedHashMap<>();
    }

    protected void generateCoordinateLocations(String world, Coordinate pos1, Coordinate pos2) {
        Bukkit.getScheduler().runTaskAsynchronously(SPlugin.getSPluginInstance().getPlugin(), () -> {
            World world1 = Bukkit.getWorld(world);
            int maxX = (int) Math.ceil(Math.max(pos1.getX(), pos2.getX()));
            int maxY = (int) Math.ceil(Math.max(pos1.getY(), pos2.getY()));
            int maxZ = (int) Math.ceil(Math.max(pos1.getZ(), pos2.getZ()));
            int minX = (int) Math.ceil(Math.min(pos1.getX(), pos2.getX()));
            int minY = (int) Math.ceil(Math.min(pos1.getY(), pos2.getY()));
            int minZ = (int) Math.ceil(Math.min(pos1.getZ(), pos2.getZ()));
            for (int x = minX; x <= maxX; x++) {
                for (int y = minY; y <= maxY; y++) {
                    for (int z = minZ; z <= maxZ; z++) {
                        this.coordinates.put(new Coordinate(x, y, z), new Location(world1, x, y, z));
                    }
                }
            }
            this.loaded = true;
        });
    }

    public boolean isWithinBoundingBox(Player player) {
        int maxX = (int) Math.ceil(Math.max(pos1.getX(), pos2.getX()));
        int maxY = (int) Math.ceil(Math.max(pos1.getY(), pos2.getY()));
        int maxZ = (int) Math.ceil(Math.max(pos1.getZ(), pos2.getZ()));
        int minX = (int) Math.ceil(Math.min(pos1.getX(), pos2.getX()));
        int minY = (int) Math.ceil(Math.min(pos1.getY(), pos2.getY()));
        int minZ = (int) Math.ceil(Math.min(pos1.getZ(), pos2.getZ()));
        Location location = player.getLocation();
        return maxX >= location.getBlockX() && location.getBlockX() >= minX
                && maxY >= location.getBlockX() && location.getBlockX() >= minY
                && maxZ >= location.getBlockX() && location.getBlockX() >= minZ;
    }
}
