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
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions

class MapsFragment : Fragment() {
    private lateinit var mMapViewModel: MapViewModel
    lateinit var sharedPreferences: SharedPreferences
    private lateinit var marker: Marker
    private lateinit var place_name:String

    private val callback = OnMapReadyCallback { googleMap ->

        googleMap.setOnMapLongClickListener { latlng ->
            googleMap.animateCamera(CameraUpdateFactory.newLatLng(latlng))
            val lat = latlng.latitude.toString()
            val long = latlng.longitude.toString()
            val action = MapsFragmentDirections.actionMapsFragmentToInfoFragment(lat, long)
            findNavController().navigate(action)
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
            place_name = marker.title.toString()
            val action2 = MapsFragmentDirections.actionMapsFragmentToPlaceFragment(place_name)
            findNavController().navigate(action2)
            true
        }
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
        sharedPreferences = requireContext().getSharedPreferences("SHARED_PREFERENCES", Context.MODE_PRIVATE)
        mMapViewModel = ViewModelProvider(this).get(MapViewModel::class.java)
    }
}
