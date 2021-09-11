package com.example.sportsdbapp.service

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.sportsdbapp.model.Sport

@Database(entities = arrayOf(Sport :: class),version = 1)
abstract class SportDatabase : RoomDatabase() {

    abstract fun sportDao() : SportDAO

    //Singleton

    companion object{

        @Volatile private var instance : SportDatabase? = null

        private val lock = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(lock) {
            instance?: createDatabase(context).also {
                instance = it
            }
        }

        private fun createDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            SportDatabase::class.java,"sportdatabase").build()
    }




}