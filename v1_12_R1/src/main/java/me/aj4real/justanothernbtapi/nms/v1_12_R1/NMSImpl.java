/**********************************
 Copyright (c) All Rights Reserved
 *********************************/

package me.aj4real.justanothernbtapi.nms.v1_12_R1; import me.aj4real.justanothernbtapi.NMS;
import me.aj4real.justanothernbtapi.api.nbt.*;
import net.minecraft.server.v1_12_R1.*;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_12_R1.CraftChunk;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_12_R1.inventory.CraftItemStack;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

public class NMSImpl implements NMS {
    private static final Field b;
    private static NBTTagEnd end;
    static {
        b = NBTTagLongArray.class.getDeclaredFields()[0];
        b.setAccessible(true);
        Constructor<?> c = NBTTagEnd.class.getDeclaredConstructors()[0];
        c.setAccessible(true);
        try {
            end = (NBTTagEnd) c.newInstance(null);
        } catch (InstantiationException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }
    public void onEnable(Plugin plugin) {
    }

    private NBTBase convert(NBTTag object) {
        if(object instanceof NBTByteArrayTag) {
            NBTByteArrayTag tag = (NBTByteArrayTag) object;
            return new NBTTagByteArray(tag.get());
        }
        if(object instanceof NBTByteTag) {
            NBTByteTag tag = (NBTByteTag) object;
            return new NBTTagByte(tag.get());
        }
        if(object instanceof NBTCompoundTag) {
            NBTCompoundTag tag = (NBTCompoundTag) object;
            net.minecraft.server.v1_12_R1.NBTTagCompound ret = new net.minecraft.server.v1_12_R1.NBTTagCompound();
            for (String key : tag.keySet()) {
                ret.set(key, convert(tag.get(key)));
            }
            return ret;
        }
        if(object instanceof NBTDoubleTag) {
            NBTDoubleTag tag = (NBTDoubleTag) object;
            return new NBTTagDouble(tag.get());
        }
        if(object instanceof NBTEndTag) {
            return end;
        }
        if(object instanceof NBTFloatTag) {
            NBTFloatTag tag = (NBTFloatTag) object;
            return new NBTTagFloat(tag.get());
        }
        if(object instanceof NBTIntArrayTag) {
            NBTIntArrayTag tag = (NBTIntArrayTag) object;
            return new NBTTagIntArray(tag.get());
        }
        if(object instanceof NBTIntTag) {
            NBTIntTag tag = (NBTIntTag) object;
            return new NBTTagInt(tag.get());
        }
        if(object instanceof NBTListTag) {
            NBTListTag tag = (NBTListTag) object;
            NBTTagList ret = new NBTTagList();
            tag.get().forEach((t) -> ret.add(convert(t)));
            return ret;
        }
        if(object instanceof NBTLongArrayTag) {
            NBTLongArrayTag tag = (NBTLongArrayTag) object;
            return new NBTTagLongArray(tag.get());
        }
        if(object instanceof NBTLongTag) {
            NBTLongTag tag = (NBTLongTag) object;
            return new NBTTagLong(tag.get());
        }
        if(object instanceof NBTShortTag) {
            NBTShortTag tag = (NBTShortTag) object;
            return new NBTTagShort(tag.get());
        }
        if(object instanceof NBTStringTag) {
            NBTStringTag tag = (NBTStringTag) object;
            return new NBTTagString(tag.toString());
        }
        return null;
    }
    private NBTTag convert(NBTBase object) {
        if (object instanceof NBTTagByteArray) {
            NBTTagByteArray tag = (NBTTagByteArray) object;
            return new NBTByteArrayTag(tag.c());
        }
        if (object instanceof NBTTagByte) {
            NBTTagByte tag = (NBTTagByte) object;
            return NBTByteTag.valueOf(tag.g());
        }
        if (object instanceof NBTTagCompound) {
            NBTTagCompound tag = (NBTTagCompound) object;
            NBTCompoundTag ret = new NBTCompoundTag();
            for (String k : tag.c()) {
                ret.put(k, convert(tag.get(k)));
            }
            return ret;
        }
        if (object instanceof NBTTagDouble) {
            NBTTagDouble tag = (NBTTagDouble) object;
            return new NBTDoubleTag(tag.asDouble());
        }
        if (object instanceof NBTTagEnd) {
            return NBTEndTag.INSTANCE;
        }
        if (object instanceof NBTTagFloat) {
            NBTTagFloat tag = (NBTTagFloat) object;
            return new NBTFloatTag(tag.d());
        }
        if (object instanceof NBTTagIntArray) {
            NBTTagIntArray tag = (NBTTagIntArray) object;
            return new NBTIntArrayTag(tag.d());
        }
        if (object instanceof NBTTagInt) {
            NBTTagInt tag = (NBTTagInt) object;
            return new NBTIntTag(tag.f());
        }
        if (object instanceof NBTTagList) {
            NBTTagList tag = (NBTTagList) object;
            NBTListTag ret = new NBTListTag();
            for (int i = 0; i < tag.size(); i++) {
                ret.get().add(convert(tag.get(i)));
            }
            return ret;
        }
        if (object instanceof NBTTagLongArray) {
            NBTTagLongArray tag = (NBTTagLongArray) object;
            try {
                return new NBTLongArrayTag((long[]) b.get(tag));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        if (object instanceof NBTTagLong) {
            NBTTagLong tag = (NBTTagLong) object;
            return new NBTLongTag(tag.d());
        }
        if (object instanceof NBTTagShort) {
            NBTTagShort tag = (NBTTagShort) object;
            return new NBTShortTag(tag.f());
        }
        if (object instanceof NBTTagString) {
            NBTTagString tag = (NBTTagString) object;
            return NBTStringTag.valueOf(tag.c_());
        }
        return null;
    }

    public NBTCompoundTag getItemNbt(ItemStack item) {
        return (NBTCompoundTag) convert(CraftItemStack.asNMSCopy(item).save(new NBTTagCompound()));
    }
    public ItemStack getItem(NBTCompoundTag nbt) {
        return CraftItemStack.asBukkitCopy(new net.minecraft.server.v1_12_R1.ItemStack((NBTTagCompound) convert(nbt)));
    }
    public NBTCompoundTag getEntityNbt(Entity entity) {
        NBTTagCompound tag = new NBTTagCompound();
        ((CraftEntity) entity).getHandle().save(tag);
        return (NBTCompoundTag) convert(tag);
    }
    public void applyEntityNbt(Entity entity, NBTCompoundTag nbt) {
        ((CraftEntity)entity).getHandle().f((NBTTagCompound) convert(nbt));
    }
    public NBTCompoundTag getTileEntityNbt(Location location) {
        Chunk chunk = ((CraftChunk)location.getChunk()).getHandle();
        BlockPosition pos = new BlockPosition(location.getBlockX(), location.getBlockY(), location.getBlockZ());
        TileEntity entity = chunk.tileEntities.get(pos);
        if(entity instanceof TileEntityMobSpawner)
            return (NBTCompoundTag) convert(((TileEntityMobSpawner)entity).getSpawner().b(new NBTTagCompound()));
        return (NBTCompoundTag) convert(entity.save(new NBTTagCompound()));
    }
    public void putTileEntityNbt(Location location, NBTCompoundTag nbt, boolean clean) {
        Chunk chunk = ((CraftChunk)location.getChunk()).getHandle();
        BlockPosition pos = new BlockPosition(location.getBlockX(), location.getBlockY(), location.getBlockZ());
        if(clean) {
            TileEntity entity = chunk.tileEntities.get(pos);
            if(entity instanceof TileEntityMobSpawner)
                ((TileEntityMobSpawner)entity).getSpawner().a((NBTTagCompound) convert(nbt));
            entity.load((NBTTagCompound) convert(nbt));
        } else {
            chunk.tileEntities.get(pos).load((NBTTagCompound) convert(nbt));
        }
    }
}
