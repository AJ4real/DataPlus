/**********************************
 Copyright (c) All Rights Reserved
 *********************************/

package me.aj4real.justanothernbtapi.api.nbt;

import me.aj4real.justanothernbtapi.api.FriendlyByteBuf;

import java.io.IOException;

public class NBTDoubleTag implements NBTNumericTag {
    private double object;
    public NBTDoubleTag(double object) {
        this.object = object;
    }
    
    public double get() {
        return this.object;
    }

    public void write(FriendlyByteBuf buf) throws IOException {
        buf.writeDouble(this.object);
    }
    public static NBTDoubleTag read(FriendlyByteBuf buf) {
        return new NBTDoubleTag(buf.readDouble());
    }
    public byte getId() {
        return 6;
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
        return (float)this.object;
    }

}
