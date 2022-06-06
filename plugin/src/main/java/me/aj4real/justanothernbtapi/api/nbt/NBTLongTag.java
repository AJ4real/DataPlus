/**********************************
 Copyright (c) All Rights Reserved
 *********************************/

package me.aj4real.justanothernbtapi.api.nbt;

public class NBTLongTag implements NBTNumericTag {
    private long object;
    public NBTLongTag(long object) {
        this.object = object;
    }
    public long get() {
        return this.object;
    }
}
