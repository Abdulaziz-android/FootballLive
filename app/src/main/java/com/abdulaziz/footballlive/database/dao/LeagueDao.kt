package com.abdulaziz.footballlive.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.abdulaziz.footballlive.database.entity.League

@Dao
interface LeagueDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(leagues: List<League>)

    @Query("select * from leagues")
    suspend fun getLeagues():List<League>

}