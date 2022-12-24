package me.aj4real.dataplus.api;

import org.bukkit.Bukkit;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class Version {
    private static final Map<String, Version> cache = new HashMap<>();
    private static final Version current;
    static {
        current = new Version();
        cache.put(current.toString().intern(), current);
    }
    public static Version current() {
        return current;
    }
    public static Version of(String strVersion) {
        return cache.computeIfAbsent(strVersion.intern(), (s) -> new Version(s));
    }
    public static Version of(int major, int minor, int micro) {
        String s = (major + "." + minor + "." + micro).intern();
        return cache.computeIfAbsent(s, (str) -> new Version(major, minor, micro));
    }
    private final int major, minor, micro;
    private final String strVersion;
    private Version() {
        this(Bukkit.getVersion());
    }
    private Version(String strVer) {
        String regex = "\\d+(\\.\\d+)+";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(strVer);
        if(!matcher.find()) throw new RuntimeException("Could not identify version in String");
        String[] split = (strVersion = matcher.group().intern()).split("\\.");
        major = Integer.parseInt(split[0]);
        minor = Integer.parseInt(split[1]);
        int _micro;
        try {
            _micro = Integer.parseInt(split[2]);
        } catch (Throwable t) {
            _micro = 0;
        }
        micro = _micro;
    }
    private Version(int major, int minor, int micro) {
        this.major = major;
        this.minor = minor;
        this.micro = micro;
        this.strVersion = (major + "." + minor + "." + micro).intern();
    }

    public boolean isHigherThan(Version other) {
        if(this.major > other.major) return true;
        if(this.minor > other.minor) return true;
        return this.micro > other.micro;
    }

    @Override
    public String toString() {
        return this.strVersion;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Version other = (Version) o;
        return this.toString().equals(other.toString());
    }

    @Override
    public int hashCode() {
        int result = major ^ (major >>> 32);
        result = 31 * result + (minor ^ (minor >>> 32));
        result = 31 * result + (micro ^ (micro >>> 32));
        return result;
    }
}
