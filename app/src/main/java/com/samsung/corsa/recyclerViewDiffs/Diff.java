package com.samsung.corsa.recyclerViewDiffs;

public class Diff {
    String diffName;
    int xp;
    String diffOrHid;
    String bound1;
    String bound2;
    String unit;

    public Diff(String diffName, int xp, String diffOrHid, String bound1, String bound2, String unit) {
        this.diffName = diffName;
        this.xp = xp;
        this.diffOrHid = diffOrHid;
        this.bound1 = bound1;
        this.bound2 = bound2;
        this.unit = unit;
    }
}
