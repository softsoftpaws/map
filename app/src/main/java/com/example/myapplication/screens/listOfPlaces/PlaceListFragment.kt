package com.example.myapplication.screens.listOfPlaces

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.screens.MapViewModel


class PlaceListFragment : Fragment() {
    private lateinit var mMapViewModel: MapViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val view = inflater.inflate(R.layout.fragment_place_list, container, false)
        val adapter = ListAdapter()
        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerview)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        mMapViewModel = ViewModelProvider(this).get(MapViewModel::class.java)
        mMapViewModel.readAllData.observe(viewLifecycleOwner, Observer { place ->
            adapter.setData(place)
        })

        return view
    }
}