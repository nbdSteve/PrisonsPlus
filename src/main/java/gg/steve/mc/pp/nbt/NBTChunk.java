package gg.steve.mc.pp.nbt;

import gg.steve.mc.pp.nbt.utils.MinecraftVersion;
import gg.steve.mc.pp.nbt.utils.annotations.AvailableSince;
import org.bukkit.Chunk;

public class NBTChunk {

    private final Chunk chunk;

    public NBTChunk(Chunk chunk) {
        this.chunk = chunk;
    }

    /**
     * Gets the NBTCompound used by spigots PersistentDataAPI. This method is only
     * available for 1.16.4+!
     *
     * @return NBTCompound containing the data of the PersistentDataAPI
     */
    @AvailableSince(version = MinecraftVersion.MC1_16_R3)
    public NBTCompound getPersistentDataContainer() {
        return new NBTPersistentDataContainer(chunk.getPersistentDataContainer());
    }

}
