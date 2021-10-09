package com.example.myapplication.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentLoginBinding
import com.google.android.material.textfield.TextInputEditText


class LoginFragment:Fragment() {

    lateinit var binding: FragmentLoginBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(layoutInflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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

            if (result(log, pass)) {
                Toast.makeText(context, "Ура, вы вошли", Toast.LENGTH_LONG).show()

            } else if (!result(log, pass)) {
                Toast.makeText(context, "Поля заполнены неверно", Toast.LENGTH_LONG).show()

            }

        }
    }
}




