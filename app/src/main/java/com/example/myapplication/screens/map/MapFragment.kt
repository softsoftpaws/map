package com.example.myapplication.screens.map

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentMapBinding
import com.example.myapplication.screens.MapViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PointOfInterest

class MapFragment : Fragment(), OnMapReadyCallback, GoogleMap.OnPoiClickListener {

    private lateinit var binding:FragmentMapBinding
    private lateinit var mMapViewModel: MapViewModel
    lateinit var sharedPreferences: SharedPreferences
    private lateinit var placeName: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentMapBinding.inflate(inflater,container,false)

        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        val mapAsync = mapFragment?.getMapAsync(this)
        sharedPreferences =
            requireContext().getSharedPreferences("SHARED_PREFERENCES", Context.MODE_PRIVATE)
        mMapViewModel = ViewModelProvider(this)[MapViewModel::class.java]

//        val lat = -7.316463
//        val lng = 112.748348
//        val address = getAddress(lat, lng)
//        Toast.makeText(context, address, Toast.LENGTH_LONG).show()

        return binding.root
    }

    override fun onMapReady(googleMap: GoogleMap) {

        googleMap.setOnPoiClickListener(this)

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

    override fun onPoiClick(poi: PointOfInterest) {
        Toast.makeText(context, """Clicked: ${poi.name}
            Place ID:${poi.placeId}
            Latitude:${poi.latLng.latitude} Longitude:${poi.latLng.longitude}""",
            Toast.LENGTH_SHORT
        ).show()
    }
    //    private fun getAddress(lat: Double, lng: Double): String {
//        val geocoder = Geocoder(context)
//        val list = geocoder.getFromLocation(lat, lng, 1)
//        return list[0].getAddressLine(0)
//    }

}
