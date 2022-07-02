/**********************************
 Copyright (c) All Rights Reserved
 *********************************/

package me.aj4real.dataplus.api.login;

import me.aj4real.dataplus.api.nbt.NBTTag;
import org.bukkit.NamespacedKey;

import java.util.Optional;

public class Dimension implements Cloneable {
    private NamespacedKey name;
    private int id;

    private boolean piglinSafe, natural, respawnAnchorWorks, hasSkylight, bedWorks, hasRaids, ultrawarm, hasCeiling;
    private float ambientLight;
    private int logicalHeight, minY, height;
    private Optional<NBTTag> monsterSpawnLightLimit = Optional.empty(), monsterSpawnLightLevel = Optional.empty();
    private double coordinateScale;
    private String infiniburn, effects;

    public Dimension(
            NamespacedKey name,
            int id,
            boolean piglinSafe,
            boolean natural,
            boolean respawnAnchorWorks,
            boolean hasSkylight,
            boolean bedWorks,
            boolean hasRaids,
            boolean ultrawarm,
            boolean hasCeiling,
            float ambientLight,
            int logicalHeight,
            int minY,
            int height,
            double coordinateScale,
            String infiniburn,
            String effect
    ) {
        this.name = name;
        this.id = id;
        this.piglinSafe = piglinSafe;
        this.natural = natural;
        this.respawnAnchorWorks = respawnAnchorWorks;
        this.hasSkylight = hasSkylight;
        this.bedWorks = bedWorks;
        this.hasRaids = hasRaids;
        this.ultrawarm = ultrawarm;
        this.hasCeiling = hasCeiling;
        this.ambientLight = ambientLight;
        this.logicalHeight = logicalHeight;
        this.minY = minY;
        this.height = height;
        this.coordinateScale = coordinateScale;
        this.infiniburn = infiniburn;
        this.effects = effect;
    }

    public void setId(int value) {
        this.id = value;
    }

    public void setName(NamespacedKey value) {
        this.name = value;
    }

    public void setMinY(int value) {
        this.minY = value;
    }

    public void setHeight(int value) {
        this.height = value;
    }

    public void setLogicalHeight(int value) {
        this.logicalHeight = value;
    }

    public void setMonsterSpawnBlockLightLimit(NBTTag monsterSpawnLightLimit) {
        this.monsterSpawnLightLimit = Optional.of(monsterSpawnLightLimit);
    }

    public void setMonsterSpawnLightLevel(NBTTag monsterSpawnLightLevel) {
        this.monsterSpawnLightLevel = Optional.of(monsterSpawnLightLevel);
    }

    public void setInfiniburn(String value) {
        this.infiniburn = value;
    }

    public void setAmbientLight(float value) {
        this.ambientLight = value;
    }

    public void setCoordinateScale(double value) {
        this.coordinateScale = value;
    }

    public void setEffects(String value) {
        this.effects = value;
    }

    public void setBedWorks(boolean value) {
        this.bedWorks = value;
    }

    public void setRespawnAnchorWorks(boolean value) {
        this.respawnAnchorWorks = value;
    }

    public void setNatural(boolean value) {
        this.natural = value;
    }

    public void setPiglinSafe(boolean value) {
        this.piglinSafe = value;
    }

    public void setUltrawarm(boolean value) {
        this.ultrawarm = value;
    }

    public void setHasRaids(boolean value) {
        this.hasRaids = value;
    }

    public void setHasSkylight(boolean value) {
        this.hasSkylight = value;
    }

    public void setHasCeiling(boolean value) {
        this.hasCeiling = value;
    }

    public int getId() {
        return this.id;
    }

    public NamespacedKey getName() {
        return this.name;
    }

    public boolean hasCeiling() {
        return this.hasCeiling;
    }

    public boolean ultrawarm() {
        return this.ultrawarm;
    }

    public boolean hasRaids() {
        return this.hasRaids;
    }

    public boolean doesBedWork() {
        return this.bedWorks;
    }

    public boolean hasSkylight() {
        return this.hasSkylight;
    }

    public boolean doesRespawnAnchorWork() {
        return this.respawnAnchorWorks;
    }

    public boolean isNatural() {
        return this.natural;
    }

    public boolean isPiglinSafe() {
        return this.piglinSafe;
    }

    public float getAmbientLight() {
        return this.ambientLight;
    }

    public int getHeight() {
        return this.height;
    }

    public int getMinY() {
        return this.minY;
    }

    public int getLogicalHeight() {
        return this.logicalHeight;
    }

    public NBTTag getMonsterSpawnBlockLightLimit() {
        return monsterSpawnLightLimit.orElse(null);
    }

    public NBTTag getMonsterSpawnLightLevel() {
        return monsterSpawnLightLevel.orElse(null);
    }

    public double getCoordinateScale() {
        return this.coordinateScale;
    }

    public String getInfiniburn() {
        return this.infiniburn;
    }

    public String getEffects() {
        return this.effects;
    }

    @Override
    public Dimension clone() {
        Dimension d = new Dimension(
                NamespacedKey.fromString(this.name.toString()),
                this.id,
                this.piglinSafe,
                this.natural,
                this.respawnAnchorWorks,
                this.hasSkylight,
                this.bedWorks,
                this.hasRaids,
                this.ultrawarm,
                this.hasCeiling,
                this.ambientLight,
                this.logicalHeight,
                this.minY,
                this.height,
                this.coordinateScale,
                this.infiniburn,
                this.effects
        );
        return d;
    }
}
