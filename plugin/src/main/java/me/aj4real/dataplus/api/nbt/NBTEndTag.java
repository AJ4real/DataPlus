/**********************************
 Copyright (c) All Rights Reserved
 *********************************/

package me.aj4real.dataplus.api.nbt;

import me.aj4real.dataplus.api.FriendlyByteBuf;

import java.io.IOException;

public class NBTEndTag implements NBTTag {
    public static final NBTEndTag INSTANCE = new NBTEndTag();
    private NBTEndTag() {}

    public void write(FriendlyByteBuf var0) throws IOException {
    }

    public NBTEndTag clone() {
        return this;
    }
    public byte getId() {
        return 0;
    }

    public boolean isValid() {
        return true;
    }

}
