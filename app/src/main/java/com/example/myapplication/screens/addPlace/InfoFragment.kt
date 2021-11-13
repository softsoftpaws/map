package com.example.myapplication.screens.addInfo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.myapplication.R
import com.example.myapplication.data.placeData.Place
import com.example.myapplication.databinding.FragmentInfoBinding
import com.example.myapplication.screens.MapViewModel


class InfoFragment : Fragment() {

    private lateinit var binding: FragmentInfoBinding
    private lateinit var mMapViewModel: MapViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        binding = FragmentInfoBinding.inflate(layoutInflater, container, false)
        mMapViewModel = ViewModelProvider(this)[MapViewModel::class.java]

        binding.placeSaveButton.setOnClickListener {
            val name = binding.placeNameEditText.text.toString().trim()
            val type = binding.placeTypeEditText.text.toString().trim()
            val days = binding.placeOpeningDaysEditText.text.toString().trim()
            val phone = binding.placePhoneEditText.text.toString().trim()
            val site = binding.placeSiteEditText.text.toString().trim()
            val comment = binding.placeCommentEditText.text.toString().trim()
            val args: InfoFragmentArgs by navArgs()
            val lat = args.lat.toDouble()
            val long = args.long.toDouble()

            val place = Place(0, name, type, days, phone, site, comment, lat, long)

            if (validate(name, type)) {
                mMapViewModel.insertPlace(place)
                findNavController().navigate(R.id.action_infoFragment_to_mapFragment)
            } else {
                Toast.makeText(context,
                    "Называние объекта и тип объекта обязательны к заполнению",
                    Toast.LENGTH_LONG).show()
            }
        }

        return binding.root
    }

    private fun validate(place_name: String, place_type: String): Boolean {
        return place_name.isNotBlank() && place_type.isNotBlank()
    }
}
