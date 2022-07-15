/**********************************
 Copyright (c) All Rights Reserved
 *********************************/

package me.aj4real.dataplus.api.nbt;

import me.aj4real.dataplus.api.FriendlyByteBuf;

import java.util.stream.IntStream;

public class NBTStringTag implements NBTTag, Comparable<String>, CharSequence {
    private static final NBTStringTag EMPTY = new NBTStringTag("");
    private static final NBTStringTag NULL = new NBTStringTag(null);
    private String delegate;

    private NBTStringTag(String delegate) {
        this.delegate = delegate;
    }

    public NBTStringTag clone() {
        return new NBTStringTag(this.delegate);
    }
    public void write(FriendlyByteBuf buf) {
        buf.writeUtf(delegate);
    }
    public static NBTStringTag read(FriendlyByteBuf buf) {
        return new NBTStringTag(buf.readUtf());
    }
    public byte getId() {
        return 8;
    }

    public boolean isValid() {
        return this.delegate != null;
    }

    public static NBTStringTag valueOf(String delegate) {
        if(delegate == null) return NULL;
        return delegate.isEmpty() ? EMPTY : new NBTStringTag(delegate);
    }

    public boolean startsWith(String other) {
        return delegate.startsWith(other);
    }

    public boolean startsWith(String other, int offset) {
        return delegate.startsWith(other, offset);
    }

    public boolean endsWith(String other) {
        return delegate.endsWith(other);
    }

    public String toString() {
        return this.delegate;
    }

    @Override
    public int compareTo(String o) {
        return delegate.compareTo(o);
    }

    @Override
    public int length() {
        return delegate.length();
    }

    @Override
    public char charAt(int index) {
        return delegate.charAt(index);
    }

    @Override
    public CharSequence subSequence(int start, int end) {
        return delegate.substring(start, end);
    }

    @Override
    public IntStream chars() {
        return delegate.chars();
    }

    @Override
    public IntStream codePoints() {
        return delegate.codePoints();
    }

    public boolean equalsIgnoreCase(String anotherString) {
        return delegate.equalsIgnoreCase(anotherString);
    }

    @Override
    public boolean equals(Object o) {
        if(!(o instanceof String) || !(o instanceof NBTStringTag)) return false;
        if(!o.toString().equalsIgnoreCase(delegate)) return false;
        if(o.hashCode() != delegate.hashCode()) return false;
        return true;
    }
}
