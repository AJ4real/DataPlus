/**********************************
 Copyright (c) All Rights Reserved
 *********************************/

package me.aj4real.justanothernbtapi.api.nbt;

public class NBTDoubleTag implements NBTNumericTag {
    private double object;
    public NBTDoubleTag(double object) {
        this.object = object;
    }
    public double get() {
        return this.object;
    }
}
