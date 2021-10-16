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
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        userViewModel = ViewModelProvider(this).get(UserViewModel::class.java)
        sharedPreferences = requireContext().getSharedPreferences("SHARED_PREFERENCES", Context.MODE_PRIVATE)
        isRemembered = sharedPreferences.getBoolean("CHECKBOX", false)

        fun transition(){
            val intent = Intent(activity, MapActivity::class.java)
            startActivity(intent)
        }

        fun checkLogin(){
            if (isRemembered) transition()
        }

        fun saveData(login:String, password:String){
            val editor:SharedPreferences.Editor = sharedPreferences.edit()
            editor.putString("LOGIN", login)
            editor.putString("PASSWORD", password)
            editor.putBoolean("CHECKBOX", true)
            editor.apply()
        }

        checkLogin()

        view.findViewById<Button>(R.id.enter).setOnClickListener {
            val log = view.findViewById<TextInputEditText>(R.id.log_et_log).text.toString().trim()
            val pass = view.findViewById<TextInputEditText>(R.id.log_et_pass).text.toString().trim()

            lifecycleScope.launchWhenResumed {
                userViewModel.getUser(log, pass)
                val userEntity = userViewModel.user
                if (userEntity != null) {
                    saveData(log,pass)
                    transition()
                } else {
                    Toast.makeText(context, "Пользователь не найден", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}
