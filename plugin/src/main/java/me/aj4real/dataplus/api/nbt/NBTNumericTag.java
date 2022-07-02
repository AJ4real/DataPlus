/**********************************
 Copyright (c) All Rights Reserved
 *********************************/

package me.aj4real.dataplus.api.nbt;

public interface NBTNumericTag extends NBTTag {
    long getAsLong();

    int getAsInt();

    short getAsShort();

    double getAsDouble();

    float getAsFloat();

}
