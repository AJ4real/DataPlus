/**********************************
 Copyright (c) All Rights Reserved
 *********************************/

package me.aj4real.dataplus.nms.v1_19_2;

import io.netty.buffer.Unpooled;
import me.aj4real.dataplus.DataPlusNMS;
import me.aj4real.dataplus.api.ChunkDataPacketEditor;
import me.aj4real.dataplus.api.FriendlyByteBuf;
import me.aj4real.dataplus.api.nbt.*;
import net.minecraft.core.BlockPos;
import net.minecraft.core.RegistryAccess;
import net.minecraft.nbt.*;
import net.minecraft.network.protocol.game.ClientboundLevelChunkWithLightPacket;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.SpawnerBlockEntity;
import net.minecraft.world.level.chunk.LevelChunk;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_19_R1.CraftChunk;
import org.bukkit.craftbukkit.v1_19_R1.CraftServer;
import org.bukkit.craftbukkit.v1_19_R1.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_19_R1.inventory.CraftItemStack;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.lang.reflect.InvocationTargetException;
import java.util.Optional;
import java.util.stream.Collectors;
public class DataPlusNMSImpl implements DataPlusNMS {

    public void onEnable(Plugin plugin) {
    }

    public NBTCompoundTag getDefaultLoginCodec() {
        net.minecraft.network.FriendlyByteBuf buf = new net.minecraft.network.FriendlyByteBuf(Unpooled.buffer());
        buf.writeWithCodec(RegistryAccess.NETWORK_CODEC, ((CraftServer) Bukkit.getServer()).getHandle().getServer().registryHolder);
        return (NBTCompoundTag) fromNMS(buf.readAnySizeNbt());
    }

    public ChunkDataPacketEditor getChunkDataPacketEditor(org.bukkit.World world, Object packet) throws InvocationTargetException, InstantiationException, IllegalAccessException {
        return new ChunkDataPacketEditorImpl(world, (ClientboundLevelChunkWithLightPacket) packet);
    }
    public ChunkDataPacketEditor getChunkDataPacketEditor(org.bukkit.Chunk chunk) throws InvocationTargetException, InstantiationException, IllegalAccessException {
        return new ChunkDataPacketEditorImpl(chunk);
    }

    public void writeNbt(FriendlyByteBuf buf, NBTCompoundTag nbt) {
        new net.minecraft.network.FriendlyByteBuf(buf).writeNbt((CompoundTag) toNMS(nbt));
    }

    public NBTCompoundTag readNbt(FriendlyByteBuf buf) {
        return (NBTCompoundTag) fromNMS(new net.minecraft.network.FriendlyByteBuf(buf).readNbt());
    }

    public Tag toNMS(NBTTag object) {
        if(object instanceof NBTByteArrayTag) {
            NBTByteArrayTag tag = (NBTByteArrayTag) object;
            return new ByteArrayTag(tag.get());
        }
        if(object instanceof NBTByteTag) {
            NBTByteTag tag = (NBTByteTag) object;
            return ByteTag.valueOf(tag.get());
        }
        if(object instanceof NBTCompoundTag) {
            NBTCompoundTag tag = (NBTCompoundTag) object;
            CompoundTag ret = new CompoundTag();
            for (String key : tag.keySet()) {
                ret.put(key, toNMS(tag.get(key)));
            }
            return ret;
        }
        if(object instanceof NBTDoubleTag) {
            NBTDoubleTag tag = (NBTDoubleTag) object;
            return DoubleTag.valueOf(tag.get());
        }
        if(object instanceof NBTEndTag) {
            return EndTag.INSTANCE;
        }
        if(object instanceof NBTFloatTag) {
            NBTFloatTag tag = (NBTFloatTag) object;
            return FloatTag.valueOf(tag.get());
        }
        if(object instanceof NBTIntArrayTag) {
            NBTIntArrayTag tag = (NBTIntArrayTag) object;
            return new IntArrayTag(tag.get());
        }
        if(object instanceof NBTIntTag) {
            NBTIntTag tag = (NBTIntTag) object;
            return IntTag.valueOf(tag.get());
        }
        if(object instanceof NBTListTag) {
            NBTListTag tag = (NBTListTag) object;
            ListTag ret = new ListTag();
            ret.addAll(tag.stream().map(this::toNMS).collect(Collectors.toList()));
            return ret;
        }
        if(object instanceof NBTLongArrayTag) {
            NBTLongArrayTag tag = (NBTLongArrayTag) object;
            return new LongArrayTag(tag.get());
        }
        if(object instanceof NBTLongTag) {
            NBTLongTag tag = (NBTLongTag) object;
            return LongTag.valueOf(tag.get());
        }
        if(object instanceof NBTShortTag) {
            NBTShortTag tag = (NBTShortTag) object;
            return ShortTag.valueOf(tag.get());
        }
        if(object instanceof NBTStringTag) {
            NBTStringTag tag = (NBTStringTag) object;
            return StringTag.valueOf(tag.toString());
        }
        return null;
    }
    public NBTTag fromNMS(Object object) {
        if (object instanceof ByteArrayTag) {
            ByteArrayTag tag = (ByteArrayTag) object;
            return new NBTByteArrayTag(tag.getAsByteArray());
        }
        if (object instanceof ByteTag) {
            ByteTag tag = (ByteTag) object;
            return NBTByteTag.valueOf(tag.getAsByte());
        }
        if (object instanceof CompoundTag) {
            CompoundTag tag = (CompoundTag) object;
            NBTCompoundTag ret = new NBTCompoundTag();
            for (String k : tag.getAllKeys()) {
                ret.put(k, fromNMS(tag.get(k)));
            }
            return ret;
        }
        if (object instanceof DoubleTag) {
            DoubleTag tag = (DoubleTag) object;
            return new NBTDoubleTag(tag.getAsDouble());
        }
        if (object instanceof EndTag) {
            return NBTEndTag.INSTANCE;
        }
        if (object instanceof FloatTag) {
            FloatTag tag = (FloatTag) object;
            return new NBTFloatTag(tag.getAsFloat());
        }
        if (object instanceof IntArrayTag) {
            IntArrayTag tag = (IntArrayTag) object;
            return new NBTIntArrayTag(tag.getAsIntArray());
        }
        if (object instanceof IntTag) {
            IntTag tag = (IntTag) object;
            return new NBTIntTag(tag.getAsInt());
        }
        if (object instanceof ListTag) {
            ListTag tag = (ListTag) object;
            return new NBTListTag(tag.stream().map(this::fromNMS).collect(Collectors.toList()));
        }
        if (object instanceof LongArrayTag) {
            LongArrayTag tag = (LongArrayTag) object;
            return new NBTLongArrayTag(tag.getAsLongArray());
        }
        if (object instanceof LongTag) {
            LongTag tag = (LongTag) object;
            return new NBTLongTag(tag.getAsLong());
        }
        if (object instanceof ShortTag) {
            ShortTag tag = (ShortTag) object;
            return new NBTShortTag(tag.getAsShort());
        }
        if (object instanceof StringTag) {
            StringTag tag = (StringTag) object;
            return NBTStringTag.valueOf(tag.getAsString());
        }
        return null;
    }

