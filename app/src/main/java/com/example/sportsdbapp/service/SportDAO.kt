package com.example.sportsdbapp.service

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.sportsdbapp.model.Sport

@Dao
interface SportDAO {

    @Insert
    suspend fun insertAll(vararg sport : Sport) : List<Long>

    @Query("SELECT * FROM sport")
    suspend fun getAllSports() : List<Sport>

    @Query("SELECT * FROM sport WHERE uuid = :sportId ")
    suspend fun getSports(sportId : Int) : Sport

    @Query("DELETE FROM sport")
    suspend fun deleteAllSports()


}