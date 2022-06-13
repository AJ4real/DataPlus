/**********************************
 Copyright (c) All Rights Reserved
 *********************************/

package me.aj4real.justanothernbtapi.api.nbt;

import me.aj4real.justanothernbtapi.api.FriendlyByteBuf;

public class NBTLongTag implements NBTNumericTag {
    private long object;
    public NBTLongTag(long object) {
        this.object = object;
    }
    public long get() {
        return this.object;
    }

    public void write(FriendlyByteBuf buf) {
        buf.writeLong(object);
    }
    public static NBTLongTag read(FriendlyByteBuf buf) {
        return new NBTLongTag(buf.readLong());
    }
    public byte getId() {
        return 4;
    }

    public long getAsLong() {
        return this.object;
    }
    public int getAsInt() {
        return (int) this.object;
    }
    public short getAsShort() {
        return (short)this.object;
    }
    public double getAsDouble() {
        return this.object;
    }
    public float getAsFloat() {
        return (float)this.object;
    }
}
