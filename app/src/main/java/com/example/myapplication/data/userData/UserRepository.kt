package com.example.myapplication.data.userData

import com.example.myapplication.data.UserDao

class UserRepository(private val userDao: UserDao) {

    suspend fun addUser(userDto: UserDto) {
        userDao.addUser(userDto)
    }

    suspend fun getUser(login: String, password: String): UserDto {
        return userDao.getUser(login, password)
    }
}
