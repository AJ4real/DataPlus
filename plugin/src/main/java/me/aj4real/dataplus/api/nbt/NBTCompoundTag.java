/**********************************
 Copyright (c) All Rights Reserved
 *********************************/

package me.aj4real.dataplus.api.nbt;

import io.netty.buffer.Unpooled;
import me.aj4real.dataplus.DataPlus;
import me.aj4real.dataplus.api.FriendlyByteBuf;
import org.bukkit.NamespacedKey;
import org.bukkit.persistence.PersistentDataAdapterContext;
import org.bukkit.persistence.PersistentDataType;

import javax.swing.text.Keymap;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;

public class NBTCompoundTag extends HashMap<String, NBTTag> implements NBTTag {

    @SuppressWarnings("unused")
    public static final PersistentDataType<byte[], NBTCompoundTag> PERSISTENT_DATA_TYPE = new PersistentDataType<>() {
        @Override
        public Class<byte[]> getPrimitiveType() {
            return byte[].class;
        }

        @Override
        public Class<NBTCompoundTag> getComplexType() {
            return NBTCompoundTag.class;
        }

        @Override
        public byte[] toPrimitive(NBTCompoundTag complex, PersistentDataAdapterContext context) {
            FriendlyByteBuf buf = new FriendlyByteBuf(Unpooled.buffer());
            buf.writeNbt(complex);
            return buf.array();
        }

        @Override
        public NBTCompoundTag fromPrimitive(byte[] primitive, PersistentDataAdapterContext context) {
            FriendlyByteBuf buf = new FriendlyByteBuf(Unpooled.wrappedBuffer(primitive));
            return buf.readNbt();
        }
    };

    public NBTCompoundTag clone() {
        NBTCompoundTag ret = new NBTCompoundTag();
        this.forEach((k,v) -> {
            ret.put(k, v.clone());
        });
        return ret;
    }

    public void write(FriendlyByteBuf buf) throws IOException {
        buf.writeNbt(this);
    }
    public static NBTCompoundTag read(FriendlyByteBuf buf) {
        return buf.readNbt();
    }

    public byte getId() {
        return 10;
    }

    public void putBoolean(String key, boolean value) {
        putByte(key, (byte) (value ? 1 : 0));
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
    public void putNamespacedKey(String key, NamespacedKey value) {
        put(key, NBTStringTag.valueOf(value.toString()));
    }
    public void putShort(String key, short value) {
        put(key, new NBTShortTag(value));
    }
    public void putString(String key, String value) {
        put(key, NBTStringTag.valueOf(value));
    }

    public boolean getBoolean(String key) {
        return getByte(key) == 1;
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
    public NamespacedKey getNamespacedKey(String key) {
        return NamespacedKey.fromString(getString(key));
    }
    public short getShort(String key) {
        return ((NBTShortTag)get(key)).get();
    }
    public String getString(String key) {
        return ((NBTStringTag)get(key)).toString();
    }

}
