/**********************************
 Copyright (c) All Rights Reserved
 *********************************/

package me.aj4real.dataplus.api.login;

import me.aj4real.dataplus.DataPlus;
import me.aj4real.dataplus.api.Version;
import me.aj4real.dataplus.api.nbt.NBTCompoundTag;
import me.aj4real.dataplus.api.nbt.NBTListTag;
import org.bukkit.NamespacedKey;

import java.util.*;

public class LoginPacketEditor {
    private static final boolean change1 = !Version.current().isHigherThan(Version.of(1, 19, 3));
    public static void include() {}
    Map<Integer, Biome> biomesById = new HashMap<>();
    Map<Integer, Dimension> dimensionsById = new HashMap<>();
    Map<NamespacedKey, Biome> biomesByName = new HashMap<>();
    Map<NamespacedKey, Dimension> dimensionsByName = new HashMap<>();
    NBTCompoundTag chat = null;

    public LoginPacketEditor() {
        this(DataPlus.nms.getDefaultLoginCodec());
    }
    public LoginPacketEditor(NBTCompoundTag nbt) {
        nbt.getCompound("minecraft:dimension_type").getList("value").forEach(NBTCompoundTag.class, (t) -> {
            NBTCompoundTag element = t.getCompound("element");
            Dimension d = new Dimension(
                    NamespacedKey.fromString(t.getString("name")),
                    t.getInt("id"),
                    element.getBoolean("piglin_safe"),
                    element.getBoolean("natural"),
                    element.getBoolean("respawn_anchor_works"),
                    element.getBoolean("has_skylight"),
                    element.getBoolean("bed_works"),
                    element.getBoolean("has_raids"),
                    element.getBoolean("has_raids"),
                    element.getBoolean("has_ceiling"),
                    element.getFloat("ambient_light"),
                    element.getInt("logical_height"),
                    element.getInt("min_y"),
                    element.getInt("height"),
                    element.getDouble("coordinate_scale"),
                    element.getString("infiniburn"),
                    element.getString("effects")
            );
            if(element.containsKey("monster_spawn_block_light_limit")) d.setMonsterSpawnBlockLightLimit(element.get("monster_spawn_block_light_limit"));
            if(element.containsKey("monster_spawn_light_level")) d.setMonsterSpawnLightLevel(element.get("monster_spawn_light_level"));
            dimensionsById.put(d.getId(), d);
            dimensionsByName.put(d.getName(), d);
        });
        nbt.getCompound("minecraft:worldgen/biome").getList("value").forEach(NBTCompoundTag.class, (t) -> {
            NBTCompoundTag element = t.getCompound("element");
            NBTCompoundTag effects = element.getCompound("effects");
            Biome b = new Biome(
                    NamespacedKey.fromString(t.getString("name")),
                    element.getFloat("temperature"),
                    element.getFloat("downfall"),
                    element.getString("precipitation"),
                    effects.getInt("sky_color"),
                    effects.getInt("water_color"),
                    effects.getInt("fog_color"),
                    effects.getInt("water_fog_color")
            );
            b.setId(t.getInt("id"));
            if(effects.containsKey("mood_sound")) {
                NBTCompoundTag moodSound = effects.getCompound("mood_sound");
                b.setOffset(moodSound.getDouble("offset"));
                b.setTickDelay(moodSound.getInt("tick_delay"));
                b.setBlockSearchExtent(moodSound.getInt("block_search_extent"));
                if(change1) b.setMoodSound(moodSound.getCompound("sound").getString("sound_id"));
                else b.setMoodSound(moodSound.getString("sound"));
            }
            if (element.containsKey("temperature_modifier"))
                b.setTemperatureModifier(element.getString("temperature_modifier"));
            if (effects.containsKey("grass_color_modifier"))
                b.setGrassColorModifier(effects.getString("grass_color_modifier"));
            if (effects.containsKey("grass_color")) b.setGrassColor(effects.getInt("grass_color"));
            if (effects.containsKey("ambient_sound")) {
                if(change1) b.setAmbientSound(effects.getCompound("ambient_sound").getString("sound_id"));
                else b.setAmbientSound(effects.get("ambient_sound").toString());
            }
            if (effects.containsKey("additions_sound")) {
                NBTCompoundTag tag = effects.getCompound("additions_sound");
                b.setAdditionalSound(tag.getCompound("sound").getString("sound_id"));
                b.setAdditionalSoundTickChance(tag.getDouble("tick_chance"));
            }
            if (effects.containsKey("foliage_color")) b.setFoliageColor(effects.getInt("foliage_color"));
            if (effects.containsKey("particle")) b.setParticle(effects.getCompound("particle"));
            if (element.containsKey("category")) b.setCategory(element.getString("category"));
            biomesById.put(b.getId(), b);
            biomesByName.put(b.getName(), b);
        });
        if(nbt.containsKey("minecraft:chat_type")) this.chat = nbt.getCompound("minecraft:chat_type");
    }
    public NBTCompoundTag build() {
        NBTCompoundTag ret = new NBTCompoundTag();
        NBTCompoundTag dimensions = new NBTCompoundTag();
        dimensions.putString("type", "minecraft:dimension_type");
        NBTListTag dimensionList = new NBTListTag();
        this.dimensionsById.values().forEach((d) -> {
            NBTCompoundTag ret2 = new NBTCompoundTag();
            ret2.putString("name", d.getName().toString());
            ret2.putInt("id", d.getId());
            NBTCompoundTag element = new NBTCompoundTag();
            element.putBoolean("piglin_safe", d.isPiglinSafe());
            element.putBoolean("natural", d.isNatural());
            element.putFloat("ambient_light", d.getAmbientLight());
            element.putString("infiniburn", d.getInfiniburn());
            element.putBoolean("respawn_anchor_works", d.doesRespawnAnchorWork());
            element.putBoolean("has_skylight", d.hasSkylight());
            element.putBoolean("bed_works", d.doesBedWork());
            element.putString("effects", d.getEffects());
            element.putBoolean("has_raids", d.hasRaids());
            element.putInt("logical_height", d.getLogicalHeight());
            element.putDouble("coordinate_scale", d.getCoordinateScale());
            element.putInt("min_y", d.getMinY());
            element.putBoolean("ultrawarm", d.ultrawarm());
            element.putBoolean("has_ceiling", d.hasCeiling());
            element.putInt("height", d.getHeight());
            if (d.getMonsterSpawnBlockLightLimit() != null)
                element.put("monster_spawn_block_light_limit", d.getMonsterSpawnBlockLightLimit());
            if (d.getMonsterSpawnLightLevel() != null)
                element.put("monster_spawn_light_level", d.getMonsterSpawnLightLevel());
            ret2.putCompound("element", element);
            dimensionList.addCompound(ret2);
        });
        dimensions.putList("value", dimensionList);
        ret.putCompound("minecraft:dimension_type", dimensions);
        NBTCompoundTag biomes = new NBTCompoundTag();
        biomes.putString("type", "minecraft:worldgen/biome");
        NBTListTag biomeList = new NBTListTag();
        this.biomesById.values().forEach((d) -> {
            NBTCompoundTag ret2 = new NBTCompoundTag();
            ret2.putString("name", d.getName().toString());
            ret2.putInt("id", d.getId());
            NBTCompoundTag element = new NBTCompoundTag();
            element.putString("precipitation", d.getPrecipitation().toLowerCase());
            element.putFloat("temperature", (float) d.getTemperature());
            element.putFloat("downfall", (float) d.getDownfall());
            NBTCompoundTag effects = new NBTCompoundTag();
            effects.putInt("sky_color", d.getSkyColor());
            effects.putInt("water_color", d.getWaterColor());
            effects.putInt("fog_color", d.getFogColor());
            effects.putInt("water_fog_color", d.getWaterFogColor());
            NBTCompoundTag moodSound = new NBTCompoundTag();
            if(d.getOffset().isPresent())
                moodSound.putDouble("offset", d.getOffset().get());
            if (d.getTickDelay().isPresent())
                moodSound.putInt("tick_delay", d.getTickDelay().get());
            if (d.getBlockSearchExtent().isPresent())
                moodSound.putInt("block_search_extent", d.getBlockSearchExtent().get());
            if (d.getCategory().isPresent()) element.putString("category", d.getCategory().get());
            if (d.getTemperatureModifier().isPresent())
                element.putString("temperature_modifier", d.getTemperatureModifier().get());
            if (d.getGrassColorModifier().isPresent())
                effects.putString("grass_color_modifier", d.getGrassColorModifier().get());
            if (d.getGrassColor().isPresent()) effects.putInt("grass_color", d.getGrassColor().get());
            if (d.getAmbientSound().isPresent()) effects.putString("ambient_sound", d.getAmbientSound().get());
            if (d.getAmbientSound().isPresent()) {
                if(change1) effects.putString("ambient_sound", d.getAmbientSound().get());
                else {
                    NBTCompoundTag tag = new NBTCompoundTag();
                    tag.putString("sound_id", d.getAmbientSound().get());
                    effects.putCompound("ambient_sound", tag);
                }
            }
            {
                Optional<Double> tickChance = d.getAdditionalSoundTickChance();
                Optional<String> sound = d.getAdditionalSound();
                if(tickChance.isPresent() && sound.isPresent()) {
                    NBTCompoundTag tag = new NBTCompoundTag();
                    tag.putDouble("tick_chance", tickChance.get());
                    NBTCompoundTag s = new NBTCompoundTag();
                    s.putString("sound_id", sound.get());
                    tag.putCompound("sound", s);
                    effects.putCompound("additions_sound", tag);
                }
            }
            if (d.getFoliageColor().isPresent()) effects.putInt("foliage_color", d.getFoliageColor().get());
            if (d.getParticle().isPresent()) effects.putCompound("particle", d.getParticle().get());
            effects.putCompound("mood_sound", moodSound);
            element.putCompound("effects", effects);
            ret2.putCompound("element", element);
            biomeList.addCompound(ret2);
        });
        biomes.putList("value", biomeList);
        ret.putCompound("minecraft:worldgen/biome", biomes);
        if(this.chat != null) ret.putCompound("minecraft:chat_type", this.chat);
        return ret;
    }
    public void addBiome(Biome biome) {
        biomesById.put(biome.getId(), biome);
        biomesByName.put(biome.getName(), biome);
    }
    public void addDimension(Dimension dimension) {
        dimensionsById.put(dimension.getId(), dimension);
        dimensionsByName.put(dimension.getName(), dimension);
    }
    public Biome getBiomeByName(NamespacedKey name) {
        return biomesByName.get(name);
    }
    public Dimension getDimensionByName(NamespacedKey name) {
        return dimensionsByName.get(name);
    }
    public Biome getBiomeById(int id) {
        return biomesById.get(id);
    }
    public Dimension getDimensionById(int id) {
        return dimensionsById.get(id);
    }
    public Set<Biome> getBiomes() {
        return Collections.unmodifiableSet(new HashSet<>(biomesById.values()));
    }
    public Set<Dimension> getDimensions() {
        return Collections.unmodifiableSet(new HashSet<>(dimensionsById.values()));
    }

}
