/**********************************
 Copyright (c) All Rights Reserved
 *********************************/

package me.aj4real.dataplus.nms.v1_16_R3;

import me.aj4real.dataplus.DataPlusNMS;
import me.aj4real.dataplus.api.ChunkDataPacketEditor;
import me.aj4real.dataplus.api.FriendlyByteBuf;
import me.aj4real.dataplus.api.nbt.*;
import net.minecraft.server.v1_16_R3.*;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_16_R3.CraftChunk;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_16_R3.inventory.CraftItemStack;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.util.Optional;
import java.util.stream.Collectors;

public class DataPlusNMSImpl implements DataPlusNMS {
    public void onEnable(Plugin plugin) {
    }

    public NBTCompoundTag getDefaultLoginCodec() {
        throw new UnsupportedOperationException("Login Codec Data is only available on v1_17_R1 and higher.");
    }

    public ChunkDataPacketEditor getChunkDataPacketEditor(org.bukkit.World world, Object packet) {
        throw new UnsupportedOperationException("Chunk Data Packet Editors are only available on version " + ChunkDataPacketEditor.SupportedVersions.values()[0].name() + " and higher.");
    }
    public ChunkDataPacketEditor getChunkDataPacketEditor(org.bukkit.Chunk chunk) {
        throw new UnsupportedOperationException("Chunk Data Packet Editors are only available on version " + ChunkDataPacketEditor.SupportedVersions.values()[0].name() + " and higher.");
    }

    public void writeNbt(FriendlyByteBuf buf, NBTCompoundTag nbt) {
        new PacketDataSerializer(buf).a((NBTTagCompound) toNMS(nbt));
    }

    public NBTCompoundTag readNbt(FriendlyByteBuf buf) {
        return (NBTCompoundTag) fromNMS(new PacketDataSerializer(buf).m());
    }

    public NBTBase toNMS(NBTTag object) {
        if(object instanceof NBTByteArrayTag) {
            NBTByteArrayTag tag = (NBTByteArrayTag) object;
            return new NBTTagByteArray(tag.get());
        }
        if(object instanceof NBTByteTag) {
            NBTByteTag tag = (NBTByteTag) object;
            return NBTTagByte.a(tag.get());
        }
        if(object instanceof NBTCompoundTag) {
            NBTCompoundTag tag = (NBTCompoundTag) object;
            net.minecraft.server.v1_16_R3.NBTTagCompound ret = new net.minecraft.server.v1_16_R3.NBTTagCompound();
            for (String key : tag.keySet()) {
                ret.set(key, toNMS(tag.get(key)));
            }
            return ret;
        }
        if(object instanceof NBTDoubleTag) {
            NBTDoubleTag tag = (NBTDoubleTag) object;
            return NBTTagDouble.a(tag.get());
        }
        if(object instanceof NBTEndTag) {
            return NBTTagEnd.b;
        }
        if(object instanceof NBTFloatTag) {
            NBTFloatTag tag = (NBTFloatTag) object;
            return NBTTagFloat.a(tag.get());
        }
        if(object instanceof NBTIntArrayTag) {
            NBTIntArrayTag tag = (NBTIntArrayTag) object;
            return new NBTTagIntArray(tag.get());
        }
        if(object instanceof NBTIntTag) {
            NBTIntTag tag = (NBTIntTag) object;
            return NBTTagInt.a(tag.get());
        }
        if(object instanceof NBTListTag) {
            NBTListTag tag = (NBTListTag) object;
            NBTTagList ret = new NBTTagList();
            ret.addAll(tag.stream().map(this::toNMS).collect(Collectors.toList()));
            return ret;
        }
        if(object instanceof NBTLongArrayTag) {
            NBTLongArrayTag tag = (NBTLongArrayTag) object;
            return new NBTTagLongArray(tag.get());
        }
        if(object instanceof NBTLongTag) {
            NBTLongTag tag = (NBTLongTag) object;
            return NBTTagLong.a(tag.get());
        }
        if(object instanceof NBTShortTag) {
            NBTShortTag tag = (NBTShortTag) object;
            return NBTTagShort.a(tag.get());
        }
        if(object instanceof NBTStringTag) {
            NBTStringTag tag = (NBTStringTag) object;
            return NBTTagString.a(tag.toString());
        }
        return null;
    }
    public NBTTag fromNMS(Object object) {
        if (object instanceof NBTTagByteArray) {
            NBTTagByteArray tag = (NBTTagByteArray) object;
            return new NBTByteArrayTag(tag.getBytes());
        }
        if (object instanceof NBTTagByte) {
            NBTTagByte tag = (NBTTagByte) object;
            return NBTByteTag.valueOf(tag.asByte());
        }
        if (object instanceof NBTTagCompound) {
            NBTTagCompound tag = (NBTTagCompound) object;
            NBTCompoundTag ret = new NBTCompoundTag();
            for (String k : tag.getKeys()) {
                ret.put(k, fromNMS(tag.get(k)));
            }
            return ret;
        }
        if (object instanceof NBTTagDouble) {
            NBTTagDouble tag = (NBTTagDouble) object;
            return new NBTDoubleTag(tag.asDouble());
        }
        if (object instanceof NBTTagEnd) {
            return NBTEndTag.INSTANCE;
        }
        if (object instanceof NBTTagFloat) {
            NBTTagFloat tag = (NBTTagFloat) object;
            return new NBTFloatTag(tag.asFloat());
        }
        if (object instanceof NBTTagIntArray) {
            NBTTagIntArray tag = (NBTTagIntArray) object;
            return new NBTIntArrayTag(tag.getInts());
        }
        if (object instanceof NBTTagInt) {
            NBTTagInt tag = (NBTTagInt) object;
            return new NBTIntTag(tag.asInt());
        }
        if (object instanceof NBTTagList) {
            NBTTagList tag = (NBTTagList) object;
            return new NBTListTag(tag.stream().map(this::fromNMS).collect(Collectors.toList()));
        }
        if (object instanceof NBTTagLongArray) {
            NBTTagLongArray tag = (NBTTagLongArray) object;
            return new NBTLongArrayTag(tag.getLongs());
        }
        if (object instanceof NBTTagLong) {
            NBTTagLong tag = (NBTTagLong) object;
            return new NBTLongTag(tag.asLong());
        }
        if (object instanceof NBTTagShort) {
            NBTTagShort tag = (NBTTagShort) object;
            return new NBTShortTag(tag.asShort());
        }
        if (object instanceof NBTTagString) {
            NBTTagString tag = (NBTTagString) object;
            return NBTStringTag.valueOf(tag.asString());
        }
        return null;
    }

