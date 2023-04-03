/**********************************
 Copyright (c) All Rights Reserved
 *********************************/

package me.aj4real.dataplus;

import me.aj4real.dataplus.denizen.DenizenImpl;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Dist extends JavaPlugin {
    Dist plugin = null;
    DataPlus main = new DataPlus();
    public void onLoad() {
        this.plugin = this;
        main.onLoad(this);
    }
    public void onEnable() {
        main.onEnable(this);
    }
    public static DataPlusNMS init(Plugin plugin) {
        String regex = "\\d+(\\.\\d+)+";
        String strVer = Bukkit.getVersion();
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(strVer);
        matcher.find();
        strVer = 'v' + matcher.group();
        try {
            plugin.getLogger().log(Level.INFO, Dist.class.getCanonicalName() + ": Attempting to load NMS interface for " + strVer);
            Version ver = Version.valueOf(strVer.replace('.', '_'));
            DataPlusNMS nms = ver.nms.newInstance();
            nms.onEnable(plugin);
            DataPlus.nms = nms;
            if(Bukkit.getPluginManager().isPluginEnabled("Denizen")) {
                new DenizenImpl(nms);
            }
            return nms;
        } catch (Exception e) {
            plugin.getLogger().log(Level.SEVERE, "Could not initiate support for " + strVer + ", Is it a supported version?", e);
            Bukkit.getPluginManager().disablePlugin(plugin);
            return null;
        }
    }
    public void onDisable() {
        main.onDisable(this);
    }
    public enum Version {
        v1_17(me.aj4real.dataplus.nms.v1_17.DataPlusNMSImpl.class),
        v1_17_1(me.aj4real.dataplus.nms.v1_17_1.DataPlusNMSImpl.class),
        v1_18(me.aj4real.dataplus.nms.v1_18.DataPlusNMSImpl.class),
        v1_18_1(me.aj4real.dataplus.nms.v1_18_1.DataPlusNMSImpl.class),
        v1_18_2(me.aj4real.dataplus.nms.v1_18_2.DataPlusNMSImpl.class),
        v1_19(me.aj4real.dataplus.nms.v1_19.DataPlusNMSImpl.class),
        v1_19_1(me.aj4real.dataplus.nms.v1_19_1.DataPlusNMSImpl.class),
        v1_19_2(me.aj4real.dataplus.nms.v1_19_2.DataPlusNMSImpl.class),
        v1_19_3(me.aj4real.dataplus.nms.v1_19_3.DataPlusNMSImpl.class),
        v1_19_4(me.aj4real.dataplus.nms.v1_19_4.DataPlusNMSImpl.class);
        private final Class<? extends DataPlusNMS> nms;
        Version(Class<? extends DataPlusNMS> nms) {
            this.nms = nms;
        }
    }
}
