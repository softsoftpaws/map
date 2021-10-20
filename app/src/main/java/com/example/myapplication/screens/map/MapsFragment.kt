package com.example.myapplication.screens.map

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.myapplication.R
import com.example.myapplication.RegistrationLoginActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MapsFragment : Fragment() {

    lateinit var sharedPreferences: SharedPreferences

    private val callback = OnMapReadyCallback { googleMap ->
        googleMap.setOnMapLongClickListener { latlng ->
        googleMap.animateCamera(CameraUpdateFactory.newLatLng(latlng))
            val location = LatLng(latlng.latitude, latlng.longitude)
            googleMap.addMarker(MarkerOptions().position(location))
        }
        googleMap.setOnPoiClickListener { poi ->
            Toast.makeText(context, """Clicked: ${poi.name}
            Latitude:${poi.latLng.latitude} Longitude:${poi.latLng.longitude}""", Toast.LENGTH_SHORT
            ).show()
        }
    }

    fun transition() {
        val intent2 = Intent(context, RegistrationLoginActivity::class.java)
        startActivity(intent2)
    }

    fun deleteData() {
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.clear().apply()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_maps, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        val mapAsync = mapFragment?.getMapAsync(callback)
        sharedPreferences =
            requireContext().getSharedPreferences("SHARED_PREFERENCES", Context.MODE_PRIVATE)

        view.findViewById<FloatingActionButton>(R.id.exit).setOnClickListener {
            transition()
            deleteData()
        }
    }
}








