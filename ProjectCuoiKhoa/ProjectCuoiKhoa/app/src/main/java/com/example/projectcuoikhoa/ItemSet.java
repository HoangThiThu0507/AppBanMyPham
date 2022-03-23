package com.example.projectcuoikhoa;

import java.util.Set;

public class ItemSet {
    Set<Integer> itemSet;
    int support;

    public Set<Integer> getItemSet() {
        return itemSet;
    }

    public void setItemSet(Set<Integer> itemSet) {
        this.itemSet = itemSet;
    }

    public int getSupport() {
        return support;
    }

    public void setSupport(int support) {
        this.support = support;
    }

    public ItemSet(Set<Integer> itemSet, int support) {
        this.itemSet = itemSet;
        this.support = support;
    }

    public ItemSet() {
    }
}
