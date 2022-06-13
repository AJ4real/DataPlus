/**********************************
 Copyright (c) All Rights Reserved
 *********************************/

package me.aj4real.justanothernbtapi;

import me.aj4real.justanothernbtapi.api.serializable.JSONSerializer;
import me.aj4real.justanothernbtapi.api.serializable.YAMLSerializer;
import org.bukkit.plugin.Plugin;

public class Main {
    public static NBTAPI nms;
    public void onLoad(Plugin plugin) {
    }
    public void onEnable(Plugin plugin, NBTAPI nms) {
        new JSONSerializer(); // Include
        new YAMLSerializer(); // Include
    }
    public void onDisable(Plugin plugin) {
    }
}
