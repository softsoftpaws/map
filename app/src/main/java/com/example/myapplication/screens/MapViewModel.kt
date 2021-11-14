package com.example.myapplication.screens

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.AppDatabase
import com.example.myapplication.data.placeData.Place
import com.example.myapplication.data.placeData.PlaceRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class MapViewModel(application: Application) : AndroidViewModel(application) {

    val readAllData: LiveData<List<Place>>
    private val repository: PlaceRepository
    var place: Place? = null

    init {
        val placeDao = AppDatabase.getDatabase(application).placeDao()
        repository = PlaceRepository(placeDao)
        readAllData = repository.readAllData
    }

    fun insertPlace(place: Place) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertPlace(place)
        }
    }

    suspend fun getPlaces(): List<Place> {
        return viewModelScope.async(Dispatchers.IO) {
            repository.getPlaces()
        }.await()
    }

    suspend fun getPlace(object_name: String) {
        place = viewModelScope.async(Dispatchers.IO) {
            repository.getPlace(object_name)
        }.await()
    }

    fun deletePlace(object_name: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deletePlace(object_name)
        }
    }
}
