/**********************************
 Copyright (c) All Rights Reserved
 *********************************/

package me.aj4real.dataplus;

import me.aj4real.dataplus.api.ChunkDataPacketEditor;
import me.aj4real.dataplus.api.login.LoginPacketEditor;
import me.aj4real.dataplus.api.serializable.JSONSerializer;
import me.aj4real.dataplus.api.serializable.YAMLSerializer;
import org.bukkit.plugin.Plugin;

public class DataPlus {
    public static DataPlusNMS nms;
    public static Plugin plugin;
    public void onLoad(Plugin plugin) {
        DataPlus.plugin = plugin;
    }
    public void onEnable(Plugin plugin) {
        new JSONSerializer(); // Include
        new YAMLSerializer(); // Include
        ChunkDataPacketEditor.include(); // Include
        LoginPacketEditor.include(); // Include
    }
    public void onDisable(Plugin plugin) {
    }
}
