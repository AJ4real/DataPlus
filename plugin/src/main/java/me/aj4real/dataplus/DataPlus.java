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
        this.plugin = plugin;
    }
    public void onEnable(Plugin plugin) {
        try {
            new JSONSerializer(); // Include
            new YAMLSerializer(); // Include
            ChunkDataPacketEditor.newInstance(null); // Include
            new LoginPacketEditor(null); // Include
        } catch (Exception e) {}
    }
    public void onDisable(Plugin plugin) {
    }
}
