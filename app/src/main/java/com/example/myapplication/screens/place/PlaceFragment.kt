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
import com.bumptech.glide.Glide
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentPlaceBinding
import com.example.myapplication.screens.MapViewModel

class PlaceFragment : Fragment() {

    private lateinit var binding: FragmentPlaceBinding
    private lateinit var mMapViewModel: MapViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentPlaceBinding.inflate(layoutInflater, container, false)
        val args: PlaceFragmentArgs by navArgs()
        val placeName = args.placeName
        mMapViewModel = ViewModelProvider(this)[MapViewModel::class.java]

        lifecycleScope.launchWhenStarted {
            mMapViewModel.getPlace(placeName)
            val placeEntity = mMapViewModel.placeDto

            binding.nameTextView.text = placeEntity?.objectName
            binding.typeTextView.text = placeEntity?.objectType

            with(binding) {
                if (placeEntity != null) {
                    if (placeEntity.placeImage.isBlank()) {
                        Glide
                            .with(requireActivity())
                            .load("https://cs11.pikabu.ru/post_img/2019/02/04/12/1549312329147951618.jpg")
                            .centerCrop()
                            .into(imagePlace)
                    } else {
                        val placeImage = placeEntity.placeImage
                        Glide
                            .with(requireActivity())
                            .load(placeImage)
                            .centerCrop()
                            .into(imagePlace)
                    }
                    if (placeEntity.phone.isBlank()) {
                        phoneTextView.text = getString(R.string.Info)
                    } else {
                        phoneTextView.text = placeEntity.phone
                    }
                    if (placeEntity.site.isBlank()) {
                        siteTextView.text = getString(R.string.Info)
                    } else {
                        siteTextView.text = placeEntity.site
                    }
                    if (placeEntity.comments.isBlank()) {
                        commentText.text = getString(R.string.comment)
                    } else {
                        commentText.text = placeEntity.comments
                    }
                    if (placeEntity.address.isBlank()) {
                        addressTextView.text = getString(R.string.Info)
                    } else {
                        addressTextView.text = placeEntity.address
                    }
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
