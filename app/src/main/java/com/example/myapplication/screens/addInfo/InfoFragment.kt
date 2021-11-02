package com.example.myapplication.screens.addInfo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.myapplication.R
import com.example.myapplication.data.placeData.Place
import com.example.myapplication.screens.MapViewModel
import com.google.android.material.textfield.TextInputEditText


class InfoFragment : Fragment() {

    private lateinit var mMapViewModel: MapViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_info, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mMapViewModel = ViewModelProvider(this).get(MapViewModel::class.java)

        fun validate(place_name: String, place_type: String): Boolean {
            return place_name.isNotBlank() && place_type.isNotBlank()
        }

        view.findViewById<AppCompatButton>(R.id.place_save).setOnClickListener {
            val name = view.findViewById<TextInputEditText>(R.id.place_name_edit).text.toString().trim()
            val type = view.findViewById<TextInputEditText>(R.id.place_type_edit).text.toString().trim()
            val days = view.findViewById<TextInputEditText>(R.id.place_opening_days_edit).text.toString().trim()
            val phone = view.findViewById<TextInputEditText>(R.id.place_phone_edit).text.toString().trim()
            val site = view.findViewById<TextInputEditText>(R.id.place_site_edit).text.toString().trim()
            val comment = view.findViewById<TextInputEditText>(R.id.place_comment_edit).text.toString().trim()

            val args: InfoFragmentArgs by navArgs()
            val lat = args.lat.toDouble()
            val long = args.long.toDouble()
            val place = Place(0, name, type, days, phone, site, comment, lat, long) //создание объекта

            if (validate(name, type)) {
                mMapViewModel.insertPlace(place)
                findNavController().navigate(R.id.action_infoFragment_to_mapsFragment)
            } else {
                Toast.makeText(context, "Называние объекта и тип объекта обязательны к заполнению", Toast.LENGTH_LONG).show()
            }
        }
    }
}
