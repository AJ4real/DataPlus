/**********************************
 Copyright (c) All Rights Reserved
 *********************************/

package me.aj4real.dataplus;

import me.aj4real.dataplus.api.ChunkDataPacketEditor;
import me.aj4real.dataplus.api.FriendlyByteBuf;
import me.aj4real.dataplus.api.nbt.NBTCompoundTag;
import me.aj4real.dataplus.api.nbt.NBTListTag;
import me.aj4real.dataplus.api.nbt.NBTTag;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.lang.reflect.InvocationTargetException;

public interface DataPlusNMS {

    void onEnable(Plugin plugin);

    NBTCompoundTag getDefaultLoginCodec();

    ChunkDataPacketEditor getChunkDataPacketEditor(World world, Object packet) throws InvocationTargetException, InstantiationException, IllegalAccessException;
    ChunkDataPacketEditor getChunkDataPacketEditor(Chunk chunk) throws InvocationTargetException, InstantiationException, IllegalAccessException;

    void writeNbt(FriendlyByteBuf buf, NBTCompoundTag nbt);
    NBTCompoundTag readNbt(FriendlyByteBuf buf);

    Object toNMS(NBTTag o);
    NBTTag fromNMS(Object o);

    NBTCompoundTag getItemNbt(ItemStack item);
    ItemStack getItem(NBTCompoundTag nbt);

    NBTCompoundTag getEntityNbt(Entity entity);
    void applyEntityNbt(Entity entity, NBTCompoundTag nbt);

    NBTCompoundTag getTileEntityNbt(Location location);
    void putTileEntityNbt(Location location, NBTCompoundTag nbt, boolean clean);

    NBTListTag getItemEnchantments(ItemStack item);
    NBTCompoundTag getBlockNbt(Location location);
    NBTCompoundTag getFluidNbt(Location location);
}
