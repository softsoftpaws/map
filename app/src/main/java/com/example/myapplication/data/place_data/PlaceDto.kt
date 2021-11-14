package com.example.myapplication.data.place_data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "place_table")
data class PlaceDto(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val object_name: String,
    val object_type: String,
    val opening_days: String,
    val phone: String,
    val site: String,
    val comments: String,
    val latitude: Double,
    val longitude: Double,
)
