/**********************************
 Copyright (c) All Rights Reserved
 *********************************/

package me.aj4real.dataplus.api.nbt;

import me.aj4real.dataplus.api.FriendlyByteBuf;

public class NBTLongArrayTag implements NBTCollectionTag {
    private long[] object;
    public NBTLongArrayTag(long[] object) {
        this.object = object;
    }

    public NBTLongArrayTag clone() {
        long[] newObject = new long[this.object.length];
        for (int i = 0; i < this.object.length; i++) {
            newObject[i] = this.object[i];
        }
        return new NBTLongArrayTag(newObject);
    }
    public void write(FriendlyByteBuf buf) {
        buf.writeInt(this.object.length);
        long[] var2 = this.object;
        int var3 = var2.length;
        for(int i = 0; i < var3; ++i) {
            long var4 = var2[i];
            buf.writeLong(var4);
        }
    }
    public static NBTLongArrayTag read(FriendlyByteBuf buf) {
        int l = buf.readInt();
        long[] ret = new long[l];
        for (int i = 0; i < l; i++) {
            ret[i] = buf.readLong();
        }
        return new NBTLongArrayTag(ret);
    }

    public byte getId() {
        return 12;
    }

    public long[] get() {
        return this.object;
    }
}
