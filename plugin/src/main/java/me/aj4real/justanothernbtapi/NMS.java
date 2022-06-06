/**********************************
 Copyright (c) All Rights Reserved
 *********************************/

package me.aj4real.justanothernbtapi;

import me.aj4real.justanothernbtapi.api.nbt.NBTCompoundTag;
import me.aj4real.justanothernbtapi.api.nbt.NBTTag;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

public interface NMS {

    void onEnable(Plugin plugin);

    Object toNMS(NBTTag o);
    NBTTag fromNMS(Object o);

    NBTCompoundTag getItemNbt(ItemStack item);
    ItemStack getItem(NBTCompoundTag nbt);

    NBTCompoundTag getEntityNbt(Entity entity);
    void applyEntityNbt(Entity entity, NBTCompoundTag nbt);

    NBTCompoundTag getTileEntityNbt(Location location);
    void putTileEntityNbt(Location location, NBTCompoundTag nbt, boolean clean);

}
