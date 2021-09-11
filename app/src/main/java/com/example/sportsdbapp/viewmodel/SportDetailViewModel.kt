package com.example.sportsdbapp.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.sportsdbapp.model.Sport
import com.example.sportsdbapp.service.SportDatabase
import kotlinx.coroutines.launch

class SportDetailViewModel(application: Application) : BaseViewModel(application) {
    val sportLiveData = MutableLiveData<Sport>()

    fun getRoomData(uuid : Int) {
        launch {
            val dao = SportDatabase(getApplication()).sportDao()
            val sport = dao.getSports(uuid)
            sportLiveData.value = sport
        }
    }
}