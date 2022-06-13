/**********************************
 Copyright (c) All Rights Reserved
 *********************************/

package me.aj4real.justanothernbtapi.api.serializable;

import me.aj4real.justanothernbtapi.api.nbt.*;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class YAMLSerializer {
    public YAMLSerializer() {
    }
    public Object writeWithComments(NBTTag tag) {
        if(tag instanceof NBTCompoundTag) {
            NBTCompoundTag nbt = (NBTCompoundTag) tag;
            YamlConfiguration c = new YamlConfiguration();
            nbt.keySet().forEach((s) -> {
                c.set(s, writeWithComments(nbt.get(s)));
                c.setInlineComments(s, Collections.singletonList(nbt.get(s).getClass().getSimpleName()));
            });
            return c;
        } else if(tag instanceof NBTNumericTag) {
            return tag instanceof NBTDoubleTag ? ((NBTNumericTag)tag).getAsDouble() : ((NBTNumericTag)tag).getAsLong();
        } else if(tag instanceof NBTStringTag) {
            return ((NBTStringTag)tag).toString();
        } else if(tag instanceof NBTCollectionTag) {
            if(tag instanceof NBTListTag) {
                YamlConfiguration c = new YamlConfiguration();
                AtomicInteger i = new AtomicInteger();
                ((NBTListTag)tag).forEach((t) -> {
                    c.set("list_" + i.get(), writeWithComments(t));
                    c.setInlineComments("list_" + i.getAndIncrement(), Collections.singletonList(t.getClass().getSimpleName()));
                });
                return c;
            } else if(tag instanceof NBTByteArrayTag) {
                return ((NBTByteArrayTag)tag).get();
            } else if(tag instanceof NBTIntArrayTag) {
                return ((NBTIntArrayTag)tag).get();
            } else if(tag instanceof NBTLongArrayTag) {
                return ((NBTLongArrayTag)tag).get();
            }
        } else {
            Bukkit.getConsoleSender().sendMessage(this.getClass().getCanonicalName() + " : " + tag.getClass().getSimpleName());
        }
        return null;
    }
    public Object write(NBTTag tag) {
        if(tag instanceof NBTCompoundTag) {
            NBTCompoundTag nbt = (NBTCompoundTag) tag;
            YamlConfiguration c = new YamlConfiguration();
            nbt.keySet().forEach((s) -> {
                c.set(s, write(nbt.get(s)));
                c.setInlineComments(s, Collections.singletonList(nbt.get(s).getClass().getSimpleName()));
            });
            return c;
        } else if(tag instanceof NBTNumericTag) {
            return tag instanceof NBTDoubleTag ? ((NBTNumericTag)tag).getAsDouble() : ((NBTNumericTag)tag).getAsLong();
        } else if(tag instanceof NBTStringTag) {
            return ((NBTStringTag)tag).toString();
        } else if(tag instanceof NBTCollectionTag) {
            if(tag instanceof NBTListTag) {
                List<Object> list = new ArrayList<>();
                ((NBTListTag)tag).forEach((t) -> {
                    list.add(write(t));
                });
                return list;
            } else if(tag instanceof NBTByteArrayTag) {
                return ((NBTByteArrayTag)tag).get();
            } else if(tag instanceof NBTIntArrayTag) {
                return ((NBTIntArrayTag)tag).get();
            } else if(tag instanceof NBTLongArrayTag) {
                return ((NBTLongArrayTag)tag).get();
            }
        } else {
            Bukkit.getConsoleSender().sendMessage(this.getClass().getCanonicalName() + " : " + tag.getClass().getSimpleName());
        }
        return null;
    }
}
