/**********************************
 Copyright (c) All Rights Reserved
 *********************************/

package me.aj4real.justanothernbtapi.api.nbt;

public class NBTByteTag implements NBTNumericTag {
    private byte b;
    private NBTByteTag(byte b) {
        this.b = b;
    }

    public static NBTByteTag valueOf(byte b) {
        return Cache.cache[128 + b];
    }

    public byte get() {
        return this.b;
    }

    public long getAsLong() {
        return (long)this.b;
    }

    public int getAsInt() {
        return this.b;
    }

    public short getAsShort() {
        return (short)this.b;
    }

    public double getAsDouble() {
        return (double)this.b;
    }

    public float getAsFloat() {
        return (float)this.b;
    }

    private static class Cache {
        static final NBTByteTag[] cache = new NBTByteTag[256];
        private Cache() {
        }
        static {
            for(int var0 = 0; var0 < cache.length; ++var0) {
                cache[var0] = new NBTByteTag((byte)(var0 - 128));
            }

        }
    }
}
