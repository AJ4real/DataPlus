/**********************************
 Copyright (c) All Rights Reserved
 *********************************/

package me.aj4real.dataplus.api.nbt;

import me.aj4real.dataplus.api.FriendlyByteBuf;

import java.io.IOException;

public interface NBTTag {
   void write(FriendlyByteBuf buf) throws IOException;
   byte getId();
   NBTTag clone();
}
