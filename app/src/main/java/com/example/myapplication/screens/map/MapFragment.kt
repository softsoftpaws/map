package com.example.myapplication.screens.map

import android.Manifest
import android.content.Context
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.myapplication.databinding.FragmentMapBinding
import com.example.myapplication.screens.MapViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PointOfInterest
import java.util.Locale


class MapFragment : Fragment(), OnMapReadyCallback, GoogleMap.OnPoiClickListener {

    private lateinit var binding: FragmentMapBinding
    private lateinit var mMapViewModel: MapViewModel
    private lateinit var placeName: String
    private lateinit var mMap: GoogleMap
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        binding = FragmentMapBinding.inflate(inflater, container, false)

        val mapFragment =
            childFragmentManager.findFragmentById(com.example.myapplication.R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(this)
        sharedPreferences = requireContext().getSharedPreferences("SHARED_PREFERENCES", Context.MODE_PRIVATE)
        mMapViewModel = ViewModelProvider(this)[MapViewModel::class.java]
        return binding.root
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        googleMap.setOnPoiClickListener(this)
        currentLocation()

        googleMap.setOnMapLongClickListener { latlng ->
            googleMap.animateCamera(CameraUpdateFactory.newLatLng(latlng))
            val lat = latlng.latitude.toString()
            val long = latlng.longitude.toString()
            val address = getAddress(latlng.latitude, latlng.longitude)

            findNavController().navigate(MapFragmentDirections.actionMapFragmentToInfoFragment(lat, long, address))
        }
        with(binding) {
            layers.mapTypeDefaultImageButton.setOnClickListener {
                mMap.mapType = GoogleMap.MAP_TYPE_NORMAL
            }
            layers.mapTypeTerrainImageButton.setOnClickListener {
                mMap.mapType = GoogleMap.MAP_TYPE_TERRAIN
            }
            layers.mapTypeSatelliteImageButton.setOnClickListener {
                mMap.mapType = GoogleMap.MAP_TYPE_SATELLITE
            }
        }

        lifecycleScope.launchWhenResumed {
            val markers = mMapViewModel.getPlaces()

            for (marker in markers) {
                mMap.addMarker(
                    MarkerOptions().position(LatLng(marker.latitude, marker.longitude))
                        .title(marker.objectName)
                )
            }
        }

        googleMap.setOnMarkerClickListener { marker ->
            placeName = marker.title.toString()
            findNavController().navigate(MapFragmentDirections.actionMapFragmentToPlaceFragment(placeName))
            true
        }
    }

    override fun onPoiClick(poi: PointOfInterest) {
        Toast.makeText(context, poi.name, Toast.LENGTH_SHORT).show()

    }

    private fun currentLocation() {
        if (ActivityCompat.checkSelfPermission(requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
        ) {
            mMap.isMyLocationEnabled = true
            mMap.uiSettings.isMyLocationButtonEnabled = true
            return
        } else {
            Toast.makeText(context, "Доступ к местоположению запрещен", Toast.LENGTH_SHORT).show()
        }
    }

    private fun getAddress(lat: Double, lng: Double): String {
        val geocoder = Geocoder(context, Locale.getDefault())
        val addresses: List<Address> = geocoder.getFromLocation(lat, lng, 1)
        return addresses[0].getAddressLine(0)

    }
}





