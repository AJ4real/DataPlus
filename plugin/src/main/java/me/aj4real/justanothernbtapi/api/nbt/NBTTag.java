/**********************************
 Copyright (c) All Rights Reserved
 *********************************/

package me.aj4real.justanothernbtapi.api.nbt;

import me.aj4real.justanothernbtapi.api.FriendlyByteBuf;

import java.io.IOException;

public interface NBTTag {
   void write(FriendlyByteBuf buf) throws IOException;
   byte getId();
}
