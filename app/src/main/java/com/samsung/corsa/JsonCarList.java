package com.samsung.corsa;

import com.samsung.corsa.carRoom.CarEntity;

import java.util.List;

public class JsonCarList {
    private int version;
    private int loginStatus;
    private List<CarEntity> cars;

    public int getLoginStatus() {
        return loginStatus;
    }

    public void setLoginStatus(int logged) {
        this.loginStatus = logged;
    }

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
