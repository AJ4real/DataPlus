/**********************************
 Copyright (c) All Rights Reserved
 *********************************/

package me.aj4real.justanothernbtapi.api.nbt;

public class NBTIntArrayTag implements NBTCollectionTag {
    private int[] object;
    public NBTIntArrayTag(int[] object) {
        this.object = object;
    }
    public int[] get() {
        return this.object;
    }
}
