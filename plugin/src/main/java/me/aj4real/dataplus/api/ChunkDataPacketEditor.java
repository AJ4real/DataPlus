/**********************************
 Copyright (c) All Rights Reserved
 *********************************/

package me.aj4real.dataplus.api;

import me.aj4real.dataplus.DataPlus;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.World;
import org.bukkit.block.data.BlockData;

import java.lang.reflect.InvocationTargetException;

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
}
