package com.example.sportsdbapp.service

import com.example.sportsdbapp.model.Sport
import io.reactivex.Single
import retrofit2.http.GET

interface SportAPI {

    //https://www.thesportsdb.com/api/v1/json/1/all_sports.php

    //BASE_URL -> https://www.thesportsdb.com/
    //api/v1/json/1/all_sports.php

    @GET("emretnkl/JSONDataSet/main/SportsDb.json")
    fun getSport() : Single<List<Sport>>

}