package com.example.dgtechnicainadminpanel.fragments

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.dgtechnicainadminpanel.Adapter.SlotsAdapter
import com.example.dgtechnicainadminpanel.DataModel.AppointmentType
import com.example.dgtechnicainadminpanel.DataModel.CatModel
import com.example.dgtechnicainadminpanel.DataModel.SlotsModel
import com.example.dgtechnicainadminpanel.DataModel.SubCategoryModel
import com.example.dgtechnicainadminpanel.DataModels.PersonalInfoModel
import com.example.dgtechnicainadminpanel.R
import com.example.dgtechnicainadminpanel.databinding.FragmentPersonalInfoBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import java.text.SimpleDateFormat
import java.util.*

class PersonalInfo : Fragment() {

    lateinit var binding: FragmentPersonalInfoBinding
    private var imageUri: Uri? = null
    lateinit var slotAdapter: SlotsAdapter
    private val selectImage = registerForActivityResult(ActivityResultContracts.GetContent()) {
        val wImage = requireView().findViewById<ImageView>(R.id.changeImg)
        imageUri = it
        wImage.setImageURI(imageUri)
    }
    val uid = FirebaseAuth.getInstance().uid.toString()
    lateinit var db: FirebaseFirestore
    var suggestion = mutableListOf<String>()
    var suggestionLogo = mutableListOf<String>()
    var suggestionSlots = mutableListOf<String>()
    var suggestion2 = mutableListOf<String>()
    var suggestion3 = mutableListOf<String>()
    var suggestionId = mutableListOf<String>()
    var suggestionListSlots = mutableListOf<String>()
    var suggestionId2 = mutableListOf<String>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPersonalInfoBinding.inflate(layoutInflater, container, false)

        binding.changeImg.setOnClickListener {

            selectImage.launch("image/*")

        }

        binding.nextBtn.setOnClickListener {

            validateData()

        }

        db = FirebaseFirestore.getInstance()
        val sharedPreferences = requireContext().getSharedPreferences(
            "MyPrefs", Context.MODE_PRIVATE
        )

