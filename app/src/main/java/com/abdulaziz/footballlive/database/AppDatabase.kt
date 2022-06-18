package com.abdulaziz.footballlive.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.abdulaziz.footballlive.database.dao.LeagueDao
import com.abdulaziz.footballlive.database.entity.League

@Database(entities = [League::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun leagueDao():LeagueDao

    companion object{
        private var database : AppDatabase?=null

        fun getInstance(context: Context):AppDatabase{
            if (database==null){
                database = Room.databaseBuilder(context, AppDatabase::class.java, "database")
                    .allowMainThreadQueries()
                    .build()
            }
            return database!!
        }
    }

}