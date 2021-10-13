package com.example.myapplication.screens.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.myapplication.R
import com.example.myapplication.screens.UserViewModel
import com.example.myapplication.databinding.FragmentLoginBinding
import com.google.android.material.textfield.TextInputEditText


class LoginFragment : Fragment() {

    lateinit var binding: FragmentLoginBinding
    lateinit var mUserViewModel2: UserViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mUserViewModel2 = ViewModelProvider(this).get(UserViewModel::class.java)

        fun validaLog(log: String): Boolean {
            return !log.isNullOrBlank()
        }

        fun validPass(pass: String): Boolean {
            return !pass.isNullOrBlank()
        }

        fun result(log: String, pass: String): Boolean {
            return validaLog(log) && validPass(pass)
        }

        view.findViewById<Button>(R.id.enter).setOnClickListener {
            val log = view.findViewById<TextInputEditText>(R.id.log_et_log).text.toString().trim()
            val pass = view.findViewById<TextInputEditText>(R.id.log_et_pass).text.toString().trim()

            lifecycleScope.launchWhenResumed {
                mUserViewModel2.getUser(log, pass)
                val userEntity = mUserViewModel2.user
                if (userEntity != null) {
                    Toast.makeText(context, "Пользователь найден", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(context, "Пользователь не найден", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}