    public NBTCompoundTag getItemNbt(ItemStack item) {
        return (NBTCompoundTag) fromNMS(CraftItemStack.asNMSCopy(item).save(new NBTTagCompound()));
    }
    public ItemStack getItem(NBTCompoundTag nbt) {
        return CraftItemStack.asBukkitCopy(net.minecraft.server.v1_16_R3.ItemStack.a((NBTTagCompound) toNMS(nbt)));
    }
    public NBTCompoundTag getEntityNbt(Entity entity) {
        NBTTagCompound tag = new NBTTagCompound();
        ((CraftEntity) entity).getHandle().save(tag);
        return (NBTCompoundTag) fromNMS(tag);
    }
    public void applyEntityNbt(Entity entity, NBTCompoundTag nbt) {
        Optional<EntityTypes<?>> op = EntityTypes.a((NBTTagCompound) toNMS(nbt));
        if(!op.isPresent()) {
            return;
        }
        ((CraftEntity)entity).getHandle().load((NBTTagCompound) toNMS(nbt));
    }
    public NBTCompoundTag getTileEntityNbt(Location location) {
        Chunk chunk = ((CraftChunk)location.getChunk()).getHandle();
        BlockPosition pos = new BlockPosition(location.getBlockX(), location.getBlockY(), location.getBlockZ());
        TileEntity entity = chunk.getTileEntity(pos);
        if(entity instanceof TileEntityMobSpawner)
            return (NBTCompoundTag) fromNMS(((TileEntityMobSpawner)entity).getSpawner().b(new NBTTagCompound()));
        return (NBTCompoundTag) fromNMS(entity.save(new NBTTagCompound()));
    }
    public void putTileEntityNbt(Location location, NBTCompoundTag nbt, boolean clean) {
        if(clean) {
            Chunk chunk = ((CraftChunk)location.getChunk()).getHandle();
            BlockPosition pos = new BlockPosition(location.getBlockX(), location.getBlockY(), location.getBlockZ());
            TileEntity entity = chunk.tileEntities.get(pos);
            if(entity instanceof TileEntityMobSpawner)
                ((TileEntityMobSpawner)entity).getSpawner().a((NBTTagCompound) toNMS(nbt));
            entity.load(entity.getBlock(), (NBTTagCompound) toNMS(nbt));
        } else {
            Chunk chunk = ((CraftChunk)location.getChunk()).getHandle();
            chunk.a((NBTTagCompound) toNMS(nbt));
        }
    }
}
