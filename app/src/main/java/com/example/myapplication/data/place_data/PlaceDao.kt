package com.example.myapplication.data.place_data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface PlaceDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertPlace(placeDto: PlaceDto)

    @Query("SELECT * FROM 'place_table'")
    fun getPlaces(): List<PlaceDto>

    @Query("SELECT * FROM place_table u WHERE u.object_name=:object_name")
    suspend fun getPlace(object_name: String): PlaceDto

    @Query("DELETE FROM place_table WHERE object_name =:object_name")
    suspend fun deletePlace(object_name: String)

    @Query("SELECT * FROM place_table ORDER BY id ASC")
    fun readAllData(): LiveData<List<PlaceDto>>
}
