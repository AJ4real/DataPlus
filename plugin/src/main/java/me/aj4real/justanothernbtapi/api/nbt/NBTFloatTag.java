/**********************************
 Copyright (c) All Rights Reserved
 *********************************/

package me.aj4real.justanothernbtapi.api.nbt;

import me.aj4real.justanothernbtapi.api.FriendlyByteBuf;

import java.io.DataOutput;
import java.io.IOException;

public class NBTFloatTag implements NBTNumericTag {
    private float object;
    public NBTFloatTag(float object) {
        this.object = object;
    }
    public float get() {
        return this.object;
    }
    public void write(FriendlyByteBuf buf) throws IOException {
        buf.writeFloat(this.object);
    }
    public static NBTFloatTag read(FriendlyByteBuf buf) {
        return new NBTFloatTag(buf.readFloat());
    }
    public long getAsLong() {
        return (long)this.object;
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
        return this.object;
    }
    public byte getId() {
        return 5;
    }

}
