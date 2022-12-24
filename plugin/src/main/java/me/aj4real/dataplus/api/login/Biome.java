/**********************************
 Copyright (c) All Rights Reserved
 *********************************/

package me.aj4real.dataplus.api.login;

import me.aj4real.dataplus.api.nbt.NBTCompoundTag;
import org.bukkit.NamespacedKey;

import java.util.Optional;
import java.util.function.Predicate;
import java.util.function.Supplier;

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
            modWaterFogColor = false,
            modPrecipitation = false,
            modAdditionalSounds = false,
            modAdditionalSoundTickChance = false,
            modAmbientSound = false;

    private final Biome original;

    //element
    private double temperature, downfall;
    private String precipitation;

    //effect
    private int skyColor, waterColor, fogColor, waterFogColor;

    //mood_sound
    private Optional<Double> offset = Optional.empty(), additionalSoundTickChance = Optional.empty();
    private Optional<Integer> tickDelay = Optional.empty(), blockSearchExtent = Optional.empty();
    private Optional<String> sound = Optional.empty(), additionalSound = Optional.empty();

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
        if (clone) this.original = this.clone();
        else this.original = null;
    }

    public boolean isMod() {
        return modFogColor ||
                modFoliageColor ||
                modGrassColor ||
                modParticle ||
                modSkyColor ||
                modWaterColor ||
                modWaterFogColor ||
                modAdditionalSounds ||
                modAdditionalSoundTickChance ||
                modAmbientSound;
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
        this.waterFogColor = value == null ? original.waterFogColor : value;
        this.modWaterFogColor = value != null;
    }

    public void setWaterFogColor(int r, int g, int b) {
        setWaterFogColor(255 << 24 | r << 16 | g << 8 | b);
    }

    public void setFogColor(Integer value) {
        this.fogColor = value == null ? original.fogColor : value;
        this.modFogColor = value != null;
    }

    public void setFogColor(int r, int g, int b) {
        setFogColor(255 << 24 | r << 16 | g << 8 | b);
    }

    public void setWaterColor(Integer value) {
        this.waterColor = value == null ? original.waterColor : value;
        this.modWaterColor = value != null;
    }

    public void setWaterColor(int r, int g, int b) {
        setWaterColor(255 << 24 | r << 16 | g << 8 | b);
    }

    public void setSkyColor(Integer value) {
        this.skyColor = value == null ? original.skyColor : value;
        this.modSkyColor = value != null;
    }

    public void setSkyColor(int r, int g, int b) {
        setSkyColor(255 << 24 | r << 16 | g << 8 | b);
    }

    public void setCategory(String value) {
        this.category = Optional.ofNullable(value);
    }

    public void setPrecipitation(String value) {
        this.precipitation = value == null ? original.precipitation : value;
        this.modPrecipitation = value != null;
    }

    public void setDownfall(Float value) {
        this.downfall = value;
    }

    public void setTemperature(Float value) {
        this.temperature = value;
    }

    public void setMoodSound(String value) {
        this.sound = Optional.ofNullable(value);
    }

    public void setAmbientSound(String ambientSound) {
        this.ambientSound = original.ambientSound.or(() -> Optional.ofNullable(ambientSound));
        this.modAmbientSound = ambientSound != null;
    }

    public void setDownfall(Double downfall) {
        this.downfall = downfall;
    }

    public void setFoliageColor(Integer value) {
        this.foliageColor = original.foliageColor.or(() -> Optional.ofNullable(value));
        this.modFoliageColor = value != null;
    }

    public void setFoliageColor(int r, int g, int b) {
        setFoliageColor(255 << 24 | r << 16 | g << 8 | b);
    }

    public void setGrassColor(Integer value) {
        this.grassColor = original.grassColor.or(() -> Optional.ofNullable(value));
        this.modGrassColor = value != null;
    }

    public void setGrassColor(int r, int g, int b) {
        setGrassColor(255 << 24 | r << 16 | g << 8 | b);
    }

    public void setGrassColorModifier(String grassColorModifier) {
        this.grassColorModifier = original.grassColorModifier.or(() -> Optional.ofNullable(grassColorModifier));
    }

    public void setParticle(NBTCompoundTag particle) {
        this.particle = original.particle.or(() -> Optional.ofNullable(particle));
        this.modParticle = particle != null;
    }

    public void setTemperature(Double temperature) {
        this.temperature = temperature;
    }

    public void setTemperatureModifier(String temperatureModifier) {
        this.temperatureModifier = Optional.ofNullable(temperatureModifier);
    }

    public void setAdditionalSound(String additionalSound) {
        this.additionalSound = original.additionalSound.or(() -> Optional.ofNullable(additionalSound));
        this.modAdditionalSounds = additionalSound != null;
    }

    public void setAdditionalSoundTickChance(Double additionalSoundTickChance) {
        this.additionalSoundTickChance = original.additionalSoundTickChance.or(() -> Optional.ofNullable(additionalSoundTickChance));
        this.modAdditionalSoundTickChance = additionalSoundTickChance != null;
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

    public Optional<Double> getAdditionalSoundTickChance() {
        return additionalSoundTickChance;
    }

    public Optional<String> getAdditionalSound() {
        return additionalSound;
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
