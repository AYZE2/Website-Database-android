package com.example.uni2.DATA

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Leagues(
    @PrimaryKey (autoGenerate = true)var id: Int=0,
    val idLeague: String?,
    val strLeague: String?,
    val strSport: String?,
    val strLeagueAlternate: String?
)