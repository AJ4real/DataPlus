/**********************************
 Copyright (c) All Rights Reserved
 *********************************/

package me.aj4real.justanothernbtapi.api.nbt;

import me.aj4real.justanothernbtapi.api.FriendlyByteBuf;
import me.aj4real.justanothernbtapi.api.NbtIo;
import org.bukkit.Bukkit;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class NBTCompoundTag extends HashMap<String, NBTTag> implements NBTTag {


    public void write(FriendlyByteBuf buf) throws IOException {
        Iterator it = this.keySet().iterator();
        while(it.hasNext()) {
            String key = (String)it.next();
            NBTTag tag = this.get(it);
            buf.writeByte(tag.getId());
            if(tag.getId() != 0) {
                buf.writeUtf(key);
                tag.write(buf);
            }
        }
        buf.writeByte(0);
    }

    public static NBTCompoundTag read(FriendlyByteBuf buf) {
        NBTCompoundTag ret = new NBTCompoundTag();
        while(buf.readByte() != 0) {
            Bukkit.getConsoleSender().sendMessage("b: " + buf.readerIndex());
            String name = buf.readUtf();
            Bukkit.getConsoleSender().sendMessage("1: " + name);
            NBTTag tag = NbtIo.parseNbt(buf, false);
            ret.put(name, tag);
        }
        return ret;
    }
    public byte getId() {
        return 10;
    }

    public void putByteArray(String key, byte[] value) {
        put(key, new NBTByteArrayTag(value));
    }
    public void putByte(String key, byte value) {
        put(key, NBTByteTag.valueOf(value));
    }
    public void putCompound(String key, NBTCompoundTag value) {
        put(key, value);
    }
    public void putDouble(String key, double value) {
        put(key, new NBTDoubleTag(value));
    }
    public void putFloat(String key, float value) {
        put(key, new NBTFloatTag(value));
    }
    public void putIntArray(String key, int[] value) {
        put(key, new NBTIntArrayTag(value));
    }
    public void putInt(String key, int value) {
        put(key, new NBTIntTag(value));
    }
    public void putList(String key, List<NBTTag> value) {
        put(key, new NBTListTag(value));
    }
    public void putLongArray(String key, long[] value) {
        put(key, new NBTLongArrayTag(value));
    }
    public void putLong(String key, long value) {
        put(key, new NBTLongTag(value));
    }
    public void putShort(String key, short value) {
        put(key, new NBTShortTag(value));
    }
    public void putString(String key, String value) {
        put(key, NBTStringTag.valueOf(value));
    }

    public byte[] getByteArray(String key) {
        return ((NBTByteArrayTag)get(key)).get();
    }
    public byte getByte(String key) {
        return ((NBTByteTag)get(key)).get();
    }
    public NBTCompoundTag getCompound(String key) {
        return (NBTCompoundTag) get(key);
    }
    public double getDouble(String key) {
        return ((NBTDoubleTag)get(key)).get();
    }
    public float getFloat(String key) {
        return ((NBTFloatTag)get(key)).get();
    }
    public int[] getIntArray(String key) {
        return ((NBTIntArrayTag)get(key)).get();
    }
    public int getInt(String key) {
        return ((NBTIntTag)get(key)).get();
    }
    public NBTListTag getList(String key) {
        return (NBTListTag) get(key);
    }
    public long[] getLongArray(String key) {
        return ((NBTLongArrayTag)get(key)).get();
    }
    public long getLong(String key) {
        return ((NBTLongTag)get(key)).get();
    }
    public short getShort(String key) {
        return ((NBTShortTag)get(key)).get();
    }
    public String getString(String key) {
        return ((NBTStringTag)get(key)).toString();
    }

}
