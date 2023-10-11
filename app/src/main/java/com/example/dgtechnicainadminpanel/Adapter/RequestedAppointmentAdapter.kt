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
import com.example.dgtechnicainadminpanel.DataModels.RequestAppointmentModel
import com.example.dgtechnicainadminpanel.R

class RequestedAppointmentAdapter(
    val requireContext: Context,
    val requestedAppointment: ArrayList<RequestAppointmentModel>,
    val type: String,
    val requireParentFragment: Fragment
) :
    RecyclerView.Adapter<RequestedAppointmentAdapter.RequestedAppointmentHolder>() {

    inner class RequestedAppointmentHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val clientImage = itemView.findViewById<ImageView>(R.id.techImage)
        val clientName = itemView.findViewById<TextView>(R.id.techName)
        val clientPhone = itemView.findViewById<TextView>(R.id.techPhone)
        val orderDescription = itemView.findViewById<TextView>(R.id.orderDescription)
        val orderDate = itemView.findViewById<TextView>(R.id.orderDate)
        val actionBtn = itemView.findViewById<Button>(R.id.actionBtn)

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RequestedAppointmentAdapter.RequestedAppointmentHolder {
        return RequestedAppointmentHolder(
            LayoutInflater.from(requireContext)
                .inflate(R.layout.item_appointment, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RequestedAppointmentHolder, position: Int) {

        val appointment = requestedAppointment[position]

        holder.clientName.text = appointment.name
        holder.clientPhone.text = appointment.phone
        holder.orderDescription.text = appointment.description
        holder.orderDate.text = appointment.date

//        Glide.with(requireContext).load(appointment.imageUrl).into(holder.clientImage)

        holder.itemView.setOnClickListener {

//            val uId = "hQvASwP8nDYP5RIlIvNJSHhY64h2"
            val uId = appointment.wId.toString()
            Toast.makeText(requireContext, appointment.uId, Toast.LENGTH_SHORT).show()
            val bundle = Bundle()
            bundle.putString("uId", uId)

            if (type == "booked") {

                NavHostFragment.findNavController(requireParentFragment)
                    .navigate(R.id.action_clientsList_to_chat, bundle)

            } else if (type == "request") {


                NavHostFragment.findNavController(requireParentFragment)
                    .navigate(R.id.action_clientRequest_to_chat, bundle)

            } else {

                NavHostFragment.findNavController(requireParentFragment)
                    .navigate(R.id.action_home2_to_chat, bundle)
            }
        }


//
//        val id = requestedAppointment[position].slot!!.trim() + "_" + requestedAppointment[position].date.trim()

        holder.actionBtn.setOnClickListener {

            Toast.makeText(
                requireContext,
                "cancle" + appointment.name,
                Toast.LENGTH_SHORT
            ).show()

        }


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