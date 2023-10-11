package com.example.dgtechnicainadminpanel.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.NavHostFragment
import com.bumptech.glide.Glide
import com.example.dgtechnicainadminpanel.R
import com.example.dgtechnicainadminpanel.databinding.FragmentProfileBinding
import com.google.firebase.auth.FirebaseAuth

class Profile : Fragment() {

    lateinit var binding: FragmentProfileBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentProfileBinding.inflate(layoutInflater, container, false)

        auth = FirebaseAuth.getInstance()

        val wName = arguments?.getString("wName").toString().trim()
        val wImageUrl = arguments?.getString("wImageUrl").toString().trim()

        Glide.with(requireContext()).load(wImageUrl).into(binding.techImage)

        binding.wName.text = wName

        binding.home.setOnClickListener {

            NavHostFragment.findNavController(this).navigate(R.id.action_profile_to_home2)

        }

        binding.appointments.setOnClickListener {

            NavHostFragment.findNavController(this).navigate(R.id.action_profile_to_clientsList)

        }

        binding.requestedAppointments.setOnClickListener {

            NavHostFragment.findNavController(this).navigate(R.id.action_profile_to_clientRequest)

        }

//        binding.search.setOnClickListener {
//
//            NavHostFragment.findNavController(this).navigate(R.id.action_profile_to_search)
//
//        }

        binding.logOut.setOnClickListener {

            auth.signOut()
            NavHostFragment.findNavController(this).navigate(R.id.action_profile_to_splash)

        }

        return binding.root
    }
}