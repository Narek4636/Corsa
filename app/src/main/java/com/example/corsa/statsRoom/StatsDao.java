package com.example.corsa.statsRoom;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import java.util.List;

@Dao
public interface StatsDao {
    @Query("SELECT * FROM stats")
    List<StatsEntity> getAll();

    @Query("SELECT * FROM stats WHERE id = :statsId")
    StatsEntity getStatById(int statsId);

    @Insert
    long insertStat(StatsEntity statsEntity);

    @Delete
    int deleteStat(StatsEntity statsEntity);

    @Update
    int updateStat(StatsEntity statsEntity);
}
