package com.example.myapplication.data.user_data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_table")
data class UserDto(
    @PrimaryKey(autoGenerate = true) //автоматическая генерация чисел для айди
    val id: Int = 0,
    val login: String,
    val mail: String,
    val password: String,
)
