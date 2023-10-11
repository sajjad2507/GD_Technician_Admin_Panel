package com.example.dgtechnicainadminpanel.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dgtechnicainadminpanel.Adapter.RequestedAppointmentAdapter
import com.example.dgtechnicainadminpanel.DataModels.RequestAppointmentModel
import com.example.dgtechnicainadminpanel.databinding.FragmentClientsListBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class ClientsList : Fragment() {

    lateinit var binding: FragmentClientsListBinding
    lateinit var db: FirebaseFirestore

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentClientsListBinding.inflate(layoutInflater, container, false)

        db = FirebaseFirestore.getInstance()
        val techId = FirebaseAuth.getInstance().currentUser!!.uid

        db.collection("workers").document(techId).collection("requestedAppointment").addSnapshotListener { value, error ->

            val requestedAppointment = arrayListOf<RequestAppointmentModel>()
            val filteredList = arrayListOf<RequestAppointmentModel>()
            val data = value?.toObjects(RequestAppointmentModel::class.java)
            requestedAppointment.addAll(data!!)

            for (i in requestedAppointment) {
                if(i.status == "booked") {
                    filteredList.add(i)
                }
            }

            Log.e("@@@", filteredList.toString())

            binding.bookedAppointmentRcv.layoutManager =
                LinearLayoutManager(requireContext())
            binding.bookedAppointmentRcv.adapter =
                RequestedAppointmentAdapter(requireContext(),
                    filteredList, "booked", requireParentFragment())

        }

        return binding.root
    }
}