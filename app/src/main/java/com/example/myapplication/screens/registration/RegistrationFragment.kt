package com.example.myapplication.screens.registration

import android.annotation.SuppressLint
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
import com.example.myapplication.data.userData.User
import com.example.myapplication.screens.UserViewModel
import com.google.android.material.textfield.TextInputEditText

class RegistrationFragment : Fragment() {

    private lateinit var mUserViewModel: UserViewModel

    @SuppressLint("UseSwitchCompatOrMaterialCode")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mUserViewModel = ViewModelProvider(this).get(UserViewModel::class.java)
        val registrationView = inflater.inflate(R.layout.fragment_registration, container, false)
        registrationView?.findViewById<Button>(R.id.regbtn)?.setOnClickListener {
            val log =
                registrationView.findViewById<TextInputEditText>(R.id.inputlog).text.toString()
                    .trim()
            val mail =
                registrationView.findViewById<TextInputEditText>(R.id.inputmail).text.toString()
                    .trim()
            val pass =
                registrationView.findViewById<TextInputEditText>(R.id.inputpass).text.toString()
                    .trim()
            val pass2 =
                registrationView.findViewById<TextInputEditText>(R.id.inputpass2).text.toString()
                    .trim()
            val switch = registrationView.findViewById<Switch>(R.id.switch1)

            if (log.isBlank() || mail.isBlank() || pass.isBlank() || pass2.isBlank())
                Toast.makeText(context, "Все поля должны быть заполнены", Toast.LENGTH_LONG).show()
            else if (log.length < 6)
                Toast.makeText(
                    context,
                    "Логин должен содержать не менее 6 символов",
                    Toast.LENGTH_LONG
                ).show()
            else if (!mail.contains("@", ignoreCase = true))
                Toast.makeText(context, "E-mail должен содержать @ ", Toast.LENGTH_LONG).show()
            else if (pass != pass2)
                Toast.makeText(context, "Пароли не совпадают", Toast.LENGTH_LONG).show()
            else if (pass.length < 6)
                Toast.makeText(
                    context,
                    "Пароль должен содержать не менее 6 символов",
                    Toast.LENGTH_LONG
                ).show()
            else if (!switch.isChecked)
                Toast.makeText(context, "Вы не согласились с правилами", Toast.LENGTH_LONG).show()
            else {
                val user = User(0, log, mail, pass)
                mUserViewModel.addUser(user)
                Toast.makeText(context, "Вы зарегистрировались", Toast.LENGTH_LONG).show()
                findNavController().navigate(R.id.action_registrationFragment_to_loginFragment)
            }
        }
        return registrationView
    }
}
