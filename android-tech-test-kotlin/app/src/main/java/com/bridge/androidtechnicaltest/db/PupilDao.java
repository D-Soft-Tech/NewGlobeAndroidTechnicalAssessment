package com.bridge.androidtechnicaltest.db;

import androidx.room.Dao;
import androidx.room.Query;

import java.util.List;

@Dao
public interface PupilDao {

    @Query("SELECT * FROM Pupils ORDER BY name ASC")
    List<Pupil> getPupils();
}
