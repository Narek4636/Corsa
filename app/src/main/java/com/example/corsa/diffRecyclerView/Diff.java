package com.example.corsa.diffRecyclerView;

public class Diff {
    String diffName;
    int xp;
    String diffOrHid;
    int bound1;
    int bound2;
    String unit;

    public Diff(String diffName, int xp, String diffOrHid, int bound1, int bound2, String unit) {
        this.diffName = diffName;
        this.xp = xp;
        this.diffOrHid = diffOrHid;
        this.bound1 = bound1;
        this.bound2 = bound2;
        this.unit = unit;
    }
}
