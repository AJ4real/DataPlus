/**********************************
 Copyright (c) All Rights Reserved
 *********************************/

package me.aj4real.dataplus.api.nbt;

import me.aj4real.dataplus.api.FriendlyByteBuf;

import java.io.IOException;

public class NBTByteArrayTag implements NBTCollectionTag {
    private byte[] bytes;
    public NBTByteArrayTag(byte[] bytes) {
        this.bytes = bytes;
    }
    public byte[] get() {
        return this.bytes;
    }
    public NBTByteArrayTag clone() {
        byte[] newObject = new byte[this.bytes.length];
        for (int i = 0; i < this.bytes.length; i++) {
            newObject[i] = this.bytes[i];
        }
        return new NBTByteArrayTag(newObject);
    }
    public void write(FriendlyByteBuf buf) throws IOException {
        buf.writeInt(this.bytes.length);
        buf.writeBytes(this.bytes);
    }
    public static NBTByteArrayTag read(FriendlyByteBuf buf) {
        int l = buf.readInt();
        return new NBTByteArrayTag(buf.readByteArray(l));
    }
    public byte getId() {
        return 7;
    }

}
