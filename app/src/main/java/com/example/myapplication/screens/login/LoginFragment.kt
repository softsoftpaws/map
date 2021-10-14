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
import com.example.myapplication.databinding.FragmentLoginBinding
import com.example.myapplication.screens.UserViewModel
import com.google.android.material.textfield.TextInputEditText

class LoginFragment : Fragment() {

    lateinit var binding: FragmentLoginBinding
    lateinit var mUserViewModel2: UserViewModel
    lateinit var sharedPreferences: SharedPreferences
    var isRemembered = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val context = requireContext()

        mUserViewModel2 = ViewModelProvider(this).get(UserViewModel::class.java)
        sharedPreferences = requireContext().getSharedPreferences("SHARED_PREFERENCES", Context.MODE_PRIVATE)
        isRemembered = sharedPreferences.getBoolean("CHECKBOX", false)

        fun checkLogin(){
            if (isRemembered){
                val intent2 = Intent(activity, MapActivity::class.java)
                startActivity(intent2);
            }
        }
        checkLogin()

        fun saveData(login:String, password:String){

            val editor:SharedPreferences.Editor = sharedPreferences.edit()
            editor.putString("LOGIN", login)
            editor.putString("PASSWORD", password)
            editor.putBoolean("CHECKBOX", true)
            editor.apply()

            val intent = Intent(activity, MapActivity::class.java)
            startActivity(intent)
        }

        view.findViewById<Button>(R.id.enter).setOnClickListener {
            val log = view.findViewById<TextInputEditText>(R.id.log_et_log).text.toString().trim()
            val pass = view.findViewById<TextInputEditText>(R.id.log_et_pass).text.toString().trim()

            lifecycleScope.launchWhenResumed {
                mUserViewModel2.getUser(log, pass)
                val userEntity = mUserViewModel2.user
                if (userEntity != null) {
                        saveData(log,pass)

                } else {
                    Toast.makeText(context, "Пользователь не найден", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}
