package com.example.myapplication.screens.profile

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.myapplication.RegistrationLoginActivity
import com.example.myapplication.databinding.FragmentProfileBinding
import com.example.myapplication.screens.UserViewModel

class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding
    private lateinit var mUserViewModel: UserViewModel
    lateinit var sharedPreferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        binding = FragmentProfileBinding.inflate(layoutInflater, container, false)
        mUserViewModel = ViewModelProvider(this)[UserViewModel::class.java]
        sharedPreferences =
            requireContext().getSharedPreferences("SHARED_PREFERENCES", Context.MODE_PRIVATE)

        val logText = sharedPreferences.getString("LOGIN", "").toString()
        val passText = sharedPreferences.getString("PASSWORD", "").toString()

        lifecycleScope.launchWhenResumed {
            mUserViewModel.getUser(logText, passText)
            val userEntity = mUserViewModel.user
            binding.loginText.text = userEntity?.login
            binding.mailText.text = userEntity?.mail
        }
        binding.exitButton.setOnClickListener {
            val editor: SharedPreferences.Editor = sharedPreferences.edit()
            editor.clear().apply()
            startActivity(Intent(context, RegistrationLoginActivity::class.java))
        }
        return binding.root
    }
}