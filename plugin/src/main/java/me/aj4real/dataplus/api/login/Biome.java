/**********************************
 Copyright (c) All Rights Reserved
 *********************************/

package me.aj4real.dataplus.api.login;

import me.aj4real.dataplus.api.nbt.NBTCompoundTag;
import org.bukkit.NamespacedKey;
import org.bukkit.persistence.PersistentDataType;

import java.util.Arrays;
import java.util.Optional;

public class Biome implements Cloneable {
    private NamespacedKey name;
    private int id;
    private boolean vanilla;

    //element
    private double temperature = 0, downfall = 0;
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
        this.name = name;
        this.temperature = temperature;
        this.downfall = downfall;
        this.precipitation = precipitation;
        this.skyColor = skyColor;
        this.waterColor = waterColor;
        this.fogColor = fogColor;
        this.waterFogColor = waterFogColor;
//        this.vanilla = Arrays.stream(org.bukkit.block.Biome.values()).map(org.bukkit.block.Biome::getKey).filter(name::equals).count() != 0;
//        this.vanilla = name.getNamespace().equalsIgnoreCase("minecraft") && this.getClass() == Biome.class;
        try {
            org.bukkit.block.Biome b = org.bukkit.block.Biome.valueOf(name.getKey());
            this.vanilla = b != org.bukkit.block.Biome.CUSTOM;
        } catch (Exception e) {
            this.vanilla = false;
        }
    }

    public void setId(int value) {
        this.id = value;
    }

    public void setName(NamespacedKey value) {
        this.name = value;
    }

    public void setTickDelay(int value) {
        this.tickDelay = Optional.ofNullable(value);
    }

    public void setBlockSearchExtent(int value) {
        this.blockSearchExtent = Optional.ofNullable(value);
    }

    public void setOffset(double value) {
        this.offset = Optional.ofNullable(value);
    }

    public void setWaterFogColor(int value) {
        this.waterFogColor = value;
    }

    public void setWaterFogColor(int r, int g, int b) {
        setWaterFogColor(255 << 24 | r << 16 | g << 8 | b);
    }

    public void setFogColor(int value) {
        this.fogColor = value;
    }

    public void setFogColor(int r, int g, int b) {
        setFogColor(255 << 24 | r << 16 | g << 8 | b);
    }

    public void setWaterColor(int value) {
        this.waterColor = value;
    }

    public void setWaterColor(int r, int g, int b) {
        setWaterColor(255 << 24 | r << 16 | g << 8 | b);
    }

    public void setSkyColor(int value) {
        this.skyColor = value;
    }

    public void setSkyColor(int r, int g, int b) {
        setSkyColor(255 << 24 | r << 16 | g << 8 | b);
    }

    public void setCategory(String value) {
        this.category = Optional.ofNullable(value);
    }

    public void setPrecipitation(String value) {
        this.precipitation = value;
    }

    public void setDownfall(float value) {
        this.downfall = value;
    }

    public void setTemperature(float value) {
        this.temperature = value;
    }

    public void setSound(String value) {
        this.sound = Optional.ofNullable(value);
    }

    public void setAmbientSound(String ambientSound) {
        this.ambientSound = Optional.ofNullable(ambientSound);
    }

    public void setDownfall(double downfall) {
        this.downfall = downfall;
    }

    public void setFoliageColor(int foliageColor) {
        this.foliageColor = Optional.ofNullable(foliageColor);
    }

    public void setFoliageColor(int r, int g, int b) {
        setFoliageColor(255 << 24 | r << 16 | g << 8 | b);
    }

    public void setGrassColor(int grassColor) {
        this.grassColor = Optional.ofNullable(grassColor);
    }

    public void setGrassColor(int r, int g, int b) {
        setGrassColor(255 << 24 | r << 16 | g << 8 | b);
    }

    public void setGrassColorModifier(String grassColorModifier) {
        this.grassColorModifier = Optional.ofNullable(grassColorModifier);
    }

    public void setParticle(NBTCompoundTag particle) {
        this.particle = Optional.ofNullable(particle);
    }

    public void setTemperature(double temperature) {
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
                this.waterFogColor
        );
        b.setId(this.getId());
        if (this.getOffset().isPresent())
            b.setOffset(this.getOffset().get());
        if (this.getTickDelay().isPresent())
            b.setTickDelay(this.getTickDelay().get());
        if (this.getBlockSearchExtent().isPresent())
            b.setBlockSearchExtent(this.getBlockSearchExtent().get());
        if (this.getSound().isPresent())
            b.setSound(this.getSound().get());
        if (this.getFoliageColor().isPresent())
            b.setFoliageColor(this.getFoliageColor().get());
        if (this.getGrassColor().isPresent())
            b.setGrassColor(this.getGrassColor().get());
        if (this.getParticle().isPresent())
            b.setParticle(this.getParticle().get().clone());
        if (this.getAmbientSound().isPresent())
            b.setAmbientSound(this.getAmbientSound().get());
        if (this.getGrassColorModifier().isPresent())
            b.setGrassColorModifier(this.getGrassColorModifier().get());
        if (this.getTemperatureModifier().isPresent())
            b.setTemperatureModifier(this.getTemperatureModifier().get());
        return b;
    }
}
