package com.example.corsa.statsRoom;

import androidx.room.RoomDatabase;

import com.example.corsa.carRoom.CarDao;
import com.example.corsa.carRoom.CarEntity;

@androidx.room.Database(entities = {StatsEntity.class}, version = 1)
public abstract class StatsDatabase extends RoomDatabase {
    public abstract StatsDao statsDao();
}
