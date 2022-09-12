/**********************************
 Copyright (c) All Rights Reserved
 *********************************/

package me.aj4real.dataplus.nms.v1_18_R2;


import io.netty.buffer.Unpooled;
import me.aj4real.dataplus.api.ChunkDataPacketEditor;
import me.aj4real.dataplus.reflection.ClassAccessor;
import me.aj4real.dataplus.reflection.ConstructorHandle;
import me.aj4real.dataplus.reflection.FieldAccessor;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.game.ClientboundLevelChunkPacketData;
import net.minecraft.network.protocol.game.ClientboundLevelChunkWithLightPacket;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.PalettedContainer;
import org.bukkit.*;
import org.bukkit.block.data.BlockData;
import org.bukkit.craftbukkit.v1_18_R2.CraftChunk;
import org.bukkit.craftbukkit.v1_18_R2.CraftServer;
import org.bukkit.craftbukkit.v1_18_R2.CraftWorld;
import org.bukkit.craftbukkit.v1_18_R2.block.CraftBlock;
import org.bukkit.craftbukkit.v1_18_R2.block.CraftBlockState;
import org.bukkit.craftbukkit.v1_18_R2.block.CraftBlockStates;
import org.bukkit.craftbukkit.v1_18_R2.util.CraftMagicNumbers;
import org.bukkit.craftbukkit.v1_18_R2.util.CraftNamespacedKey;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

public class ChunkDataPacketEditorImpl implements ChunkDataPacketEditor {

    public static final FieldAccessor<byte[]> chunkdataBuffer;
    public static final FieldAccessor<ClientboundLevelChunkPacketData> packetChunkData;
    public static final ConstructorHandle<PalettedContainer> palettedContainer;

    private static final RegistryAccess access;

    static {
        ClassAccessor<ClientboundLevelChunkPacketData> chunkpacket = ClassAccessor.of(ClientboundLevelChunkPacketData.class);
        ClassAccessor<ClientboundLevelChunkWithLightPacket> chunkWithLightPacket = ClassAccessor.of(ClientboundLevelChunkWithLightPacket.class);
        chunkdataBuffer = chunkpacket.lookupField(byte[].class).get(0);
        packetChunkData = chunkWithLightPacket.lookupField(ClientboundLevelChunkPacketData.class).get(0);
        palettedContainer = ConstructorHandle.of(Arrays.stream(PalettedContainer.class.getConstructors()).filter((c) -> c.getParameterCount() == 3).findAny().get());
        access = ((CraftServer) Bukkit.getServer()).getHandle().getServer().registryAccess();
    }

    private final PalettedContainer<BlockState>[] states;
    private final int[] blockCount;
    private final Registry<Biome> registry;
    private final PalettedContainer<net.minecraft.world.level.biome.Biome>[] biomes;
    private final Chunk control;
    private int chunkX, chunkZ;
    private World world;
    private final ClientboundLevelChunkWithLightPacket original;
    private final int min, max;
    public ChunkDataPacketEditorImpl(Chunk chunk) throws InvocationTargetException, InstantiationException, IllegalAccessException {
        this(chunk.getWorld(), new ClientboundLevelChunkWithLightPacket(((CraftChunk)chunk).getHandle(), ((CraftWorld)chunk.getWorld()).getHandle().getLightEngine(), null, null, true));
    }
    public ChunkDataPacketEditorImpl(World world, ClientboundLevelChunkWithLightPacket packet) throws InvocationTargetException, InstantiationException, IllegalAccessException {
        FriendlyByteBuf buffer = new FriendlyByteBuf(Unpooled.buffer());
        packet.write(buffer);
        this.original = packet;
        this.world = world;
        this.chunkX = buffer.readInt();
        this.chunkZ = buffer.readInt();
        this.control = world.getChunkAt(chunkX, chunkZ);
        this.min = world.getMinHeight();
        this.max = world.getMaxHeight();
        int size = ((min - max) * -1) / 16;
        this.states = new PalettedContainer[size];
        this.blockCount = new int[size];
        this.biomes = new PalettedContainer[size];
        this.registry = ((CraftWorld)world).getHandle().registryAccess().registryOrThrow(Registry.BIOME_REGISTRY);
        FriendlyByteBuf tmp = packet.getChunkData().getReadBuffer();
        for (int y = 0; y < size; y++) {
            short blockCount = tmp.readShort();
            this.blockCount[y] = blockCount;
            PalettedContainer<BlockState> states = (PalettedContainer<BlockState>) palettedContainer.invoke(Block.BLOCK_STATE_REGISTRY, Blocks.AIR.defaultBlockState(), PalettedContainer.Strategy.SECTION_STATES);
            states.read(tmp);
            this.states[y] = states;
            PalettedContainer<net.minecraft.world.level.biome.Biome> biomes = (PalettedContainer<net.minecraft.world.level.biome.Biome>) palettedContainer.invoke(registry, registry.getOrThrow(Biomes.THE_VOID), PalettedContainer.Strategy.SECTION_BIOMES);
            biomes.read(tmp);
            this.biomes[y] = biomes;
        }
    }