    public NBTCompoundTag getItemNbt(ItemStack item) {
        return (NBTCompoundTag) fromNMS(CraftItemStack.asNMSCopy(item).save(new CompoundTag()));
    }
    public ItemStack getItem(NBTCompoundTag nbt) {
        return CraftItemStack.asBukkitCopy(net.minecraft.world.item.ItemStack.of((CompoundTag) toNMS(nbt)));
    }
    public NBTCompoundTag getEntityNbt(Entity entity) {
        CompoundTag tag = new CompoundTag();
        ((CraftEntity) entity).getHandle().save(tag);
        return (NBTCompoundTag) fromNMS(tag);
    }
    public void applyEntityNbt(Entity entity, NBTCompoundTag nbt) {
        Optional<EntityType<?>> op = EntityType.by((CompoundTag) toNMS(nbt));
        if(!op.isPresent()) return;
        ((CraftEntity)entity).getHandle().load((CompoundTag) toNMS(nbt));
    }
    public NBTCompoundTag getTileEntityNbt(Location location) {
        LevelChunk chunk = ((CraftChunk)location.getChunk()).getHandle();
        BlockEntity entity = chunk.getBlockEntity(new BlockPos(location.getBlockX(), location.getBlockY(), location.getBlockZ()));
        if(entity instanceof SpawnerBlockEntity)
            return (NBTCompoundTag) fromNMS(((SpawnerBlockEntity)entity).getSpawner().save(new CompoundTag()));
        return (NBTCompoundTag) fromNMS(entity.saveWithFullMetadata());
    }
    public void putTileEntityNbt(Location location, NBTCompoundTag nbt, boolean clean) {
        if(clean) {
            LevelChunk chunk = ((CraftChunk)location.getChunk()).getHandle();
            BlockPos pos = new BlockPos(location.getBlockX(), location.getBlockY(), location.getBlockZ());
            BlockEntity entity = chunk.getBlockEntity(pos);
            if(entity instanceof SpawnerBlockEntity)
                ((SpawnerBlockEntity)entity).getSpawner().load(chunk.getLevel(), pos, (CompoundTag) toNMS(nbt));
            entity.load((CompoundTag) toNMS(nbt));
        } else {
            LevelChunk chunk = ((CraftChunk)location.getChunk()).getHandle();
            chunk.setBlockEntityNbt((CompoundTag) toNMS(nbt));
        }
    }
    public NBTListTag getItemEnchantments(ItemStack item) {
        return (NBTListTag) fromNMS(CraftItemStack.asNMSCopy(item).getEnchantmentTags());
    }
    public NBTCompoundTag getBlockNbt(Location location) {
        BlockPos pos = new BlockPos(location.getBlockX(), location.getBlockY(), location.getBlockZ());
        return (NBTCompoundTag) fromNMS(NbtUtils.writeBlockState(
                ((CraftChunk)location.getChunk()).getHandle().getBlockState(pos)));
    }
    public NBTCompoundTag getFluidNbt(Location location) {
        BlockPos pos = new BlockPos(location.getBlockX(), location.getBlockY(), location.getBlockZ());
        return (NBTCompoundTag) fromNMS(NbtUtils.writeFluidState(
                ((CraftChunk)location.getChunk()).getHandle().getFluidState(pos)));
    }
}
