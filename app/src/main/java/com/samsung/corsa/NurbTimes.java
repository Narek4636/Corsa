package com.samsung.corsa;

public class NurbTimes {
    public int MM;
    public int SS;

    public NurbTimes(String time) {
        this.MM = Integer.parseInt(String.valueOf(time.charAt(0)));
        int x = Integer.parseInt(String.valueOf(time.charAt(1))) * 10;
        int y = Integer.parseInt(String.valueOf(time.charAt(2)));
        this.SS = x + y;
    }
}
