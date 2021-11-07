package com.example.myapplication.data.placeData

import androidx.lifecycle.LiveData

class PlaceRepository(private val placeDao: PlaceDao) {

    val readAllData: LiveData<List<Place>> = placeDao.readAllData()

    suspend fun insertPlace(place: Place) {
        placeDao.insertPlace(place)
    }

    fun getPlaces(): List<Place> {
        return placeDao.getPlaces()
    }

    suspend fun getPlace(object_name: String): Place {
        return placeDao.getPlace(object_name)
    }

    suspend fun deletePlace(object_name: String) {
        placeDao.deletePlace(object_name)
    }
}
