package com.example.dgtechnicainadminpanel.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.NavHostFragment
import com.bumptech.glide.Glide
import com.example.dgtechnicainadminpanel.R
import com.example.dgtechnicainadminpanel.databinding.FragmentGetStartedBinding

class GetStarted : Fragment() {

    lateinit var binding: FragmentGetStartedBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentGetStartedBinding.inflate(layoutInflater, container, false)

        Glide.with(requireActivity()).load(R.drawable.img).into(binding.banner)

//        Glide.with(requireActivity()).load(R.drawable.started).into(binding.getStarted)

        binding.getStarted.setOnClickListener {

            NavHostFragment.findNavController(this)
                .navigate(R.id.action_getStarted_to_logIn)

        }

        return binding.root
    }
}