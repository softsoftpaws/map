package com.example.myapplication.screens.profile

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.myapplication.R
import com.example.myapplication.RegistrationLoginActivity
import com.example.myapplication.screens.UserViewModel

class ProfileFragment : Fragment() {
    private lateinit var mUserViewModel: UserViewModel
    lateinit var sharedPreferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    fun transition() {
        val intent2 = Intent(context, RegistrationLoginActivity::class.java)
        startActivity(intent2)
    }

    fun deleteData() {
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.clear().apply()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mUserViewModel = ViewModelProvider(this).get(UserViewModel::class.java)
        sharedPreferences =
            requireContext().getSharedPreferences("SHARED_PREFERENCES", Context.MODE_PRIVATE)


        val log = sharedPreferences.getString("LOGIN", "").toString()
        val pass = sharedPreferences.getString("PASSWORD", "").toString()

        lifecycleScope.launchWhenResumed {
            mUserViewModel.getUser(log, pass)
            val userEntity = mUserViewModel.user
            view.findViewById<TextView>(R.id.loginText).text = userEntity?.login
            view.findViewById<TextView>(R.id.mailText).text = userEntity?.mail
        }
        view.findViewById<Button>(R.id.exit).setOnClickListener {
            transition()
            deleteData()
        }
    }
}