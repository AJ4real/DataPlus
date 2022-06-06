/**********************************
 Copyright (c) All Rights Reserved
 *********************************/

package me.aj4real.justanothernbtapi.api;

import me.aj4real.justanothernbtapi.api.nbt.NBTCompoundTag;

import java.util.UUID;

public class EntityNBT {

    private final NBTCompoundTag tag;

    private UUID uuid;

    public EntityNBT(NBTCompoundTag tag) {
        this.tag = tag;
    }

    public void setUUID(UUID uuid) {
        tag.putString("UUID", uuid.toString());
    }
}
