/**********************************
 Copyright (c) All Rights Reserved
 *********************************/

package me.aj4real.dataplus.api.nbt;

import me.aj4real.dataplus.api.FriendlyByteBuf;

import java.io.IOException;

public class NBTByteTag implements NBTNumericTag {
    private byte b;
    private NBTByteTag(byte b) {
        this.b = b;
    }

    public static NBTByteTag valueOf(byte b) {
        return Cache.cache[128 + b];
    }

    public NBTByteTag clone() {
        return this;
    }
    public byte get() {
        return this.b;
    }

    public void write(FriendlyByteBuf buf) throws IOException {
        buf.writeByte(this.b);
    }
    public static NBTByteTag read(FriendlyByteBuf buf) {
        return new NBTByteTag(buf.readByte());
    }
    public long getAsLong() {
        return this.b;
    }

    public int getAsInt() {
        return this.b;
    }

    public short getAsShort() {
        return this.b;
    }

    public double getAsDouble() {
        return this.b;
    }

    public float getAsFloat() {
        return this.b;
    }

    public byte getId() {
        return 1;
    }

    public boolean isValid() {
        return true;
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
