package com.example.corsa.carRoom;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "cars")
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

    @ColumnInfo(name = "accelTime")
    public double accelTime;

    @ColumnInfo(name = "imagePath")
    public String imagePath="";

    @ColumnInfo(name = "interior")
    public String interior="";

    @ColumnInfo(name = "carGuessAllow")
    public String carGuessAllow="";

    @ColumnInfo(name = "showInterior")
    public String showInterior="";

    public CarEntity(long id, String name, Integer power, Integer prodYear, String nurbTime, double accelTime, String imagePath, String interior, String carGuessAllow, String showInterior) {
        this.id = id;
        this.name = name;
        this.power = power;
        this.prodYear = prodYear;
        this.nurbTime = nurbTime;
        this.accelTime = accelTime;
        this.imagePath = imagePath;
        this.interior = interior;
        this.carGuessAllow = carGuessAllow;
        this.showInterior = showInterior;
    }
}
