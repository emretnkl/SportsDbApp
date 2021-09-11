package com.example.sportsdbapp.service

import com.example.sportsdbapp.model.Sport
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.Retrofit.Builder
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class SportAPIService {

    //https://www.thesportsdb.com/api/v1/json/1/all_sports.php
    //https://github.com/emretnkl/JSONDataSet/blob/main/SportsDb.json
    //https://raw.githubusercontent.com/emretnkl/JSONDataSet/main/SportsDb.json
    //BASE_URL -> https://www.thesportsdb.com/
    //api/v1/json/1/all_sports.php

    private val BASE_URL = "https://raw.githubusercontent.com/"
    private val api = Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
        .create(SportAPI::class.java)

    fun getData() : Single<List<Sport>> {
        return api.getSport()

    }

}