        db.collection("WorkerCategory").addSnapshotListener { value, error ->

            val catList = arrayListOf<CatModel>()
            val data = value?.toObjects(CatModel::class.java)
            catList.addAll(data!!)

            for (i in catList) {
                suggestion.add(i.name)
                suggestionLogo.add(i.logo)
            }

            val adapterCat = ArrayAdapter(
                requireContext(),
                android.R.layout.simple_dropdown_item_1line,
                suggestion
            )
            val autoCompleteTextView = binding.category
            autoCompleteTextView.setAdapter(adapterCat)

            autoCompleteTextView.onItemClickListener =
                AdapterView.OnItemClickListener { parent, view, position, id ->
                    val selectedCategory = parent.getItemAtPosition(position).toString()
                    val catLogo = suggestionLogo[position]

                    val editor = sharedPreferences.edit()

                    editor.putString("logo", catLogo)
                    editor.apply()

                    Toast.makeText(
                        requireContext(),
                        catLogo,
                        Toast.LENGTH_SHORT
                    ).show()

                    if (binding.category.text.toString().isEmpty()) {
                        Toast.makeText(requireContext(), "Choose a Category", Toast.LENGTH_SHORT)
                            .show()
                    } else {

                        binding.yourInputLayout1.visibility = View.VISIBLE

                        db.collection("WorkerCategory")
                            .document(binding.category.text.toString().trim()).collection("types")
                            .addSnapshotListener { value, error ->
                                binding.appointmentType.isEnabled = true

                                val catList2 = arrayListOf<SubCategoryModel>()
                                val data2 =
                                    value?.toObjects(SubCategoryModel::class.java)
                                catList2.addAll(data2!!)

                                for (i in catList2) {
                                    suggestion2.add(i.name)
                                }

                                val adapterSubCat = ArrayAdapter(
                                    requireContext(),
                                    android.R.layout.simple_dropdown_item_1line,
                                    suggestion2
                                )
                                val autoCompleteTextView =
                                    binding.subCategory
                                autoCompleteTextView.setAdapter(adapterSubCat)

                                autoCompleteTextView.onItemClickListener =
                                    AdapterView.OnItemClickListener { parent, view, position, id ->
                                        val selectedCategory =
                                            parent.getItemAtPosition(position)
                                                .toString()

                                        Toast.makeText(
                                            requireContext(),
                                            "You selected: $selectedCategory ${suggestion2[id.toInt()]}",
                                            Toast.LENGTH_SHORT
                                        ).show()


                                        if (binding.subCategory.text.toString().isEmpty()) {
                                            Toast.makeText(
                                                requireContext(),
                                                "Choose a Sub Category",
                                                Toast.LENGTH_SHORT
                                            )
                                                .show()
                                        } else {

                                            binding.yourInputLayout2.visibility = View.VISIBLE

                                            db.collection("AppointmentType")
                                                .addSnapshotListener { value, error ->
                                                    binding.appointmentType.isEnabled = true

                                                    val appoitnmentTypeList =
                                                        arrayListOf<AppointmentType>()
                                                    val data3 =
                                                        value?.toObjects(AppointmentType::class.java)
                                                    appoitnmentTypeList.addAll(data3!!)

                                                    for (i in appoitnmentTypeList) {
                                                        suggestion3.add(i.title)
                                                    }

                                                    val adapterAppointment = ArrayAdapter(
                                                        requireContext(),
                                                        android.R.layout.simple_dropdown_item_1line,
                                                        suggestion3
                                                    )
                                                    val autoCompleteTextView =
                                                        binding.appointmentType
                                                    autoCompleteTextView.setAdapter(
                                                        adapterAppointment
                                                    )

                                                    autoCompleteTextView.onItemClickListener =
                                                        AdapterView.OnItemClickListener { parent, view, position, id ->
                                                            val selectedCategory =
                                                                parent.getItemAtPosition(position)
                                                                    .toString()

                                                            Toast.makeText(
                                                                requireContext(),
                                                                "You selected: $selectedCategory ${suggestion3[id.toInt()]}",
                                                                Toast.LENGTH_SHORT
                                                            ).show()


                                                            if (binding.subCategory.text.toString()
                                                                    .isEmpty()
                                                            ) {
                                                                Toast.makeText(
                                                                    requireContext(),
                                                                    "Choose a Appointment Type",
                                                                    Toast.LENGTH_SHORT
                                                                )
                                                                    .show()
                                                            } else {

                                                                binding.appointmentSlotRcv.visibility =
                                                                    View.VISIBLE

                                                                val id =
                                                                    binding.appointmentType.text.toString()
                                                                        .trim()

                                                                db.collection("AppointmentType")
                                                                    .document(id)
                                                                    .collection("slots")
                                                                    .addSnapshotListener { value, error ->

                                                                        val slotList =
                                                                            arrayListOf<SlotsModel>()
                                                                        val slots =
                                                                            value?.toObjects(
                                                                                SlotsModel::class.java
                                                                            )
                                                                        slotList.addAll(slots!!)

                                                                        // Set up the RecyclerView with a GridLayoutManager
                                                                        val layoutManager =
                                                                            GridLayoutManager(
                                                                                requireContext(),
                                                                                3
                                                                            ) // Set the desired number of columns
                                                                        binding.appointmentSlotRcv.layoutManager =
                                                                            layoutManager

                                                                        // Create and attach the adapter
                                                                        slotAdapter = SlotsAdapter(
                                                                            requireContext(),
                                                                            slots
                                                                        )
                                                                        binding.appointmentSlotRcv.adapter =
                                                                            slotAdapter

                                                                    }
                                                            }


                                                        }
                                                }
                                        }


                                    }
                            }
                    }
                }
        }