    public ClientboundLevelChunkWithLightPacket build() throws IllegalAccessException {
        FriendlyByteBuf buffer = new FriendlyByteBuf(Unpooled.buffer());
        this.original.write(buffer);
        ClientboundLevelChunkWithLightPacket duplicate = new ClientboundLevelChunkWithLightPacket(buffer);
        ClientboundLevelChunkPacketData chunkData = duplicate.getChunkData();
        int size = 0;
        for(int y = 0; y < this.states.length; ++y) {
            size += 2 + this.states[y].getSerializedSize() + this.biomes[y].getSerializedSize();
        }
        byte[] data = new byte[size];
        FriendlyByteBuf dataBuffer = new FriendlyByteBuf(Unpooled.wrappedBuffer(data));
        dataBuffer.writerIndex(0);
        for(int y = 0; y < this.states.length; y++) {
            dataBuffer.writeShort(this.blockCount[y]);
            this.states[y].write(dataBuffer);
            this.biomes[y].write(dataBuffer);
        }
        chunkdataBuffer.set(chunkData, data);
        return duplicate;
    }

    public int getChunkX() {
        return this.chunkX;
    }
    public int getChunkZ() {
        return this.chunkZ;
    }
    public World getWorld() {
        return this.world;
    }
    public Chunk getChunk() {
        return this.control;
    }

    public void setX(int chunkX) {
        this.chunkX = chunkX;
    }

    public void setZ(int chunkZ) {
        this.chunkZ = chunkZ;
    }

    public void setWorld(World world) {
        this.world = world;
    }

    public List<org.bukkit.block.BlockState> getAllBlocks(Predicate<org.bukkit.block.BlockState> consumer) {
        List<org.bukkit.block.BlockState> ret = new ArrayList<>();
        for (int y = 0; y < this.states.length; y++) {
            PalettedContainer<BlockState> states = this.states[y];
            states.getAll((b) -> {
                org.bukkit.block.BlockState state = CraftBlockStates.getBlockState(b, null);
                if(consumer.test(state)) {
                    ret.add(state);
                }
            });
        }
        return ret;
    }

