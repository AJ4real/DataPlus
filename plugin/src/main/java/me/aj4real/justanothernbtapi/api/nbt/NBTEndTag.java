/**********************************
 Copyright (c) All Rights Reserved
 *********************************/

package me.aj4real.justanothernbtapi.api.nbt;

import me.aj4real.justanothernbtapi.api.FriendlyByteBuf;

import java.io.IOException;

public class NBTEndTag implements NBTTag {
    public static final NBTEndTag INSTANCE = new NBTEndTag();
    private NBTEndTag() {}

    public void write(FriendlyByteBuf var0) throws IOException {
    }

    public byte getId() {
        return 0;
    }

}
