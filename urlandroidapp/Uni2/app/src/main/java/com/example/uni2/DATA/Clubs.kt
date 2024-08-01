package com.example.uni2.DATA

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Clubs(
    @PrimaryKey (autoGenerate = true)var id: Int=0,
    val strTeam: String?,
    val strLeague: String?,
    val strStadium: String?,
    val strDescriptionEN: String?
)