    public boolean setBlockMaterial(int x, int y, int z, Material material) {
        int i = this.getSectionIndex(y);
        Material old = getBlockMaterial(x, y, z);
        if(old.isAir() && !material.isAir()) this.blockCount[i]++;
        if(!old.isAir() && material.isAir()) this.blockCount[i]--;
        PalettedContainer<BlockState> palette = this.states[i];
        palette.acquire();
        palette.set(x, y & 15, z, CraftMagicNumbers.getBlock(material).defaultBlockState());
        palette.release();
        return material.equals(getBlockMaterial(x,y,z));
    }
    public boolean setBlockData(int x, int y, int z, BlockData state) {
        int i = this.getSectionIndex(y);
        Material old = getBlockMaterial(x, y, z);
        Material material = state.getMaterial();
        if(old.isAir() && !material.isAir()) this.blockCount[i]++;
        if(!old.isAir() && material.isAir()) this.blockCount[i]--;
        PalettedContainer<BlockState> palette = this.states[i];
        palette.acquire();
        this.states[i].set(x, y & 15, z, ((CraftBlockState)state).getHandle());
        palette.release();
        return material.equals(getBlockMaterial(x,y,z));
    }
    public boolean setBlockState(int x, int y, int z, org.bukkit.block.BlockState state) {
        int i = this.getSectionIndex(y);
        Material old = getBlockMaterial(x, y, z);
        Material material = state.getBlockData().getMaterial();
        if(old.isAir() && !material.isAir()) this.blockCount[i]++;
        if(!old.isAir() && material.isAir()) this.blockCount[i]--;
        PalettedContainer<BlockState> palette = this.states[i];
        palette.acquire();
        palette.set(x, y & 15, z, ((CraftBlockState)state).getHandle());
        palette.release();
        return material.equals(getBlockMaterial(x,y,z));
    }
    public Material getBlockMaterial(int x, int y, int z) {
        return CraftMagicNumbers.getMaterial(this.states[this.getSectionIndex(y)].get(x, y & 15, z).getBlock());
    }
    public void setAllBiome(org.bukkit.block.Biome biome) {
        net.minecraft.world.level.biome.Biome nmsBiome = CraftBlock.biomeToBiomeBase(registry, biome).value();
        for (int i = 0; i < this.biomes.length; i++) {
            PalettedContainer<net.minecraft.world.level.biome.Biome> palette = this.biomes[i];
            palette.acquire();
            for (int x = 0; x < 4; x++) {
                for (int y = 0; y < 4; y++) {
                    for (int z = 0; z < 4; z++) {
                        palette.set(x,y,z,nmsBiome);
                    }
                }
            }
            palette.release();
        }
    }
    public boolean setBiome(int x, int y, int z, org.bukkit.block.Biome biome) {
        PalettedContainer<net.minecraft.world.level.biome.Biome> palette = this.biomes[this.getSectionIndex(y)];
        palette.acquire();
        palette.set((x & 15) >> 2, (y & 15) >> 2, (z & 15) >> 2, CraftBlock.biomeToBiomeBase(registry, biome).value());
        palette.release();
        return getBiome(x, y, z).equals(biome);
    }
    public org.bukkit.block.Biome getBiome(int x, int y, int z) {
        PalettedContainer<net.minecraft.world.level.biome.Biome> biome = this.biomes[this.getSectionIndex(y)];
        return CraftBlock.biomeBaseToBiome(this.registry, biome.get(x >> 2, (y & 15) >> 2, z >> 2));
    }
    public boolean containsBiome(NamespacedKey name) {
        Registry<Biome> registry = access.registry(Registry.BIOME_REGISTRY).get();
        for (int i = 0; i < this.biomes.length; i++) {
            PalettedContainer<Biome> palette = this.biomes[i];
            palette.acquire();
            boolean bool = palette.maybeHas((b) -> b == registry.get(CraftNamespacedKey.toMinecraft(name)));
            if(bool) return true;
            palette.release();
        }
        return false;
    }
    public void setAllNMSBiome(Object biome) {
        assert (biome instanceof net.minecraft.world.level.biome.Biome);
        for (int i = 0; i < this.biomes.length; i++) {
            PalettedContainer<net.minecraft.world.level.biome.Biome> palette = this.biomes[i];
            palette.acquire();
            for (int x = 0; x < 4; x++) {
                for (int y = 0; y < 4; y++) {
                    for (int z = 0; z < 4; z++) {
                        palette.set(x,y,z, (Biome) biome);
                    }
                }
            }
            palette.release();
        }
    }
    public boolean setNMSBiome(int x, int y, int z, Object biome) {
        assert (biome instanceof net.minecraft.world.level.biome.Biome);
        PalettedContainer<net.minecraft.world.level.biome.Biome> palette = this.biomes[this.getSectionIndex(y)];
        palette.acquire();
        palette.set((x & 15) >> 2, (y & 15) >> 2, (z & 15) >> 2, (Biome) biome);
        palette.release();
        return getNMSBiome(x, y, z).equals(biome);
    }
    public net.minecraft.world.level.biome.Biome getNMSBiome(int x, int y, int z) {
        PalettedContainer<net.minecraft.world.level.biome.Biome> biome = this.biomes[this.getSectionIndex(y)];
        return biome.get(x >> 2, (y & 15) >> 2, z >> 2);
    }

    private int getSectionIndex(int y) {
        return y - min >> 4;
    }
}
