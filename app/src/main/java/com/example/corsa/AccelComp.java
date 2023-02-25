package com.example.corsa;

public class AccelComp {
    double time;
    String timestr;
    String name;
    int prod_year;
    int image;

    public AccelComp(int image, double time, String timestr, String name, int prod_year) {
        this.time = time;
        this.timestr = timestr;
        this.name = name;
        this.prod_year = prod_year;
        this.image = image;
    }
}
