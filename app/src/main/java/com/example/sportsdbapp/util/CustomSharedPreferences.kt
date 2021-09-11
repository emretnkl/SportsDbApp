package com.example.sportsdbapp.util

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.preference.Preference

class CustomSharedPreferences {

    companion object{

        private val TIME = "time"
        private var sharedPreferences : SharedPreferences? = null

        @Volatile private var instance : CustomSharedPreferences ? = null

        private val lock = Any()

        operator fun invoke(context: Context) : CustomSharedPreferences = instance ?: synchronized(lock) {

            instance ?: makeCustomSharedPreferences(context).also {
                instance = it
            }
        }

        private fun makeCustomSharedPreferences(context : Context) : CustomSharedPreferences{
            sharedPreferences = androidx.preference.PreferenceManager.getDefaultSharedPreferences(context)
            return CustomSharedPreferences()

        }
    }

    fun saveTime(time : Long) {
        sharedPreferences?.edit(commit = true){
            putLong(TIME,time)
        }
    }
    fun takeTime() = sharedPreferences?.getLong(TIME,0)
}