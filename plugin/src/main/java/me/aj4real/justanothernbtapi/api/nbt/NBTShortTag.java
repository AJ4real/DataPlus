/**********************************
 Copyright (c) All Rights Reserved
 *********************************/

package me.aj4real.justanothernbtapi.api.nbt;

public class NBTShortTag implements NBTTag {
    private short object;
    public NBTShortTag(short object) {
        this.object = object;
    }
    public short get() {
        return this.object;
    }
}
