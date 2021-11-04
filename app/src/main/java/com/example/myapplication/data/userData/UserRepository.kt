package com.example.myapplication.data.userData

import com.example.myapplication.data.UserDao

class UserRepository(private val userDao: UserDao) {

    suspend fun addUser(user: User) {
        userDao.addUser(user)
    }

    suspend fun getUser(login: String, password: String): User {
        return userDao.getUser(login, password)
    }
}
