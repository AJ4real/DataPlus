/**********************************
 Copyright (c) All Rights Reserved
 *********************************/

package me.aj4real.justanothernbtapi.api.nbt;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NBTListTag implements NBTCollectionTag {
    private List<NBTTag> tags;
    public NBTListTag() {
        this.tags = new ArrayList<>();
    }
    public NBTListTag(NBTTag... tags) {
        this.tags = Arrays.asList(tags);
    }
    public NBTListTag(List<NBTTag> tags) {
        this.tags = tags;
    }
    public List<NBTTag> get() {
        return this.tags;
    }
}
