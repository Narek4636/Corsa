package com.samsung.corsa.carRoom;

import androidx.room.RoomDatabase;

@androidx.room.Database(entities = {CarEntity.class}, version = 10)
public abstract class CarDatabase extends RoomDatabase {
    public abstract CarDao carDao();
}
