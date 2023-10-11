package com.example.dgtechnicainadminpanel.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.dgtechnicainadminpanel.R
import com.example.dgtechnicainadminpanel.databinding.FragmentSearchBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

class Search : Fragment() {

    lateinit var binding: FragmentSearchBinding
    lateinit var db: Firebase

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentSearchBinding.inflate(layoutInflater, container, false)

        // Add a TextWatcher to the customSearchView
        binding.searchEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // This method is called before the text changes
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // This method is called as the text changes
                val query = s.toString()
//                showData(query)
            }

            override fun afterTextChanged(s: Editable?) {
                // This method is called after the text changes
            }
        })

        binding.searchIcon.setOnClickListener {
            val query = binding.searchEditText.text.toString()
            // Handle the search icon click event here
//            showData(query)
        }

        return binding.root
    }


//    private fun showNotes(filter: String) {
//
//
//        db = FirebaseFirestore.getInstance()
//        val user = FirebaseAuth.getInstance().currentUser
//        val uId = user!!.uid
//
//        db.collection("user").document(uId).collection("notes")
//            .addSnapshotListener { value, error ->
//
//                val allNotes = arrayListOf<NotesDataModel>()
//                var filteredList = arrayListOf<NotesDataModel>()
//                val data = value?.toObjects(NotesDataModel::class.java)
//                allNotes.addAll(data!!)
//
//
//                // Add notes to filter list
//                if (filter.isBlank()) {
//                    // If the filter is blank, show all notes
//                    filteredList = allNotes
//                } else {
//                    for (note in allNotes) {
//                        val titleContainsQuery = note.title.contains(filter, ignoreCase = true)
//                        val notesContainsQuery = note.notes.contains(filter, ignoreCase = true)
//
//                        if (titleContainsQuery || notesContainsQuery) {
//                            filteredList.add(note)
//                        }
//                    }
//
//                }
//
//                binding.searchRcv.layoutManager =
//                    StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
//                binding.searchRcv.adapter =
//                    NotesAdapter(
//                        requireContext(),
//                        filteredList, requireParentFragment()
//                    )
//
//
//            }
//
//    }

}