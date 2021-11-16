package com.example.myapplication.screens.list_of_places

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.myapplication.databinding.FragmentPlacesBinding
import com.example.myapplication.screens.MapViewModel

class PlaceListFragment : Fragment() {

    private lateinit var binding: FragmentPlacesBinding
    private lateinit var mMapViewModel: MapViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentPlacesBinding.inflate(inflater, container, false)
        val adapter = ListAdapter()
        binding.recyclerView.adapter = adapter

        mMapViewModel = ViewModelProvider(this)[MapViewModel::class.java]
        mMapViewModel.readAllData.observe(viewLifecycleOwner, { place ->
            adapter.setData(place)
        })
        adapter.onItemClick = { currentItem ->
            val placeName = currentItem.objectName
            findNavController().navigate(PlaceListFragmentDirections.actionPlacesFragmentToPlaceFragment(placeName))
        }
        return binding.root
    }
}
