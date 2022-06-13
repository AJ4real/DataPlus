/**********************************
 Copyright (c) All Rights Reserved
 *********************************/

package me.aj4real.justanothernbtapi.api.nbt;

import me.aj4real.justanothernbtapi.api.FriendlyByteBuf;

public class NBTShortTag implements NBTNumericTag {
    private short object;
    public NBTShortTag(short object) {
        this.object = object;
    }
    public short get() {
        return this.object;
    }

    public void write(FriendlyByteBuf buf) {
        buf.writeShort(object);
    }
    public static NBTShortTag read(FriendlyByteBuf buf) {
        return new NBTShortTag(buf.readShort());
    }
    public byte getId() {
        return 2;
    }

    public long getAsLong() {
        return this.object;
    }
    public int getAsInt() {
        return this.object;
    }
    public short getAsShort() {
        return this.object;
    }
    public double getAsDouble() {
        return this.object;
    }
    public float getAsFloat() {
        return this.object;
    }
}
