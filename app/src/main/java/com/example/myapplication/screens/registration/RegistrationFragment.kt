package com.example.myapplication.screens.registration

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Switch
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.myapplication.R
import com.example.myapplication.data.User
import com.example.myapplication.screens.UserViewModel
import com.google.android.material.textfield.TextInputEditText

class RegistrationFragment : Fragment() {

    private lateinit var mUserViewModel: UserViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mUserViewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        view.findViewById<Button>(R.id.regbtn).setOnClickListener {
            val log = view.findViewById<TextInputEditText>(R.id.inputlog).text.toString().trim()
            val mail = view.findViewById<TextInputEditText>(R.id.inputmail).text.toString().trim()
            val pass = view.findViewById<TextInputEditText>(R.id.inputpass).text.toString().trim()
            val pass2 = view.findViewById<TextInputEditText>(R.id.inputpass2).text.toString().trim()

            if (validateCheck() && result(log, mail, pass, pass2)) {
                val user = User(0, log, mail, pass) //создание объекта
                mUserViewModel.addUser(user) //добавление в бд
                Toast.makeText(context, "Вы зарегистрировались", Toast.LENGTH_LONG).show()
                findNavController().navigate(R.id.action_registrationFragment_to_loginFragment)

            } else if (!result(log, mail, pass, pass2)) {
                Toast.makeText(context, "Поля заполнены неверно", Toast.LENGTH_LONG).show()

            } else if (!validateCheck()) {
                Toast.makeText(context, "Вы не согласились с правилами", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun validateEmailAddress(mail: String): Boolean {
        return mail.isNotBlank()
    }

    private fun validateLog(log: String): Boolean {
        return log.isNotBlank()
    }

    private fun validatePass(pass: String, pass2: String): Boolean {
        return pass.isNotBlank() && pass2.isNotBlank() && pass == pass2
    }

    private fun validateCheck(): Boolean {
        return view?.findViewById<Switch>(R.id.switch1)!!.isChecked
    }

    private fun result(log: String, mail: String, pass: String, pass2: String): Boolean {
        return validateLog(log) && validateEmailAddress(mail) && validatePass(pass, pass2)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_registration, container, false)
    }
}