        return binding.root
    }

    private fun validateData() {
        if (binding.techName.text.toString().isEmpty()
            || binding.techPhone.text.toString().isEmpty()
            || binding.techEmail.text.toString().isEmpty()
            || binding.category.text.toString().isEmpty()
            || binding.subCategory.text.toString().isEmpty()
            || binding.appointmentType.text.toString().isEmpty()
            || binding.desTv.text.toString().isEmpty()
            || imageUri == null
        ) {
            Toast.makeText(requireContext(), "Please Fill all the field", Toast.LENGTH_SHORT).show()
        } else {
            uploadImage()
        }
    }

    private fun uploadImage() {
        val storageReference = FirebaseStorage.getInstance().getReference("WorkersImage")
            .child(FirebaseAuth.getInstance().currentUser!!.uid)

        storageReference.child("profile.jpg")
            .putFile(imageUri!!).addOnSuccessListener {
                storageReference.child("profile.jpg").downloadUrl.addOnSuccessListener {
                    storeData(it.toString())
                }
                    .addOnFailureListener {
                        val storageRef =
                            FirebaseStorage.getInstance().getReference("WorkersImage").child(uid)
                                .child("profile.jpg")
                        storageRef.downloadUrl.addOnSuccessListener { uri ->
                            val storeImage = uri.toString()
                            storeData(storeImage)
//            // Do something with the download URL, like saving it to a Realtime Database node
                        }.addOnFailureListener { exception ->
//            // Handle any errors
                        }
                    }
            }
            .addOnFailureListener {
                Toast.makeText(
                    requireContext(),
                    it.message.toString(),
                    Toast.LENGTH_SHORT
                ).show()
            }
    }


    private fun storeData(uri: String) {
        val list: MutableList<SlotsModel>? = slotAdapter.whenChange()

        val db = FirebaseFirestore.getInstance()
        val user = FirebaseAuth.getInstance().currentUser
        val workersCollection = db.collection("workers")
        val timestamp = com.google.firebase.Timestamp.now()

        if (user != null) {
            val uid = user.uid
            val data = createPersonalInfoData(uri)
            val document = workersCollection.document(uid)

            document.set(data)
                .addOnSuccessListener {
                    onDocumentWriteSuccess(uid, list, db)
                }
                .addOnFailureListener { e ->
                    onDocumentWriteFailure(e)
                }
        } else {
            // User is not authenticated
            Toast.makeText(requireContext(), "User is not authenticated", Toast.LENGTH_SHORT).show()
        }
    }

    private fun createPersonalInfoData(uri: String): PersonalInfoModel {

        val currentDate = getCurrentFormattedDate()

        return PersonalInfoModel(
            techId = FirebaseAuth.getInstance().currentUser?.uid ?: "",
            name = binding.techName.text.toString(),
            phoneNo = binding.techPhone.text.toString(),
            email = binding.techEmail.text.toString(),
            imageUri = uri.toString(),
            category = binding.category.text.toString(),
            rating = 5.0,
            joiningDate = currentDate,
            subCategory = binding.subCategory.text.toString(),
            appointmentType = binding.appointmentType.text.toString(),
            description = binding.desTv.text.toString(),
            status = "active"
        )
    }

    private fun onDocumentWriteSuccess(
        uid: String,
        list: MutableList<SlotsModel>?,
        db: FirebaseFirestore
    ) {

        list?.forEach { slot ->
            db.collection("workers").document(uid).collection("slots")
                .document(slot.slot).set(slot)
        }

        val sharedPreferences = requireContext().getSharedPreferences(
            "MyPrefs", Context.MODE_PRIVATE
        )
        val catLogo = sharedPreferences.getString("logo", "")
        val cat = CatModel(name = binding.category.text.toString(), logo = catLogo!!)

        val catNet = db.collection("techNetwork")
            .document(binding.category.text.toString().trim())

        catNet.set(cat).addOnSuccessListener {
            catNet.collection("technicains").document(uid).set(createPersonalInfoData(""))
                .addOnSuccessListener {
                }
        }


        NavHostFragment.findNavController(this)
            .navigate(R.id.action_personalInfo_to_home2)
        Toast.makeText(requireContext(), "Profile has been created", Toast.LENGTH_SHORT).show()
    }

    private fun onDocumentWriteFailure(e: Exception) {
        // Document write failed
        Toast.makeText(
            requireContext(),
            "Failed while creating your profile",
            Toast.LENGTH_SHORT
        ).show()
        Log.e("@@@", "Error writing document", e)
    }

    fun getCurrentFormattedDate(): String {
        val dateFormat = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
        val currentDate = Date()
        return dateFormat.format(currentDate)
    }

}