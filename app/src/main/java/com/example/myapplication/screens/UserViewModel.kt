package com.example.myapplication.screens

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.AppDatabase
import com.example.myapplication.data.user_data.UserDto
import com.example.myapplication.data.user_data.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class UserViewModel(application: Application) : AndroidViewModel(application) {

    var userDto: UserDto? = null
    private val repository: UserRepository

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
