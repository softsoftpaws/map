package com.example.myapplication.screens

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.User
import com.example.myapplication.data.UserDatabase
import com.example.myapplication.data.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class UserViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: UserRepository
    var user: User? = null

    init {
        val userDao = UserDatabase.getDatabase(application).userDao()
        repository = UserRepository(userDao)
    }

    fun addUser(user: User) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addUser(user)
        }
    }

    suspend fun getUser(login: String, password: String) {
        user = viewModelScope.async(Dispatchers.IO) {
            repository.getUser(login, password)
        }.await()
    }
}
