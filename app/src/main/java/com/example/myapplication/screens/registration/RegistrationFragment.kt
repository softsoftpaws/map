package com.example.myapplication.screens.registration

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.myapplication.R
import com.example.myapplication.data.userData.User
import com.example.myapplication.databinding.FragmentRegistrationBinding
import com.example.myapplication.screens.UserViewModel

class RegistrationFragment : Fragment() {

    private lateinit var binding: FragmentRegistrationBinding
    private lateinit var mUserViewModel: UserViewModel

    @SuppressLint("UseSwitchCompatOrMaterialCode")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        binding = FragmentRegistrationBinding.inflate(inflater, container, false)
        mUserViewModel = ViewModelProvider(this)[UserViewModel::class.java]

        binding.regButton.setOnClickListener {

            val log = binding.logEditText.text.toString().trim()
            val mail = binding.mailEditText.text.toString().trim()
            val pass = binding.firstPassEditText.text.toString().trim()
            val pass2 = binding.secondPassEditText.text.toString().trim()
            val switch = binding.switchReg

            if (log.isBlank() || mail.isBlank() || pass.isBlank() || pass2.isBlank())
                Toast.makeText(context, "Все поля должны быть заполнены", Toast.LENGTH_LONG).show()
            else if (log.length < 6)
                Toast.makeText(
                    context, "Логин должен содержать не менее 6 символов", Toast.LENGTH_LONG).show()
            else if (!mail.contains("@", ignoreCase = true))
                Toast.makeText(context, "E-mail должен содержать @ ", Toast.LENGTH_LONG).show()
            else if (pass != pass2)
                Toast.makeText(context, "Пароли не совпадают", Toast.LENGTH_LONG).show()
            else if (pass.length < 6)
                Toast.makeText(context,
                    "Пароль должен содержать не менее 6 символов",
                    Toast.LENGTH_LONG).show()
            else if (!switch.isChecked)
                Toast.makeText(context, "Вы не согласились с правилами", Toast.LENGTH_LONG).show()
            else {
                val user = User(0, log, mail, pass)
                mUserViewModel.addUser(user)
                Toast.makeText(context, "Вы зарегистрировались", Toast.LENGTH_LONG).show()
                findNavController().navigate(R.id.action_registrationFragment_to_loginFragment)
            }
        }
        return binding.root
    }
}
