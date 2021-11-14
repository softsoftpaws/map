package com.example.myapplication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.myapplication.databinding.ActivityMapBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class MapActivity : AppCompatActivity() {
    lateinit var bindingClass: ActivityMapBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingClass = ActivityMapBinding.inflate(layoutInflater)
        setContentView(bindingClass.root)
        val navView: BottomNavigationView = bindingClass.navView
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerViewMap) as NavHostFragment
        val navController = navHostFragment.navController
        navView.setupWithNavController(navController)
    }
}
