package com.example.myapplication.screens.place

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.myapplication.R
import com.example.myapplication.screens.MapViewModel

class PlaceFragment : Fragment() {

    private lateinit var mMapViewModel: MapViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_place, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mMapViewModel = ViewModelProvider(this).get(MapViewModel::class.java)
        val args: PlaceFragmentArgs by navArgs()
        val placeName = args.placeName

        lifecycleScope.launchWhenStarted {
            mMapViewModel.getPlace(placeName)
            val placeEntity = mMapViewModel.place

            view.findViewById<TextView>(R.id.name).text = placeEntity?.object_name
            view.findViewById<TextView>(R.id.type).text = placeEntity?.object_type
            view.findViewById<TextView>(R.id.days).text = placeEntity?.opening_days
            view.findViewById<TextView>(R.id.phone).text = placeEntity?.phone
            view.findViewById<TextView>(R.id.site).text = placeEntity?.site
        }

        view.findViewById<Button>(R.id.deletePlace).setOnClickListener {
            mMapViewModel.deletePlace(placeName)
            findNavController().navigate(R.id.action_placeFragment_to_mapsFragment)
        }
    }
}
