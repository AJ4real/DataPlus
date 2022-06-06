/**********************************
 Copyright (c) All Rights Reserved
 *********************************/

package me.aj4real.justanothernbtapi.nms.v1_17_R1;

import me.aj4real.justanothernbtapi.NMS;
import me.aj4real.justanothernbtapi.api.nbt.*;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.*;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.SpawnerBlockEntity;
import net.minecraft.world.level.chunk.LevelChunk;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_17_R1.CraftChunk;
import org.bukkit.craftbukkit.v1_17_R1.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_17_R1.inventory.CraftItemStack;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.util.Optional;
import java.util.stream.Collectors;

public class NMSImpl implements NMS {

    public void onEnable(Plugin plugin) {
    }

    private Tag convert(NBTTag object) {
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
                ret.put(key, convert(tag.get(key)));
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
            ret.addAll(tag.get().stream().map(this::convert).collect(Collectors.toList()));
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
    private NBTTag convert(Tag object) {
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
                ret.put(k, convert(tag.get(k)));
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
            return new NBTListTag(tag.stream().map(this::convert).collect(Collectors.toList()));
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
        return (NBTCompoundTag) convert(CraftItemStack.asNMSCopy(item).save(new CompoundTag()));
    }
    public ItemStack getItem(NBTCompoundTag nbt) {
        return CraftItemStack.asBukkitCopy(net.minecraft.world.item.ItemStack.of((CompoundTag) convert(nbt)));
    }
    public NBTCompoundTag getEntityNbt(Entity entity) {
        CompoundTag tag = new CompoundTag();
        ((CraftEntity) entity).getHandle().save(tag);
        return (NBTCompoundTag) convert(tag);
    }
    public void applyEntityNbt(Entity entity, NBTCompoundTag nbt) {
        Optional<EntityType<?>> op = EntityType.by((CompoundTag) convert(nbt));
        if(!op.isPresent()) {
            // nbt does not represent a valid entity
            return;
        }
        ((CraftEntity)entity).getHandle().load((CompoundTag) convert(nbt));
    }
    public NBTCompoundTag getTileEntityNbt(Location location) {
        LevelChunk chunk = ((CraftChunk)location.getChunk()).getHandle();
        BlockPos pos = new BlockPos(location.getBlockX(), location.getBlockY(), location.getBlockZ());
        BlockEntity entity = chunk.getBlockEntity(pos);
        if(entity instanceof SpawnerBlockEntity)
            return (NBTCompoundTag) convert(((SpawnerBlockEntity)entity).getSpawner().save(chunk.getLevel(), pos, new CompoundTag()));
        return (NBTCompoundTag) convert(entity.save(new CompoundTag()));
    }
    public void putTileEntityNbt(Location location, NBTCompoundTag nbt, boolean clean) {
        if(clean) {
            LevelChunk chunk = ((CraftChunk)location.getChunk()).getHandle();
            BlockPos pos = new BlockPos(location.getBlockX(), location.getBlockY(), location.getBlockZ());
            BlockEntity entity = chunk.getBlockEntity(pos);
            if(entity instanceof SpawnerBlockEntity)
                ((SpawnerBlockEntity)entity).getSpawner().load(chunk.getLevel(), pos, (CompoundTag) convert(nbt));
            entity.load((CompoundTag) convert(nbt));
        } else {
            LevelChunk chunk = ((CraftChunk)location.getChunk()).getHandle();
            chunk.setBlockEntityNbt((CompoundTag) convert(nbt));
        }
    }
}