package com.example.corsa.carRoom;

import androidx.annotation.NonNull;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

@androidx.room.Database(entities = {CarEntity.class}, version = 7)
public abstract class CarDatabase extends RoomDatabase {
    public abstract CarDao carDao();
}
