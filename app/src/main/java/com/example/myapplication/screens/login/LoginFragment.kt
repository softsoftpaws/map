package com.example.myapplication.screens.login

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.myapplication.MapActivity
import com.example.myapplication.R
import com.example.myapplication.screens.UserViewModel
import com.google.android.material.textfield.TextInputEditText

class LoginFragment : Fragment() {

    private lateinit var userViewModel: UserViewModel
    lateinit var sharedPreferences: SharedPreferences
    var isRemembered = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val loginView = inflater.inflate(R.layout.fragment_login, container, false)
        userViewModel = ViewModelProvider(this).get(UserViewModel::class.java)
        sharedPreferences =
            requireContext().getSharedPreferences("SHARED_PREFERENCES", Context.MODE_PRIVATE)
        isRemembered = sharedPreferences.getBoolean("CHECKBOX", false)

        checkLogin()

        loginView.findViewById<Button>(R.id.enter).setOnClickListener {
            val log =
                loginView.findViewById<TextInputEditText>(R.id.log_et_log).text.toString().trim()
            val pass =
                loginView.findViewById<TextInputEditText>(R.id.log_et_pass).text.toString().trim()

            lifecycleScope.launchWhenResumed {
                userViewModel.getUser(log, pass)
                val userEntity = userViewModel.user
                if (userEntity != null) {
                    saveData(log, pass)
                    transitionToMap()
                } else {
                    Toast.makeText(context, "Пользователь не найден", Toast.LENGTH_LONG).show()
                }
            }
        }
        return loginView
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
