package com.example.myapplication.screens.listOfPlaces

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.FragmentPlaceListBinding
import com.example.myapplication.screens.MapViewModel

class PlaceListFragment : Fragment() {

    private lateinit var binding: FragmentPlaceListBinding
    private lateinit var mMapViewModel: MapViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentPlaceListBinding.inflate(inflater, container, false)
        val adapter = ListAdapter()

        binding.swipeList.setOnRefreshListener {
            binding.swipeList.isRefreshing = false
        }
        val recyclerView: RecyclerView = binding.recyclerView
        recyclerView.adapter = adapter
        //recyclerView.addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        mMapViewModel = ViewModelProvider(this)[MapViewModel::class.java]
        mMapViewModel.readAllData.observe(viewLifecycleOwner, Observer { place ->
            adapter.setData(place)
        })

        adapter.onItemClick = { currentItem ->
            val placeName = currentItem.object_name
            findNavController().navigate(PlaceListFragmentDirections.actionPlacesFragmentToPlaceFragment(
                placeName))
        }
        return binding.root
    }
}
