package com.example.sportsdbapp.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class Sport(
    @ColumnInfo(name = "strSport")
    @SerializedName("strSport")
    val sportName: String?,
    @ColumnInfo(name = "strFormat")
    @SerializedName("strFormat")
    val sportFormat: String?,
    @ColumnInfo(name = "strSportDescription")
    @SerializedName("strSportDescription")
    val sportDescription: String?,
    @ColumnInfo(name = "strSportThumb")
    @SerializedName("strSportThumb")
    val sportImage: String?
) {

    @PrimaryKey(autoGenerate = true)
    var uuid : Int = 0
}