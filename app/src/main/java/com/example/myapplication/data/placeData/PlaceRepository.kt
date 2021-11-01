package com.example.myapplication.data.placeData

class PlaceRepository(private val placeDao: PlaceDao) {

    suspend fun insertPlace(place: Place) {
        placeDao.insertPlace(place)
    }

       fun getPlaces():List<Place> {
          return placeDao.getPlaces()
    }
    suspend fun getPlace(object_name:String): Place {
        return placeDao.getPlace(object_name)
    }
}
