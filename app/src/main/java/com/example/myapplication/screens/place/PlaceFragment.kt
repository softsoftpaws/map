package com.example.myapplication.screens.place

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentPlaceBinding
import com.example.myapplication.screens.MapViewModel

class PlaceFragment : Fragment() {

    private lateinit var binding: FragmentPlaceBinding
    private lateinit var mMapViewModel: MapViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentPlaceBinding.inflate(layoutInflater, container, false)
        mMapViewModel = ViewModelProvider(this)[MapViewModel::class.java]

        val args: PlaceFragmentArgs by navArgs()
        val placeName = args.placeName

        lifecycleScope.launchWhenStarted {
            mMapViewModel.getPlace(placeName)
            val placeEntity = mMapViewModel.place

            binding.nameTextView.text = placeEntity?.object_name
            binding.typeTextView.text = placeEntity?.object_type

            if (placeEntity != null) {
                if (placeEntity.opening_days.isBlank()) {
                    binding.daysTextView.text = "Информация отсутствует"
                } else {
                    binding.daysTextView.text = placeEntity.opening_days
                }
                if (placeEntity.phone.isBlank()) {
                    binding.phoneTextView.text = "Информация отсутствует"
                } else {
                    binding.phoneTextView.text = placeEntity.phone
                }
                if (placeEntity.site.isBlank()) {
                    binding.siteTextView.text = "Информация отсутствует"
                } else {
                    binding.siteTextView.text = placeEntity.site
                }
            }
        }

        binding.deletePlaceButton.setOnClickListener {
            mMapViewModel.deletePlace(placeName)
            findNavController().navigate(R.id.action_placeFragment_to_mapFragment)
        }
        return binding.root
    }
}
