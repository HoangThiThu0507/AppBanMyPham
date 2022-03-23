package com.example.projectcuoikhoa.Apriori;

import java.util.HashSet;
import java.util.Set;

public class Law {
    Set<Integer> setA;
    Set<Integer> setB;
    double min_conf;

    public Law(Set<Integer> setA, Set<Integer> setB, double min_conf) {
        super();
        this.setA = setA;
        this.setB = setB;
        this.min_conf = min_conf;
    }

    public Law(double min_conf) {
        super();
        this.setA = new HashSet<>();
        this.setB = new HashSet<>();
        this.min_conf = min_conf;
    }

    public String print() {
        String res = "";
        for (Integer i : setA) res += i;
        res += " --> ";
        for (Integer j : setB) res += j;
        res += "\t min_conf= " + min_conf;
        return res;
    }

    public void setMin_conf(double min_conf) {
        this.min_conf = min_conf;
    }

    public Set<Integer> getSetA() {
        return setA;
    }

    public void setSetA(Set<Integer> setA) {
        this.setA = setA;
    }

    public Set<Integer> getSetB() {
        return setB;
    }

    public void setSetB(Set<Integer> setB) {
        this.setB = setB;
    }

    public double getMin_conf() {
        return min_conf;
    }
}
