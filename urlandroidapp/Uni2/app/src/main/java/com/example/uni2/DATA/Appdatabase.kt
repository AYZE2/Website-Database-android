package com.example.uni2.DATA

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.uni2.DATA.Leagues
import com.example.uni2.DATA.LeaguesDOA


@Database(entities = [Leagues::class,Clubs::class], version=1)
abstract class Appdatabase: RoomDatabase() {
    abstract fun LeaguesDOA(): LeaguesDOA
    abstract fun ClubsDOA(): ClubsDOA

}

