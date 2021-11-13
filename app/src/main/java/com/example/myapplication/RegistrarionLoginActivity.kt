package com.example.myapplication


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.example.myapplication.databinding.ActivityRegistrarionLoginBinding

class RegistrationLoginActivity : AppCompatActivity() {

    private lateinit var binding:ActivityRegistrarionLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistrarionLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.registrationButton.setOnClickListener {
            if (findNavController(R.id.fragmentContainerView).currentDestination?.id != R.id.registrationFragment) {
                findNavController(R.id.fragmentContainerView).navigate(R.id.action_loginFragment_to_registrationFragment)
            }
        }
        binding.loginButton.setOnClickListener {
            if (findNavController(R.id.fragmentContainerView).currentDestination?.id != R.id.loginFragment) {
                findNavController(R.id.fragmentContainerView).navigate(R.id.action_registrationFragment_to_loginFragment)
            }
        }
    }
}
