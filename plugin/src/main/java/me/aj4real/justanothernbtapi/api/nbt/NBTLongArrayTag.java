/**********************************
 Copyright (c) All Rights Reserved
 *********************************/

package me.aj4real.justanothernbtapi.api.nbt;

public class NBTLongArrayTag implements NBTTag {
    private long[] object;
    public NBTLongArrayTag(long[] object) {
        this.object = object;
    }
    public long[] get() {
        return this.object;
    }
}
