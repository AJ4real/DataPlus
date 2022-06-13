/**********************************
 Copyright (c) All Rights Reserved
 *********************************/

package me.aj4real.justanothernbtapi.api.nbt;

import me.aj4real.justanothernbtapi.api.FriendlyByteBuf;

import java.io.IOException;

public class NBTIntArrayTag implements NBTCollectionTag {
    private int[] object;
    public NBTIntArrayTag(int[] object) {
        this.object = object;
    }
    public int[] get() {
        return this.object;
    }
    public void write(FriendlyByteBuf buf) throws IOException {
        buf.writeInt(this.object.length);
        int[] aint = this.object;
        int i = aint.length;
        for(int j = 0; j < i; ++j) {
            int k = aint[j];
            buf.writeInt(k);
        }
    }
    public static NBTIntArrayTag read(FriendlyByteBuf buf) {
        int l = buf.readInt();
        int[] ints = new int[l];
        for (int i = 0; i < l; i++) {
            ints[i] = buf.readInt();
        }
        return new NBTIntArrayTag(ints);
    }
    public byte getId() {
        return 11;
    }


}
