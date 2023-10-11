package com.example.dgtechnicainadminpanel.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.navigation.fragment.NavHostFragment
import com.example.dgtechnicainadminpanel.DataModel.AppointmentType
import com.example.dgtechnicainadminpanel.DataModel.CatModel
import com.example.dgtechnicainadminpanel.DataModel.CatgoryModel
import com.example.dgtechnicainadminpanel.DataModel.SubCatModel
import com.example.dgtechnicainadminpanel.DataModels.PersonalInfoModel
import com.example.dgtechnicainadminpanel.R
import com.example.dgtechnicainadminpanel.databinding.FragmentCategoryInfoBinding
import com.google.android.material.chip.Chip
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class CategoryInfo : Fragment() {

    lateinit var binding: FragmentCategoryInfoBinding
    lateinit var db: FirebaseFirestore
    var suggestion = mutableListOf<String>()
    var suggestion1 = mutableListOf<String>()
    var suggestion2 = mutableListOf<String>()
    var suggestionId = mutableListOf<String>()
    var suggestionId1 = mutableListOf<String>()
    var suggestionId2 = mutableListOf<String>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentCategoryInfoBinding.inflate(layoutInflater, container, false)

        db = FirebaseFirestore.getInstance()

        binding.yourInputField1.isEnabled = false

        db.collection("typesofworkers").addSnapshotListener { value, error ->

            val catList = arrayListOf<CatModel>()
            val data = value?.toObjects(CatModel::class.java)
            catList.addAll(data!!)

            for (i in catList) {
                suggestion.add(i.name)
            }

            val adapter = ArrayAdapter(
                requireContext(),
                android.R.layout.simple_dropdown_item_1line,
                suggestion
            )
            val autoCompleteTextView = binding.yourInputField
            autoCompleteTextView.setAdapter(adapter)

            autoCompleteTextView.onItemClickListener =
                AdapterView.OnItemClickListener { parent, view, position, id ->
                    val selectedCategory = parent.getItemAtPosition(position).toString()

                    binding.yourInputField1.text.clear()

                    Toast.makeText(
                        requireContext(),
                        "You selected: $selectedCategory ${suggestionId[id.toInt()].toString()}",
                        Toast.LENGTH_SHORT
                    ).show()

                    if (binding.yourInputField.text.toString().isEmpty()) {
                        Toast.makeText(requireContext(), "Choose a Category", Toast.LENGTH_SHORT)
                            .show()
                    } else {
                        binding.yourInputLayout1.visibility = View.VISIBLE

                        db.collection("typesofworkers").document(suggestionId[id.toInt()].trim())
                            .collection("workertype").addSnapshotListener { value, error ->
                            binding.yourInputField1.isEnabled = true

                            val catList1 = arrayListOf<SubCatModel>()
                            val data1 = value?.toObjects(SubCatModel::class.java)
                            catList1.addAll(data1!!)

                            for (i in catList1) {
                                suggestion1.add(i.type)
                                suggestionId1.add(i.id)
                            }

                            val adapter = ArrayAdapter(
                                requireContext(),
                                android.R.layout.simple_dropdown_item_1line,
                                suggestion1
                            )
                            val autoCompleteTextView = binding.yourInputField1
                            autoCompleteTextView.setAdapter(adapter)

                            autoCompleteTextView.onItemClickListener =
                                AdapterView.OnItemClickListener { parent, view, position, id ->
                                    val selectedCategory =
                                        parent.getItemAtPosition(position).toString()

                                    Toast.makeText(
                                        requireContext(),
                                        "You selected: $selectedCategory ${suggestionId1[id.toInt()]}",
                                        Toast.LENGTH_SHORT
                                    ).show()

                                    if (binding.yourInputField.text.toString().isEmpty()) {
                                        Toast.makeText(requireContext(), "Choose a Sub-Category", Toast.LENGTH_SHORT)
                                            .show()
                                    } else {
                                        binding.yourInputLayout1.visibility = View.VISIBLE

                                        db.collection("AppointmentType").addSnapshotListener { value, error ->
                                                binding.yourInputField2.isEnabled = true

                                                val catList2 = arrayListOf<AppointmentType>()
                                                val data2 = value?.toObjects(AppointmentType::class.java)
                                                catList2.addAll(data2!!)

                                                for (i in catList2) {
                                                    suggestion2.add(i.title)
                                                }

                                                val adapter = ArrayAdapter(
                                                    requireContext(),
                                                    android.R.layout.simple_dropdown_item_1line,
                                                    suggestion2
                                                )
                                                val autoCompleteTextView = binding.yourInputField2
                                                autoCompleteTextView.setAdapter(adapter)

                                                autoCompleteTextView.onItemClickListener =
                                                    AdapterView.OnItemClickListener { parent, view, position, id ->
                                                        val selectedCategory =
                                                            parent.getItemAtPosition(position).toString()

                                                        Toast.makeText(
                                                            requireContext(),
                                                            "You selected: $selectedCategory ${suggestion2[id.toInt()]}",
                                                            Toast.LENGTH_SHORT
                                                        ).show()
                                                    }
                                            }
                                    }

                                }
                        }
                    }
                }
        }

        binding.nextBtn.setOnClickListener {

            storeData()

        }

//        val chipGroup = binding.chipGroup
//
//        val tags = arrayOf("tag1", "tag2", "tag3", "tag4", "description for tag 5")
//
//        for (tag in tags) {
//            val chip = Chip(requireContext())
//            chip.text = tag
//            chip.isCloseIconVisible = true // Enables the cross button
//            chip.setOnCloseIconClickListener {
//                chipGroup.removeView(chip) // Removes the Chip from the ChipGroup
//            }
//            chipGroup.addView(chip)
//        }

        return binding.root
    }

    private fun storeData() {

        val sharedPreferences = requireActivity().getSharedPreferences(
            "MyPrefs", Context.MODE_PRIVATE
        )

        val name = sharedPreferences.getString("name", "0")
        val phoneNo = sharedPreferences.getString("phoneNo", "0")
        val email = sharedPreferences.getString("email", "0")
        val dob = sharedPreferences.getString("dob", "0")
        val personalInfo = sharedPreferences.getString("personalInfo", "0")
        val imageUri = sharedPreferences.getString("imageUri", "0")

        val data = PersonalInfoModel(
            name = name.toString(),
            phoneNo = phoneNo.toString(),
            email = email.toString(),
            imageUri = imageUri.toString(),
            category = binding.yourInputField.text.toString(),
            appointmentType = binding.yourInputField2.text.toString(),
            description = binding.desTv.text.toString()
        )

        val db = FirebaseFirestore.getInstance()
        val user = FirebaseAuth.getInstance().currentUser
        val workersCollection = db.collection("workers")

        if (user != null) {
            val uid = user.uid

            // Create a new document with the UID as the document ID
            val document = workersCollection.document(uid)

            // Set the data of the document
            document.set(data)
                .addOnSuccessListener {
                    // Document write was successful
                    NavHostFragment.findNavController(this)
                        .navigate(R.id.action_categoryInfo_to_home2)

                    Toast.makeText(requireContext(), "profile has been created", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener { e ->
                    // Document write failed
                    Toast.makeText(requireContext(), "Failed while creating your profile", Toast.LENGTH_SHORT).show()
                    Log.e("@@@", "Error writing document", e)
                }
        } else {
            // User is not authenticated
            Toast.makeText(requireContext(), "User is not authenticated", Toast.LENGTH_SHORT).show()
        }


    }

}