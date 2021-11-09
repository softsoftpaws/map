package com.example.myapplication.screens.map

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.myapplication.R
import com.example.myapplication.screens.MapViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapFragment : Fragment() {
    private lateinit var mMapViewModel: MapViewModel
    lateinit var sharedPreferences: SharedPreferences
    private lateinit var placeName: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val view = inflater.inflate(R.layout.fragment_map, container, false)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        val mapAsync = mapFragment?.getMapAsync(callback)
        sharedPreferences =
            requireContext().getSharedPreferences("SHARED_PREFERENCES", Context.MODE_PRIVATE)
        mMapViewModel = ViewModelProvider(this)[MapViewModel::class.java]
        return view
    }

    private val callback = OnMapReadyCallback { googleMap ->

        googleMap.setOnMapLongClickListener { latlng ->
            googleMap.animateCamera(CameraUpdateFactory.newLatLng(latlng))
            val lat = latlng.latitude.toString()
            val long = latlng.longitude.toString()
            findNavController().navigate(MapFragmentDirections.actionMapFragmentToInfoFragment(lat,
                long))
        }

        lifecycleScope.launchWhenResumed {
            val markers = mMapViewModel.getPlaces()
            val mMap = googleMap
            for (marker in markers) {
                mMap.addMarker(
                    MarkerOptions().position(LatLng(marker.latitude, marker.longitude))
                        .title(marker.object_name)
                )
            }
        }
        googleMap.setOnMarkerClickListener { marker ->
            placeName = marker.title.toString()
            findNavController().navigate(MapFragmentDirections.actionMapFragmentToPlaceFragment(
                placeName))
            true
        }
    }
}
