/**********************************
 Copyright (c) All Rights Reserved
 *********************************/

package me.aj4real.dataplus.api;

import io.netty.buffer.ByteBuf;
import me.aj4real.dataplus.DataPlus;
import me.aj4real.dataplus.api.nbt.NBTCompoundTag;
import org.bukkit.*;
import org.bukkit.block.BlockState;
import org.bukkit.block.data.BlockData;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.function.Predicate;

public interface ChunkDataPacketEditor {
    Version compatibility = Version.of(1, 17, 0);
    InstantiationException err = new InstantiationException(
            ChunkDataPacketEditor.class.getCanonicalName() + ": This feature requires the server to be on " + compatibility + " or higher.");
    @SuppressWarnings("unused")
    static ChunkDataPacketEditor newInstance(Chunk chunk) throws InvocationTargetException, InstantiationException, IllegalAccessException {
        return DataPlus.nms.getChunkDataPacketEditor(chunk);
    }
    @SuppressWarnings("unused")
    static ChunkDataPacketEditor newInstance(World world, Object packet) throws InvocationTargetException, InstantiationException, IllegalAccessException {
        return DataPlus.nms.getChunkDataPacketEditor(world, packet);
    }
    static void include() {}
    Object build() throws IllegalAccessException;
    int getChunkX();
    int getChunkZ();
    World getWorld();
    Chunk getChunk();

    void setX(int chunkX);
    void setZ(int chunkZ);

    void setWorld(World world);

    List<BlockState> getAllBlocks(Predicate<BlockState> consumer);

    boolean setBlockMaterial(int x, int y, int z, Material material);
    boolean setBlockData(int x, int y, int z, BlockData state);
    boolean setBlockState(int x, int y, int z, org.bukkit.block.BlockState state);
    Material getBlockMaterial(int x, int y, int z);

    void setAllBiome(org.bukkit.block.Biome biome);
    boolean setBiome(int x, int y, int z, org.bukkit.block.Biome biome);
    org.bukkit.block.Biome getBiome(int x, int y, int z);
    boolean containsBiome(NamespacedKey name);

    void setAllNMSBiome(Object biome);
    boolean setNMSBiome(int x, int y, int z, Object biome);
    Object getNMSBiome(int x, int y, int z);
    abstract class IBlockEntity {

        protected NBTCompoundTag nbt;
        protected int x, y, z, type;

        public void write(ByteBuf buffer) {
            int packed = (z & 15) << 4 | (z & 15);
            FriendlyByteBuf buf = new FriendlyByteBuf(buffer);
            buf.writeByte(packed);
            buf.writeShort(y);
            buf.writeVarInt(type);
            buf.writeNbt(nbt);
        }

        public int getType() {
            return type;
        }

        public NBTCompoundTag getNbt() {
            return nbt;
        }

        public Location getLocation() {
            return new Location(null, x, y, z);
        }

        public void setType(int type) {
            this.type = type;
        }

        public void setNbt(NBTCompoundTag nbt) {
            this.nbt = nbt;
        }
        public void setLocation(Location location) {
            this.x = location.getBlockX() & 15;
            this.y = location.getBlockY();
            this.z = location.getBlockZ() & 15;
        }
    }
}
