/**********************************
 Copyright (c) All Rights Reserved
 *********************************/

package me.aj4real.dataplus.api.login;

import me.aj4real.dataplus.api.nbt.NBTCompoundTag;
import org.bukkit.NamespacedKey;
import org.bukkit.persistence.PersistentDataType;

import javax.annotation.OverridingMethodsMustInvokeSuper;
import java.util.Arrays;
import java.util.Optional;

public class Biome implements Cloneable {
    private NamespacedKey name;
    private int id;
    private boolean vanilla;

    private boolean modFogColor = false,
            modFoliageColor = false,
            modGrassColor = false,
            modParticle = false,
            modSkyColor = false,
            modWaterColor = false,
            modWaterFogColor = false;

    private Biome original;

    //element
    private double temperature, downfall;
    private String precipitation;

    //effect
    private int skyColor, waterColor, fogColor, waterFogColor;

    //mood_sound
    private Optional<Double> offset = Optional.empty();
    private Optional<Integer> tickDelay = Optional.empty(), blockSearchExtent = Optional.empty();
    private Optional<String> sound = Optional.empty();

    //optional
    private Optional<String> temperatureModifier = Optional.empty(), grassColorModifier = Optional.empty(), ambientSound = Optional.empty(), category = Optional.empty();
    private Optional<Integer> grassColor = Optional.empty(), foliageColor = Optional.empty();
    private Optional<NBTCompoundTag> particle = Optional.empty();

    public Biome(
            NamespacedKey name,
            double temperature,
            double downfall,
            String precipitation,
            int skyColor,
            int waterColor,
            int fogColor,
            int waterFogColor
    ) {
        this(name, temperature, downfall, precipitation, skyColor, waterColor, fogColor, waterFogColor, true);
    }

    private Biome(
            NamespacedKey name,
            double temperature,
            double downfall,
            String precipitation,
            int skyColor,
            int waterColor,
            int fogColor,
            int waterFogColor,
            boolean clone
    ) {
        this.name = name;
        this.temperature = temperature;
        this.downfall = downfall;
        this.precipitation = precipitation;
        this.skyColor = skyColor;
        this.waterColor = waterColor;
        this.fogColor = fogColor;
        this.waterFogColor = waterFogColor;
        try {
            org.bukkit.block.Biome b = org.bukkit.block.Biome.valueOf(name.getKey());
            this.vanilla = b != org.bukkit.block.Biome.CUSTOM;
        } catch (Exception e) {
            this.vanilla = false;
        }
        if(clone) this.original = this.clone();
    }

    public boolean isMod() {
        return modFogColor || modFoliageColor || modGrassColor || modParticle || modSkyColor || modWaterColor || modWaterFogColor;
    }

    public void setId(int value) {
        this.id = value;
    }

    public void setName(NamespacedKey value) {
        this.name = value;
    }

    public void setTickDelay(Integer value) {
        this.tickDelay = Optional.ofNullable(value);
    }

    public void setBlockSearchExtent(int value) {
        this.blockSearchExtent = Optional.ofNullable(value);
    }

    public void setOffset(Double value) {
        this.offset = Optional.ofNullable(value);
    }

    public void setWaterFogColor(Integer value) {
        if(value == null) {
            this.waterFogColor = original.waterFogColor;
            this.modWaterFogColor = false;
        } else {
            this.waterFogColor = value;
            this.modWaterFogColor = true;
        }
    }

    public void setWaterFogColor(int r, int g, int b) {
        setWaterFogColor(255 << 24 | r << 16 | g << 8 | b);
    }

    public void setFogColor(Integer value) {
        if(value == null) {
            this.fogColor = original.fogColor;
            this.modFogColor = false;
        } else {
            this.fogColor = value;
            this.modFogColor = true;
        }
    }

    public void setFogColor(int r, int g, int b) {
        setFogColor(255 << 24 | r << 16 | g << 8 | b);
    }

    public void setWaterColor(Integer value) {
        if(value == null) {
            this.waterColor = original.waterColor;
            this.modWaterColor = false;
        } else {
            this.waterColor = value;
            this.modWaterColor = true;
        }
    }

    public void setWaterColor(int r, int g, int b) {
        setWaterColor(255 << 24 | r << 16 | g << 8 | b);
    }

    public void setSkyColor(Integer value) {
        if(value == null) {
            this.skyColor = original.skyColor;
            this.modSkyColor = false;
        } else {
            this.skyColor = value;
            this.modSkyColor = true;
        }
    }

