package com.example.myapplication.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Switch
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentRegistrationBinding



class RegistrationFragment:Fragment() {


    lateinit var binding: FragmentRegistrationBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegistrationBinding.inflate(layoutInflater,container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



            view.findViewById<Button>(R.id.regbtn).setOnClickListener {
               if(view.findViewById<Switch>(R.id.switch1).isChecked){
                        findNavController().navigate(R.id.action_registrationFragment_to_loginFragment)
                    }else{
                   Toast.makeText(context, "Вы не согласились с правилами", Toast.LENGTH_LONG).show();
               }
                }
            }

        }





