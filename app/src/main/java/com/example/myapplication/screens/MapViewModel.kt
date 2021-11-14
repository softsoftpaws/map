package com.example.myapplication.screens

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.AppDatabase
import com.example.myapplication.data.place_data.PlaceDto
import com.example.myapplication.data.place_data.PlaceRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class MapViewModel(application: Application) : AndroidViewModel(application) {

    val readAllData: LiveData<List<PlaceDto>>
    var placeDto: PlaceDto? = null
    private val repository: PlaceRepository

    init {
        val placeDao = AppDatabase.getDatabase(application).placeDao()
        repository = PlaceRepository(placeDao)
        readAllData = repository.readAllData
    }

    fun insertPlace(placeDto: PlaceDto) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertPlace(placeDto)
        }
    }

    suspend fun getPlaces(): List<PlaceDto> {
        return viewModelScope.async(Dispatchers.IO) {
            repository.getPlaces()
        }.await()
    }

    suspend fun getPlace(object_name: String) {
        placeDto = viewModelScope.async(Dispatchers.IO) {
            repository.getPlace(object_name)
        }.await()
    }

    fun deletePlace(object_name: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deletePlace(object_name)
        }
    }
}
