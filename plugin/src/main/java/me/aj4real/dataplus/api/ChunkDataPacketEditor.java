/**********************************
 Copyright (c) All Rights Reserved
 *********************************/

package me.aj4real.dataplus.api;

import io.netty.buffer.ByteBuf;
import me.aj4real.dataplus.DataPlus;
import me.aj4real.dataplus.api.nbt.NBTCompoundTag;
import org.bukkit.*;
import org.bukkit.block.BlockState;
import org.bukkit.block.TileState;
import org.bukkit.block.data.BlockData;
import org.checkerframework.checker.signature.qual.BinaryNameWithoutPackage;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.function.Predicate;

public interface ChunkDataPacketEditor {
    static ChunkDataPacketEditor newInstance(Chunk chunk) throws InvocationTargetException, InstantiationException, IllegalAccessException {
        return DataPlus.nms.getChunkDataPacketEditor(chunk);
    }
    static ChunkDataPacketEditor newInstance(World world, Object packet) throws InvocationTargetException, InstantiationException, IllegalAccessException {
        return DataPlus.nms.getChunkDataPacketEditor(world, packet);
    }
    enum SupportedVersions {
        v1_18_R1,
        v1_18_R2,
        v1_19_R1
    }

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
    class BlockEntity {

        private NBTCompoundTag nbt;
        private int x, y, z, type;

        public BlockEntity(int packedxz, int y, int type, NBTCompoundTag nbt) {
            this.x = ((packedxz >> 4) & 15);
            this.y = y;
            this.z = (packedxz & 15);
            this.type = type;
            this.nbt = nbt;
        }

        public void write(ByteBuf buffer) {
            int packed = (z & 15) << 4 | (z & 15);
            FriendlyByteBuf buf = new FriendlyByteBuf(buffer);
            buf.writeByte(packed);
            buf.writeShort(y);
            buf.writeVarInt(type);
            buf.writeNbt(nbt);
        }

        public static BlockEntity read(ByteBuf buffer) {
            FriendlyByteBuf buf = new FriendlyByteBuf(buffer);
            buf.readerIndex(buffer.readerIndex());
            int packedxz = buf.readByte();
            int y = buf.readShort();
            int id = buf.readVarInt();
            NBTCompoundTag nbt = buf.readNbt();
            return new BlockEntity(packedxz, y, id, nbt);
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
            this.x = location.getBlockX();
            this.y = location.getBlockY();
            this.z = location.getBlockZ();
        }
    }
}
