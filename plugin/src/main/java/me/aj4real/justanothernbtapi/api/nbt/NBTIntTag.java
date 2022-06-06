/**********************************
 Copyright (c) All Rights Reserved
 *********************************/

package me.aj4real.justanothernbtapi.api.nbt;

public class NBTIntTag implements NBTNumericTag {
    private int object;
    public NBTIntTag(int object) {
        this.object = object;
    }
    public int get() {
        return this.object;
    }
}
