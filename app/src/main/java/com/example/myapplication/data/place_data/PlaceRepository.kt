package com.example.myapplication.data.place_data

import androidx.lifecycle.LiveData

class PlaceRepository(private val placeDao: PlaceDao) {

    val readAllData: LiveData<List<PlaceDto>> = placeDao.readAllData()

    suspend fun insertPlace(placeDto: PlaceDto) {
        placeDao.insertPlace(placeDto)
    }

    fun getPlaces(): List<PlaceDto> {
        return placeDao.getPlaces()
    }

    suspend fun getPlace(placeName: String): PlaceDto {
        return placeDao.getPlace(placeName)
    }

    suspend fun deletePlace(placeName: String) {
        placeDao.deletePlace(placeName)
    }
}
