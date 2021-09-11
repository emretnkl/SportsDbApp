package com.example.sportsdbapp.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.sportsdbapp.model.Sport
import com.example.sportsdbapp.service.SportAPIService
import com.example.sportsdbapp.service.SportDAO
import com.example.sportsdbapp.service.SportDatabase
import com.example.sportsdbapp.util.CustomSharedPreferences
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch

class SportListViewModel(application: Application) : BaseViewModel(application) {
    val sports = MutableLiveData<List<Sport>>()
    val sportErrorMessage = MutableLiveData<Boolean>()
    val sportLoading = MutableLiveData<Boolean>()
    private var updatingTime = 10 * 60 * 1000 * 1000 * 1000L

    private val sportApiService = SportAPIService()
    private val disposable = CompositeDisposable()
    private val customSharedPreferences = CustomSharedPreferences(getApplication())

    fun refreshData(){

        val savingTime = customSharedPreferences.takeTime()
        if (savingTime != null && savingTime != 0L && System.nanoTime() - savingTime < updatingTime){

            getDatasFromSqlite()
        }else
        {
            getDatasFromWeb()

        }


    }

    fun refreshFromWeb(){
        getDatasFromWeb()
    }

    private fun getDatasFromWeb(){
        sportLoading.value = true

        disposable.add(
            sportApiService.getData()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<List<Sport>>() {
                    override fun onSuccess(t: List<Sport>) {
                        //Başarılı
                        savetoSqlite(t)
                        Toast.makeText(getApplication(),"Veriler Web'den alındı.",Toast.LENGTH_LONG).show()


                    }

                    override fun onError(e: Throwable) {
                        //Hata
                        sportErrorMessage.value = true
                        sportLoading.value = false
                        e.printStackTrace()
                    }

                })
        )
    }

    private fun getDatasFromSqlite(){
        launch {
            val sportList = SportDatabase(getApplication()).sportDao().getAllSports()
            showSports(sportList)
            Toast.makeText(getApplication(),"Veriler Room'dan alındı.",Toast.LENGTH_LONG).show()
        }

    }

    private fun showSports(sportsList : List<Sport>){
        sports.value = sportsList
        sportErrorMessage.value = false
        sportLoading.value = false
    }

    private fun savetoSqlite(sportsList : List<Sport>){

        launch {
            val dao = SportDatabase(getApplication()).sportDao()
            dao.deleteAllSports()
            val uuidList = dao.insertAll(*sportsList.toTypedArray())

            var i = 0
            while (i < sportsList.size){
                sportsList[i].uuid = uuidList[i].toInt()
                i = i + 1

            }
            showSports(sportsList)

        }
        customSharedPreferences.saveTime(System.nanoTime())


    }
}