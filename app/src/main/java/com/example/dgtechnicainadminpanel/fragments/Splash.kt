package com.example.dgtechnicainadminpanel.fragments

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.NavHostFragment
import com.example.dgtechnicainadminpanel.DataModel.CatModel
import com.example.dgtechnicainadminpanel.R
import com.example.dgtechnicainadminpanel.databinding.FragmentSplashBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class Splash : Fragment() {

    lateinit var binding: FragmentSplashBinding
    private lateinit var auth: FirebaseAuth
    lateinit var db: FirebaseFirestore

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSplashBinding.inflate(layoutInflater, container, false)

        db = FirebaseFirestore.getInstance()
        auth = FirebaseAuth.getInstance()
        val currentUser = auth.currentUser

        Handler(Looper.getMainLooper()).postDelayed({

            if (currentUser != null) {


                val techId = currentUser!!.uid
                val ref = db.collection("workers").document(techId)

                    ref.addSnapshotListener { snapshot, error ->
                        if (error != null) {
                            return@addSnapshotListener
                        }

                        var status = snapshot?.getString("status")
                        val message = if (status == "active") {

                            // Reference to the "slots" collection
                            val slotsCollectionRef =
                                db.collection("workers").document(techId).collection("slots")

                            slotsCollectionRef.get()
                                .addOnSuccessListener { querySnapshot ->
                                    if (!querySnapshot.isEmpty) {

                                        Handler(Looper.getMainLooper()).postDelayed({

                                        NavHostFragment.findNavController(this).navigate(R.id.action_splash_to_home22)

                                        }, 3000)
                                        
                                        }
                                    else {
                                        
                                    }
                                }
                                .addOnFailureListener { e ->
                                    // Handle any errors that occurred while fetching the documents
                                    // This could be due to network issues or Firestore security rules
                                    Log.e("Firestore", "Error getting documents: $e")

                                }
                        }
                        else {
                            NavHostFragment.findNavController(this).navigate(R.id.action_splash_to_personalInfo)
                        }

                    }

            } else {

                NavHostFragment.findNavController(this)
                    .navigate(R.id.action_splash_to_getStarted)

            }

        }, 3000)

        return binding.root
    }
}