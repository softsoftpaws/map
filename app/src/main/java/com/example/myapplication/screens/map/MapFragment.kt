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
import android.widget.RelativeLayout
import android.widget.SearchView
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
        positionLocationButton()
        positionCompass()

        googleMap.setOnMapLongClickListener { latlng ->
            googleMap.animateCamera(CameraUpdateFactory.newLatLng(latlng))
            val lat = latlng.latitude.toString()
            val long = latlng.longitude.toString()
            val address = getAddress(latlng.latitude, latlng.longitude)
            findNavController().navigate(MapFragmentDirections.actionMapFragmentToInfoFragment(lat, long, address))
        }


        binding.searchPlaceView.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            androidx.appcompat.widget.SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String?): Boolean {
                val searchPlaceName = binding.searchPlaceView.query.toString()
                if (searchPlaceName.isNotBlank()) {
                    try {
                        val locationAddress = Geocoder(requireContext()).getFromLocationName(searchPlaceName, 1)[0]
                        if (locationAddress != null) {
                            val placeCoordinates = LatLng(locationAddress.latitude, locationAddress.longitude)
                            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(placeCoordinates, 9f))
                        }
                    } catch (e: Exception) {
                        println(e)
                    }
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })

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

    private fun positionLocationButton() {
        val locationButton =
            (view?.findViewById<View>(Integer.parseInt("1"))?.parent as View).findViewById<View>(Integer.parseInt("2"))
        val rlp = locationButton.layoutParams as (RelativeLayout.LayoutParams)
        rlp.addRule(RelativeLayout.ALIGN_PARENT_TOP, 0)
        rlp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE)
        rlp.setMargins(0, 0, 50, 200)
    }

    private fun positionCompass() {
        val locationCompass =
            (view?.findViewById<View>(Integer.parseInt("1"))?.parent as View).findViewById<View>(Integer.parseInt("5"))
        val layoutParams = locationCompass.layoutParams as (RelativeLayout.LayoutParams)
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP, 0)
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE)
        layoutParams.setMargins(0, 0, 0, 375)
    }
}
