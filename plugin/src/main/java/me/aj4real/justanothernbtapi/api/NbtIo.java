/**********************************
 Copyright (c) All Rights Reserved
 *********************************/

package me.aj4real.justanothernbtapi.api;

import me.aj4real.justanothernbtapi.api.nbt.*;
import org.bukkit.Bukkit;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class NbtIo {
    private static final Map<Byte, Function<FriendlyByteBuf, NBTTag>> tagGetters = new HashMap<>();
    static {
        tagGetters.put((byte) 0, (i) -> NBTEndTag.INSTANCE);
        tagGetters.put((byte) 1, NBTByteTag::read);
        tagGetters.put((byte) 2, NBTShortTag::read);
        tagGetters.put((byte) 3, NBTIntTag::read);
        tagGetters.put((byte) 4, NBTLongTag::read);
        tagGetters.put((byte) 5, NBTFloatTag::read);
        tagGetters.put((byte) 6, NBTDoubleTag::read);
        tagGetters.put((byte) 7, NBTByteArrayTag::read);
        tagGetters.put((byte) 8, NBTStringTag::read);
        tagGetters.put((byte) 9, NBTListTag::read);
        tagGetters.put((byte) 10, NBTCompoundTag::read);
        tagGetters.put((byte) 11, NBTIntArrayTag::read);
        tagGetters.put((byte) 12, NBTLongArrayTag::read);
    }
    public static NBTTag parseNbt(FriendlyByteBuf buf, boolean unnamed) {
        byte type = buf.readByte();
        buf.readUtf();
        return tagGetters.get(type).apply(buf);
    }
}
