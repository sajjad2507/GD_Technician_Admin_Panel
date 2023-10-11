package com.example.dgtechnicainadminpanel.Adapter

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.dgtechnicainadminpanel.DataModels.ClientReviewModel
import com.example.dgtechnicainadminpanel.R

class ClientReviewAdapter(
    val requireContext: Context,
    val requestedAppointment: ArrayList<ClientReviewModel>,
    val requireParentFragment: Fragment
) :
    RecyclerView.Adapter<ClientReviewAdapter.RequestedAppointmentHolder>() {

    inner class RequestedAppointmentHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val clientImage = itemView.findViewById<ImageView>(R.id.techImage)
        val clientName = itemView.findViewById<TextView>(R.id.clientName)
        val clientAddress = itemView.findViewById<TextView>(R.id.clientAddress)
        val clientReview = itemView.findViewById<TextView>(R.id.clientReview)
        val clientRating = itemView.findViewById<TextView>(R.id.clientRating)
        val reviewDate = itemView.findViewById<TextView>(R.id.reviewDate)

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ClientReviewAdapter.RequestedAppointmentHolder {
        return RequestedAppointmentHolder(
            LayoutInflater.from(requireContext)
                .inflate(R.layout.item_review, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RequestedAppointmentHolder, position: Int) {

        val appointment = requestedAppointment[position]

        holder.clientName.text = appointment.clientName
        holder.clientAddress.text = appointment.clientAddress
        holder.clientReview.text = appointment.clientReview
        holder.clientRating.text = appointment.clientRating.toString()
        holder.reviewDate.text = appointment.reviewDate

        Glide.with(requireContext).load(appointment.clientImage).into(holder.clientImage)

//        holder.itemView.setOnClickListener {
//
//            val uId = appointment.uId
//            Toast.makeText(requireContext, uId, Toast.LENGTH_SHORT).show()
//            val bundle = Bundle()
//            bundle.putString("uId", uId)
//
//            if (type == "booked") {
//
//                NavHostFragment.findNavController(requireParentFragment)
//                    .navigate(R.id.action_clientsList_to_chat, bundle)
//
//            } else if (type == "request") {
//
//
//                NavHostFragment.findNavController(requireParentFragment)
//                    .navigate(R.id.action_clientRequest_to_chat, bundle)
//
//            } else {
//
//                NavHostFragment.findNavController(requireParentFragment)
//                    .navigate(R.id.action_home2_to_chat, bundle)
//            }
//        }


//
//        val id = requestedAppointment[position].slot!!.trim() + "_" + requestedAppointment[position].date.trim()

//        holder.actionBtn.setOnClickListener {
//
//            Toast.makeText(
//                requireContext,
//                "cancle" + appointment.name,
//                Toast.LENGTH_SHORT
//            ).show()
//
//        }


//            db.collection("workers").document(requestedAppointment[position].wId.trim())
//                .collection("requestedAppointment")
//                .document(id).delete().addOnSuccessListener {
//
//                    db.collection("users").document(requestedAppointment[position].uId.trim())
//                        .collection("requestedAppointment")
//                        .document(id).delete().addOnSuccessListener {
//
//                            Toast.makeText(requireContext, "Request Deleted Successfully", Toast.LENGTH_SHORT).show()
//
//                        }
//
//                }

//        }

    }

    override fun getItemCount(): Int {
        return requestedAppointment.size
    }
}