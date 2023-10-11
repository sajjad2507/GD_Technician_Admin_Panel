package com.example.dgtechnicainadminpanel.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.dgtechnicainadminpanel.Adapter.ClientReviewAdapter
import com.example.dgtechnicainadminpanel.Adapter.RequestedAppointmentAdapter
import com.example.dgtechnicainadminpanel.DataModels.ClientReviewModel
import com.example.dgtechnicainadminpanel.DataModels.RequestAppointmentModel
import com.example.dgtechnicainadminpanel.R
import com.example.dgtechnicainadminpanel.databinding.FragmentHomeBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.*

class Home : Fragment() {

    lateinit var binding: FragmentHomeBinding
    lateinit var db: FirebaseFirestore
    private lateinit var auth: FirebaseAuth
    lateinit var techName: String
    lateinit var techImage: String
    private var backPressedTime: Long = 0
    private val doubleBackToExitPressedMessage = "Press back again to exit"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

//        requireActivity().window.addFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN)
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)


//        (activity as MainActivity).findViewById<BottomNavigationView>(R.id.bottomNav).visibility = View.VISIBLE

        db = FirebaseFirestore.getInstance()
        auth = FirebaseAuth.getInstance()
        val techId = auth.uid.toString()
        val currentUser = auth.currentUser!!.uid

        if (currentUser != null) {

            val ref = db.collection("workers").document(currentUser)

            ref.addSnapshotListener { snapshot, error ->
                if (error != null) {
                    return@addSnapshotListener
                }

                val rating = snapshot?.getDouble("rating")

                techName = snapshot?.getString("name").toString()
                techImage = snapshot?.getString("imageUri").toString()

                Glide.with(this)
                    .load(techImage)
                    .apply(RequestOptions().placeholder(R.drawable.userprofile)) // Optional placeholder image
                    .into(binding.techImage)

//                val techFirstWord = techName!!.split(" ").firstOrNull() ?: ""
                binding.homeTechnician.text = techName
                binding.techCategory.text = snapshot?.getString("category").toString()
                binding.positiveRatingRate.text = String.format("%.1f", rating)


                val inputDateString = "23-08-2023"
                val inputFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
                val date = inputFormat.parse(inputDateString)
                val outputFormat = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
                binding.techJoiningDate.text = outputFormat.format(date)



                binding.orderCompletionPro.apply {

                    // Set Progress
                    progress = 0f
                    // or with animation
                    setProgressWithAnimation(100f, 1000) // =1s
                    progressBarWidth = 4f
                    backgroundProgressBarWidth = 4f

                    // Set Progress Max
                    progressMax = 100f

                }

                binding.ratingPro.apply {

                    // Set Progress
                    progress = 0f
                    // or with animation
                        setProgressWithAnimation( rating!!.toFloat(), 1000) // =1s
                    progressBarWidth = 4f
                    backgroundProgressBarWidth = 5f

                    // Set Progress Max
                    progressMax = 5f

                }

                binding.ratingPro1.apply {

                    // Set Progress
                    progress = 0f
                    // or with animation
                    setProgressWithAnimation(100f, 1000) // =1s
                    progressBarWidth = 4f
                    backgroundProgressBarWidth = 4f

                    // Set Progress Max
                    progressMax = 100f

                }

            }

        }

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

            binding.techCatRcv.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            binding.techCatRcv.adapter =
                RequestedAppointmentAdapter(requireContext(),
                    filteredList, "home", requireParentFragment())

        }

        db.collection("workers").document(techId).collection("clientReviews").addSnapshotListener { value, error ->

            val clientReviews = arrayListOf<ClientReviewModel>()
            val data = value?.toObjects(ClientReviewModel::class.java)
            clientReviews.addAll(data!!)

//
            binding.reviewRcv.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            binding.reviewRcv.adapter =
                ClientReviewAdapter(requireContext(), clientReviews, requireParentFragment())

        }

//        binding.homeNotification.setOnClickListener {
//
////            auth.signOut()
//            Toast.makeText(requireContext(), "done", Toast.LENGTH_SHORT).show()
//            NavHostFragment.findNavController(this).navigate(R.id.action_home2_to_clientRequest)
//        }

//        db.collection("bestTechnicians").addSnapshotListener { value, error ->
//
//            val bestTechList = arrayListOf<QuoteModel>()
//            val data = value?.toObjects(QuoteModel::class.java)
//            bestTechList.addAll(data!!)
//
//            Log.e("@@@", bestTechList.toString())
//
////            binding.bestTechRcv.layoutManager =
////                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
////            binding.bestTechRcv.adapter =
////                BestTechAdapter(requireContext(), bestTechList)
//
//            val bestTechAdapter = QuoteAdapter(requireContext(), bestTechList)
//
//            binding.bestTechRcv.adapter = bestTechAdapter
//            binding.bestTechRcv.apply {
////                set3DItem(true)
//                setAlpha(true)
//                setInfinite(true)
//            }
//
//        }

//        db.collection("techCategories").addSnapshotListener { value, error ->
//
//            val techCategoryList = arrayListOf<TechCategoryModel>()
//            val data = value?.toObjects(TechCategoryModel::class.java)
//            techCategoryList.addAll(data!!)
//
//            Log.e("@@@", techCategoryList.toString())
//
//            binding.techCatRcv.layoutManager =
//                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
//            binding.techCatRcv.adapter =
//                TechCategoryAdapter(requireContext(), techCategoryList, requireParentFragment())
//
//        }

        activity?.onBackPressedDispatcher?.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (backPressedTime + 2000 > System.currentTimeMillis()) {
                    requireActivity().finish()
                    return
                } else {
                    Toast.makeText(requireContext(), doubleBackToExitPressedMessage, Toast.LENGTH_SHORT).show()
                }
                backPressedTime = System.currentTimeMillis()
            }
        })


        binding.homeSeeAll.setOnClickListener {

            NavHostFragment.findNavController(this).navigate(R.id.action_home2_to_clientRequest)

        }

        binding.menuSearch.setOnClickListener {

            NavHostFragment.findNavController(this).navigate(R.id.action_home2_to_search)

        }

        binding.menuAppointments.setOnClickListener {

            NavHostFragment.findNavController(this).navigate(R.id.action_home2_to_clientsList)

        }

        binding.menuProfile.setOnClickListener {

            val bundle = Bundle()
            bundle.putString("wName", techName)
            bundle.putString("wImageUrl", techImage)
            NavHostFragment.findNavController(this).navigate(R.id.action_home2_to_profile, bundle)

        }

        return binding.root
    }
}