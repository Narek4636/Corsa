package com.example.corsa;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.corsa.carRoom.CarEntity;

import java.util.List;

public class JsonCarList {
    private int version;
    private List<CarEntity> cars;

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public List<CarEntity> getCars() {
        return cars;
    }

    public void setCars(List<CarEntity> cars) {
        this.cars = cars;
    }
}
