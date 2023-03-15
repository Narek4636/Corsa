package com.example.corsa;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "cars")
public class Car {
    @PrimaryKey(autoGenerate = true)
    String name;
    double accel_time;
    int power;
    String nurb_time;
    int year;
    byte[] image;

    public Car(String name, double accel_time, int power, String nurb_time, int year, byte[] image) {
        this.name = name;
        this.accel_time = accel_time;
        this.power = power;
        this.nurb_time = nurb_time;
        this.year = year;
    }
}
