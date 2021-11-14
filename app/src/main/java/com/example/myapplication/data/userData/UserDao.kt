package com.example.myapplication.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.myapplication.data.userData.UserDto

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addUser(userDto: UserDto)

    @Query("SELECT * FROM user_table u WHERE u.login=:login AND u.password=:password")
    suspend fun getUser(login: String, password: String): UserDto
}
