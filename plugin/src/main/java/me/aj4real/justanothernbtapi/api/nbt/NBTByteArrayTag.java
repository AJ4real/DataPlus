/**********************************
 Copyright (c) All Rights Reserved
 *********************************/

package me.aj4real.justanothernbtapi.api.nbt;

public class NBTByteArrayTag implements NBTCollectionTag {
    private byte[] bytes;
    public NBTByteArrayTag(byte[] bytes) {
        this.bytes = bytes;
    }
    public byte[] get() {
        return this.bytes;
    }
}
