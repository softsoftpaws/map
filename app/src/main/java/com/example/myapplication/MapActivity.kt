package com.example.myapplication

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button

import androidx.appcompat.app.AppCompatActivity

class MapActivity : AppCompatActivity() {

    lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)

        sharedPreferences = getSharedPreferences("SHARED_PREFERENCES", Context.MODE_PRIVATE)

        findViewById<Button>(R.id.logOut).setOnClickListener {
            transition()
            deleteData()
        }
    }

    private fun transition() {
        val intent2 = Intent(this, RegistrationLoginActivity::class.java)
        startActivity(intent2)
    }

    private fun deleteData() {
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.clear().apply()
    }
}
