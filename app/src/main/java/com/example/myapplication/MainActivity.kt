package com.example.myapplication


import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.example.myapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)


        findViewById<Button>(R.id.registrBtn).setOnClickListener{
            if (findNavController(R.id.fragmentContainerView).currentDestination?.id!=R.id.registrationFragment){
                findNavController(R.id.fragmentContainerView).navigate(R.id.action_loginFragment_to_registrationFragment)
            }

        }

        findViewById<Button>(R.id.loginBtn).setOnClickListener{
            if (findNavController(R.id.fragmentContainerView).currentDestination?.id!=R.id.loginFragment){
                findNavController(R.id.fragmentContainerView).navigate(R.id.action_registrationFragment_to_loginFragment)
            }
        }




        }

    }



