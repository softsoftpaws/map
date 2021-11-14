package com.example.myapplication.data.user_data

class UserRepository(private val userDao: UserDao) {

    suspend fun addUser(userDto: UserDto) {
        userDao.addUser(userDto)
    }

    suspend fun getUser(login: String, password: String): UserDto {
        return userDao.getUser(login, password)
    }
}
