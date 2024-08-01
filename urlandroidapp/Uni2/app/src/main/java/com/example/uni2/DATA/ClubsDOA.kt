package com.example.uni2.DATA

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.uni2.DATA.Clubs


@Dao
interface ClubsDOA {
    @Query("select * from Clubs")
    suspend fun getAll(): List<Clubs>
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(clubs: List<Clubs>)
    // insert one user without replacing an identical one - duplicates allowed
    @Insert
    suspend fun insertUser(clubs: Clubs)
    @Delete
    suspend fun deleteUser(clubs: Clubs)

    @Query("SELECT * FROM Clubs WHERE strTeam LIKE '%' || :search || '%' OR strLeague LIKE '%' || :search || '%'")
    suspend fun searchClubs(search: String): List<Clubs>
}