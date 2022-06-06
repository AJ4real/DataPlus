/**********************************
 Copyright (c) All Rights Reserved
 *********************************/

package me.aj4real.justanothernbtapi.api.nbt;

public class NBTFloatTag implements NBTNumericTag {
    private float object;
    public NBTFloatTag(float object) {
        this.object = object;
    }
    public float get() {
        return this.object;
    }
}
