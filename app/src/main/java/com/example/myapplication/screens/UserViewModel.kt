package com.example.myapplication.screens

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.AppDatabase
import com.example.myapplication.data.userData.UserDto
import com.example.myapplication.data.userData.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class UserViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: UserRepository
    var userDto: UserDto? = null

    init {
        val userDao = AppDatabase.getDatabase(application).userDao()
        repository = UserRepository(userDao)
    }

    fun addUser(userDto: UserDto) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addUser(userDto)
        }
    }

    suspend fun getUser(login: String, password: String) {
        userDto = viewModelScope.async(Dispatchers.IO) {
            repository.getUser(login, password)
        }.await()
    }
}
