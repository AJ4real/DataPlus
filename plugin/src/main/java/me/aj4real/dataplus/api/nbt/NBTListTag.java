/**********************************
 Copyright (c) All Rights Reserved
 *********************************/

package me.aj4real.dataplus.api.nbt;

import com.google.common.collect.Lists;
import me.aj4real.dataplus.api.FriendlyByteBuf;
import me.aj4real.dataplus.api.NbtIo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;

public class NBTListTag extends ArrayList<NBTTag> implements NBTCollectionTag {
    private final byte type;
    public NBTListTag() {
        super();
        type = 0;
    }
    public NBTListTag(NBTTag... tags) {
        super(Arrays.asList(tags));
        type = tags.length == 0 ? 0 : tags[0].getId();
    }
    public NBTListTag(List<NBTTag> tags) {
        super(tags);
        type = tags.size() == 0 ? 0 : tags.get(0).getId();
    }

    public NBTListTag clone() {
        NBTListTag ret = new NBTListTag();
        for (NBTTag nbtTag : this) {
            ret.add(nbtTag.clone());
        }
        return ret;
    }
    public <T extends NBTTag> void forEach(Class<T> type, Consumer<T> consumer) {
        super.forEach((t) -> {
            if(type.isInstance(t)) {
                consumer.accept((T) t);
            }
        });
    }
    public void write(FriendlyByteBuf buf) throws IOException {
        buf.writeByte(this.type);
        buf.writeInt(this.size());
        Iterator var2 = this.iterator();

        while(var2.hasNext()) {
            NBTTag var3 = (NBTTag)var2.next();
            var3.write(buf);
        }
    }
    public static NBTListTag read(FriendlyByteBuf var0) {
        byte var3 = var0.readByte();
        int var4 = var0.readInt();
        if (var3 == 0 && var4 > 0) {
            throw new RuntimeException("Missing type on ListTag");
        } else {
            List<NBTTag> var6 = Lists.newArrayListWithCapacity(var4);
            for(int var7 = 0; var7 < var4; ++var7) {
                var6.add(NbtIo.parseNbt(var0, true));
            }
            return new NBTListTag(var6);
        }
    }
    public byte getId() {
        return 9;
    }
    public boolean addByteArray(byte[] value) {
        return add(new NBTByteArrayTag(value));
    }
    public boolean addByte(byte value) {
        return add(NBTByteTag.valueOf(value));
    }
    public boolean addCompound(NBTCompoundTag value) {
        return add(value);
    }
    public boolean addDouble(double value) {
        return add(new NBTDoubleTag(value));
    }
    public boolean addFloat(float value) {
        return add(new NBTFloatTag(value));
    }
    public boolean addIntArray(int[] value) {
        return add(new NBTIntArrayTag(value));
    }
    public boolean addInt(int value) {
        return add(new NBTIntTag(value));
    }
    public boolean addList(List<NBTTag> value) {
        return add(new NBTListTag(value));
    }
    public boolean addLongArray(long[] value) {
        return add(new NBTLongArrayTag(value));
    }
    public boolean addLong(long value) {
        return add(new NBTLongTag(value));
    }
    public boolean addShort(short value) {
        return add(new NBTShortTag(value));
    }
    public boolean addString(String value) {
        return add(NBTStringTag.valueOf(value));
    }

    public void setByteArray(int index, byte[] value) {
        set(index, new NBTByteArrayTag(value));
    }
    public void setByte(int index, byte value) {
        set(index, NBTByteTag.valueOf(value));
    }
    public void setCompound(int index, NBTCompoundTag value) {
        set(index, value);
    }
    public void setDouble(int index, double value) {
        set(index, new NBTDoubleTag(value));
    }
    public void setFloat(int index, float value) {
        set(index, new NBTFloatTag(value));
    }
    public void setIntArray(int index, int[] value) {
        set(index, new NBTIntArrayTag(value));
    }
    public void setInt(int index, int value) {
        set(index, new NBTIntTag(value));
    }
    public void setList(int index, List<NBTTag> value) {
        set(index, new NBTListTag(value));
    }
    public void setLongArray(int index, long[] value) {
        set(index, new NBTLongArrayTag(value));
    }
    public void setLong(int index, long value) {
        set(index, new NBTLongTag(value));
    }
    public void setShort(int index, short value) {
        set(index, new NBTShortTag(value));
    }
    public void setString(int index, String value) {
        set(index, NBTStringTag.valueOf(value));
    }

    public byte[] getByteArray(int index) {
        return ((NBTByteArrayTag)get(index)).get();
    }
    public byte getByte(int index) {
        return ((NBTByteTag)get(index)).get();
    }
    public NBTCompoundTag getCompound(int index) {
        return (NBTCompoundTag) get(index);
    }
    public double getDouble(int index) {
        return ((NBTDoubleTag)get(index)).get();
    }
    public float getFloat(int index) {
        return ((NBTFloatTag)get(index)).get();
    }
    public int[] getIntArray(int index) {
        return ((NBTIntArrayTag)get(index)).get();
    }
    public int getInt(int index) {
        return ((NBTIntTag)get(index)).get();
    }
    public NBTListTag getList(int index) {
        return (NBTListTag) get(index);
    }
    public long[] getLongArray(int index) {
        return ((NBTLongArrayTag)get(index)).get();
    }
    public long getLong(int index) {
        return ((NBTLongTag)get(index)).get();
    }
    public short getShort(int index) {
        return ((NBTShortTag)get(index)).get();
    }
    public String getString(int index) {
        return ((NBTStringTag)get(index)).toString();
    }

}
