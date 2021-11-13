package com.example.myapplication


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import com.example.myapplication.databinding.ActivityRegistrarionLoginBinding

class RegistrationLoginActivity : AppCompatActivity() {

    private lateinit var binding:ActivityRegistrarionLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistrarionLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.registrationButton.setOnClickListener {
            binding.registrationButton.setTextColor(ContextCompat.getColor(applicationContext, R.color.white))
            binding.loginButton.setTextColor(ContextCompat.getColor(applicationContext, R.color.purple))
            pressButton(binding.registrationButton, binding.loginButton, true)
            if (findNavController(R.id.fragmentContainerView).currentDestination?.id != R.id.registrationFragment) {
                findNavController(R.id.fragmentContainerView).navigate(R.id.action_loginFragment_to_registrationFragment)
            }
        }
        binding.loginButton.setOnClickListener {
            binding.loginButton.setTextColor(ContextCompat.getColor(applicationContext, R.color.white))
            binding.registrationButton.setTextColor(ContextCompat.getColor(applicationContext, R.color.purple))
            pressButton(binding.registrationButton, binding.loginButton, false)
            if (findNavController(R.id.fragmentContainerView).currentDestination?.id != R.id.loginFragment) {
                findNavController(R.id.fragmentContainerView).navigate(R.id.action_registrationFragment_to_loginFragment)
            }
        }
    }

    private fun pressButton(unpressedButton: AppCompatButton, pressedButton: AppCompatButton, pressed: Boolean) {

        unpressedButton.isSelected = pressed
        pressedButton.isSelected = pressed
//        unpressedButton.setTextColor(ContextCompat.getColor(applicationContext, R.color.white))
//        pressedButton.setTextColor(ContextCompat.getColor(applicationContext, R.color.white))
    }
}
