package com.example.corsa.carRoom;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "cars", indices = {@Index(value = {"name"}, unique = true)})
public class CarEntity implements Serializable {
    @PrimaryKey(autoGenerate = true)
    public long id;

    @ColumnInfo(name = "name")
    public String name;

    @ColumnInfo(name = "power")
    public Integer power;

    @ColumnInfo(name = "prodYear")
    public Integer prodYear;

    @ColumnInfo(name = "nurbTime")
    public String nurbTime;

    @ColumnInfo(name = "image")
    public byte[] image;

    @ColumnInfo(name = "accelTime")
    public double accelTime;

    @ColumnInfo(name = "added")
    public int added;

    public CarEntity(String name, Integer power, Integer prodYear, String nurbTime, double accelTime, byte[] image) {
        this.name = name;
        this.power = power;
        this.prodYear = prodYear;
        this.nurbTime = nurbTime;
        this.image = image;
        this.accelTime = accelTime;
    }
}
