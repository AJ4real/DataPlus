/**********************************
 Copyright (c) All Rights Reserved
 *********************************/

package me.aj4real.dataplus.api.nbt;

import me.aj4real.dataplus.api.FriendlyByteBuf;

public class NBTIntTag implements NBTNumericTag {
    private int object;
    public NBTIntTag(int object) {
        this.object = object;
    }
    public int get() {
        return this.object;
    }
    public NBTIntTag clone() {
        return new NBTIntTag(this.object);
    }
    public void write(FriendlyByteBuf buf) {
        buf.writeInt(this.object);
    }
    public static NBTIntTag read(FriendlyByteBuf buf) {
        return new NBTIntTag(buf.readInt());
    }

    public byte getId() {
        return 3;
    }

    public long getAsLong() {
        return this.object;
    }
    public int getAsInt() {
        return this.object;
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
