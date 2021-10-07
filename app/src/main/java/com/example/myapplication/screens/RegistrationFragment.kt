package com.example.myapplication.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Switch
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentRegistrationBinding
import com.google.android.material.textfield.TextInputEditText


class RegistrationFragment:Fragment() {


    lateinit var binding: FragmentRegistrationBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegistrationBinding.inflate(layoutInflater,container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fun validateEmailAddress(mail:String): Boolean {
            return !mail.isNullOrBlank()
        }

        fun validateLog(log:String):Boolean{
            return !log.isNullOrBlank()
        }

        fun validatePass(pass:String, pass2:String): Boolean {
            return !pass.isNullOrBlank() && !pass2.isNullOrBlank() && pass == pass2
        }

        fun validateCheck(): Boolean {
            return view.findViewById<Switch>(R.id.switch1).isChecked
        }

        fun result(log:String, mail:String, pass:String, pass2:String): Boolean {
            return validateLog(log) && validateEmailAddress(mail) && validatePass(pass, pass2)
        }

        view.findViewById<Button>(R.id.regbtn).setOnClickListener {
            val log = view.findViewById<TextInputEditText>(R.id.inputlog).text.toString().trim()
            val mail = view.findViewById<TextInputEditText>(R.id.inputmail).text.toString().trim()
            val pass = view.findViewById<TextInputEditText>(R.id.inputpass).text.toString().trim()
            val pass2 = view.findViewById<TextInputEditText>(R.id.inputpass2).text.toString().trim()

            if (validateCheck() && result(log, mail, pass, pass2)) {
                findNavController().navigate(R.id.action_registrationFragment_to_loginFragment)

            } else if (!result(log, mail, pass, pass2)) {
                Toast.makeText(context,  "Поля заполнены не верно", Toast.LENGTH_LONG).show()

            } else if (!validateCheck()) {
                Toast.makeText(context, "Вы не согласились с правилами", Toast.LENGTH_LONG).show()

            }
        }
    }
}






