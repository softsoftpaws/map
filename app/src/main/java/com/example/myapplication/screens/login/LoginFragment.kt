package com.example.myapplication.screens.login

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.myapplication.MapActivity
import com.example.myapplication.databinding.FragmentLoginBinding
import com.example.myapplication.screens.UserViewModel

class LoginFragment : Fragment() {

    var isRemembered = false
    private lateinit var binding: FragmentLoginBinding
    private lateinit var userViewModel: UserViewModel
    lateinit var sharedPreferences: SharedPreferences

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentLoginBinding.inflate(layoutInflater, container, false)
        userViewModel = ViewModelProvider(this)[UserViewModel::class.java]
        sharedPreferences = requireContext().getSharedPreferences("SHARED_PREFERENCES", Context.MODE_PRIVATE)
        isRemembered = sharedPreferences.getBoolean("CHECKBOX", false)

        checkLogin()

        binding.enterButton.setOnClickListener {
            val log = binding.logEditText.text.toString().trim()
            val pass = binding.passEditText.text.toString().trim()

            lifecycleScope.launchWhenResumed {
                if (checkLoginText(log) == checkPasswordText(pass)) {
                    userViewModel.getUser(log, pass)
                    val userEntity = userViewModel.userDto
                    if (userEntity != null) {
                        saveData(log, pass)
                        transitionToMap()
                    } else {
                        Toast.makeText(context, "Пользователь не найден", Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
        return binding.root
    }

    private fun checkLoginText(login: String): Boolean {
        with(binding) {
            if (login.isBlank()) {
                logTextInputLayout.error = "Введите логин"
                return false
            } else if (login.length < 6) {
                logTextInputLayout.error = "Длина менее 6 символов"
                return false
            } else {
                return true
            }
        }
    }

    private fun checkPasswordText(password: String): Boolean {
        with(binding) {
            if (password.isBlank()) {
                passTextInputLayout.error = "Введите пароль"
                return false
            } else if (password.length < 6) {
                passTextInputLayout.error = "Длина менее 6 символов"
                return false
            } else {
                return true
            }

        }
    }

    private fun transitionToMap() {
        startActivity(Intent(context, MapActivity::class.java))
    }

    private fun checkLogin() {
        if (isRemembered) transitionToMap()
    }

    private fun saveData(login: String, password: String) {
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.putString("LOGIN", login)
        editor.putString("PASSWORD", password)
        editor.putBoolean("CHECKBOX", true)
        editor.apply()
    }
}