    public void setSkyColor(int r, int g, int b) {
        setSkyColor(255 << 24 | r << 16 | g << 8 | b);
    }

    public void setCategory(String value) {
        this.category = Optional.ofNullable(value);
    }

    public void setPrecipitation(String value) {
        if(value == null) this.precipitation = original.precipitation;
        else this.precipitation = value;
    }

    public void setDownfall(Float value) {
        this.downfall = value;
    }

    public void setTemperature(Float value) {
        this.temperature = value;
    }

    public void setSound(String value) {
        this.sound = Optional.ofNullable(value);
    }

    public void setAmbientSound(String ambientSound) {
        this.ambientSound = Optional.ofNullable(ambientSound);
    }

    public void setDownfall(Double downfall) {
        this.downfall = downfall;
    }

    public void setFoliageColor(Integer value) {
        if(value == null) {
            this.foliageColor = original.foliageColor;
            this.modFoliageColor = false;
        } else {
            this.foliageColor = Optional.of(value);
            this.modFoliageColor = true;
        }
    }

    public void setFoliageColor(int r, int g, int b) {
        setFoliageColor(255 << 24 | r << 16 | g << 8 | b);
    }

    public void setGrassColor(Integer value) {
        if(value == null) {
            this.grassColor = original.grassColor;
            this.modGrassColor = false;
        } else {
            this.grassColor = Optional.of(value);
            this.modGrassColor = true;
        }
    }

    public void setGrassColor(int r, int g, int b) {
        setGrassColor(255 << 24 | r << 16 | g << 8 | b);
    }

    public void setGrassColorModifier(String grassColorModifier) {
        this.grassColorModifier = Optional.ofNullable(grassColorModifier);
    }

    public void setParticle(NBTCompoundTag particle) {
        this.particle = Optional.ofNullable(particle);
        if(particle == null) {
            this.modParticle = false;
        } else {
            this.modParticle = true;
        }
    }

    public void setTemperature(Double temperature) {
        this.temperature = temperature;
    }

    public void setTemperatureModifier(String temperatureModifier) {
        this.temperatureModifier = Optional.ofNullable(temperatureModifier);
    }

    public boolean isVanilla() {
        return vanilla;
    }

    public NamespacedKey getName() {
        return this.name;
    }

    public double getDownfall() {
        return this.downfall;
    }

    public double getTemperature() {
        return this.temperature;
    }

    public Optional<Double> getOffset() {
        return this.offset;
    }

    public Optional<String> getCategory() {
        return this.category;
    }

    public String getPrecipitation() {
        return this.precipitation;
    }

    public int getId() {
        return this.id;
    }

    public int getSkyColor() {
        return this.skyColor;
    }

    public int getWaterColor() {
        return this.waterColor;
    }

    public int getFogColor() {
        return this.fogColor;
    }

    public int getWaterFogColor() {
        return this.waterFogColor;
    }

    public Optional<Integer> getTickDelay() {
        return this.tickDelay;
    }

    public Optional<Integer> getBlockSearchExtent() {
        return this.blockSearchExtent;
    }

    public Optional<String> getSound() {
        return this.sound;
    }

    public Optional<Integer> getFoliageColor() {
        return foliageColor;
    }

    public Optional<Integer> getGrassColor() {
        return grassColor;
    }

    public Optional<NBTCompoundTag> getParticle() {
        return particle;
    }

    public Optional<String> getAmbientSound() {
        return ambientSound;
    }

    public Optional<String> getGrassColorModifier() {
        return grassColorModifier;
    }

    public Optional<String> getTemperatureModifier() {
        return temperatureModifier;
    }

    @Override
    public Biome clone() {
        Biome b = new Biome(
                this.name,
                this.temperature,
                this.downfall,
                this.precipitation,
                this.skyColor,
                this.waterColor,
                this.fogColor,
                this.waterFogColor,
                false
        );
        b.id = this.id;
        b.offset = this.offset;
        b.tickDelay = this.tickDelay;
        b.sound = this.sound;
        b.foliageColor = this.foliageColor;
        b.grassColor = this.grassColor;
        b.ambientSound = this.ambientSound;
        b.grassColorModifier = this.grassColorModifier;
        b.temperatureModifier = this.temperatureModifier;
        b.blockSearchExtent = this.blockSearchExtent;
        b.particle = this.particle;
        return b;
    }
}
