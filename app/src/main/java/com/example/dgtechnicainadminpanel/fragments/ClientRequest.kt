package com.example.dgtechnicainadminpanel.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.Fragment
import android.view.ViewGroup
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dgtechnicainadminpanel.Adapter.RequestedAppointmentAdapter
import com.example.dgtechnicainadminpanel.DataModels.RequestAppointmentModel
import com.example.dgtechnicainadminpanel.R
import com.example.dgtechnicainadminpanel.databinding.FragmentClientRequestBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class ClientRequest : Fragment() {

    lateinit var binding: FragmentClientRequestBinding
    lateinit var db: FirebaseFirestore

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentClientRequestBinding.inflate(layoutInflater, container, false)

        db = FirebaseFirestore.getInstance()
        val techId = FirebaseAuth.getInstance().currentUser!!.uid

        db.collection("workers").document(techId).collection("requestedAppointment").addSnapshotListener { value, error ->

            val requestedAppointment = arrayListOf<RequestAppointmentModel>()
            val filteredList = arrayListOf<RequestAppointmentModel>()
            val data = value?.toObjects(RequestAppointmentModel::class.java)
            requestedAppointment.addAll(data!!)

            for (i in requestedAppointment) {
                if(i.status == "request") {
                    filteredList.add(i)
                }
            }

            Log.e("@@@", filteredList.toString())

            binding.requestedAppointmentRcv.layoutManager =
                LinearLayoutManager(requireContext())
            binding.requestedAppointmentRcv.adapter =
                RequestedAppointmentAdapter(requireContext(),
                    filteredList, "request", requireParentFragment())

        }

        binding.backBtn.setOnClickListener {

            NavHostFragment.findNavController(this).navigate(R.id.action_clientRequest_to_home2)

        }

        return binding.root
    }
}