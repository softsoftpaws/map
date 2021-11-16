package com.example.myapplication.data.place_data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "place_table")
data class PlaceDto(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val objectName: String,
    val objectType: String,
    val placeImage: String,
    val phone: String,
    val site: String,
    val comments: String,
    val latitude: Double,
    val longitude: Double,
    val address: String
)
