package com.example.uni2.DATA

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.uni2.DATA.Leagues


@Dao
interface LeaguesDOA {
    @Query("select * from Leagues")
    suspend fun getAll(): List<Leagues>
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg leagues: Leagues)
    // insert one user without replacing an identical one - duplicates allowed
    @Insert
    suspend fun insertUser(leagues: Leagues)
    @Delete
    suspend fun deleteUser(leagues: Leagues)

}