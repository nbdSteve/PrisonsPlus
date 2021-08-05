package gg.steve.mc.pp.addons.mines.location;

import lombok.Data;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

@Data
public abstract class AbstractLocation {
    private Location location;
    private double x, y, z;
    private String world;

    public AbstractLocation(Location location) {
        this.location = location;
        this.x = this.location.getX();
        this.y = this.location.getY();
        this.z = this.location.getZ();
        this.world = this.location.getWorld().getName();
    }

    public String serializeLocation() {
        StringBuilder builder = new StringBuilder();
        builder.append(this.world);
        builder.append(":");
        builder.append(this.x);
        builder.append("-");
        builder.append(this.y);
        builder.append("-");
        builder.append(this.z);
        return builder.toString();
    }

    public static Location deserializeLocation(String mineLocation) {
        String world = mineLocation.split(":")[0];
        String coordinates = mineLocation.split(":")[1];
        double x = Double.parseDouble(coordinates.split("-")[0]);
        double y = Double.parseDouble(coordinates.split("-")[1]);
        double z = Double.parseDouble(coordinates.split("-")[2]);
        return new Location(Bukkit.getWorld(world), x, y, z);
    }

    public void teleportPlayerToLocation(Player player) {
        player.teleport(this.location);
    }

    public boolean isPlayerNearbyLocation(Player player, double distance) {
        return Bukkit.getWorld(this.world).getNearbyEntities(this.location, distance, distance, distance).contains(player);
    }

    public int getTotalPlayersNearbyLocation(double distance) {
        int total = 0;
        for (Entity entity : Bukkit.getWorld(this.world).getNearbyEntities(this.location, distance, distance, distance)) {
            if (entity instanceof Player) total++;
        }
        return total;
    }

    public Block getBlock() {
        return this.location.getBlock();
    }
}
