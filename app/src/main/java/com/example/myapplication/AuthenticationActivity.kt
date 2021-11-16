package com.example.myapplication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import com.example.myapplication.databinding.ActivityAuthenticationBinding

class RegistrationLoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAuthenticationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthenticationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        with(binding) {
            registrationButton.setOnClickListener {
                registrationButton.setTextColor(R.color.white.getCurrentColor())
                loginButton.setTextColor(R.color.purple.getCurrentColor())
                pressButton(registrationButton, loginButton, true)

                if (findNavController(R.id.fragmentContainerView).currentDestination?.id != R.id.registrationFragment) {
                    findNavController(R.id.fragmentContainerView).navigate(R.id.action_loginFragment_to_registrationFragment)
                }
            }
            loginButton.setOnClickListener {
                loginButton.setTextColor(R.color.white.getCurrentColor())
                registrationButton.setTextColor(R.color.purple.getCurrentColor())
                pressButton(registrationButton, loginButton, false)

                if (findNavController(R.id.fragmentContainerView).currentDestination?.id != R.id.loginFragment) {
                    findNavController(R.id.fragmentContainerView).navigate(R.id.action_registrationFragment_to_loginFragment)
                }
            }
        }
    }

    private fun Int.getCurrentColor() = ContextCompat.getColor(applicationContext, this)

    private fun pressButton(unpressedButton: AppCompatButton, pressedButton: AppCompatButton, pressed: Boolean) {
        unpressedButton.isSelected = pressed
        pressedButton.isSelected = pressed
    }
}
