/**********************************
 Copyright (c) All Rights Reserved
 *********************************/

package me.aj4real.dataplus.api.serializable;

import me.aj4real.dataplus.api.nbt.*;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;

public class YAMLSerializer {
    public YAMLSerializer() {
    }
    public YamlConfiguration writeWithComments(NBTCompoundTag tag) {
        return (YamlConfiguration) writeWithComments0(tag);
    }
    private Object writeWithComments0(NBTTag tag) {
        if(tag instanceof NBTCompoundTag) {
            NBTCompoundTag nbt = (NBTCompoundTag) tag;
            YamlConfiguration c = new YamlConfiguration();
            nbt.keySet().forEach((s) -> {
                c.set(s, writeWithComments0(nbt.get(s)));
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
                    c.set("list_" + i.get(), writeWithComments0(t));
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
    public YamlConfiguration write(NBTCompoundTag tag) {
        return (YamlConfiguration) write0(tag);
    }
    private Object write0(NBTTag tag) {
        if(tag instanceof NBTCompoundTag) {
            NBTCompoundTag nbt = (NBTCompoundTag) tag;
            YamlConfiguration c = new YamlConfiguration();
            nbt.keySet().forEach((s) -> {
                c.set(s, write0(nbt.get(s)));
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
                    list.add(write0(t));
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
