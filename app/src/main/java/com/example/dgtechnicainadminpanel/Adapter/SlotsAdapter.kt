package com.example.dgtechnicainadminpanel.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.dgtechnicainadminpanel.DataModel.SlotsModel
import com.example.dgtechnicainadminpanel.R

class SlotsAdapter(val requireContext: Context, val slotList: MutableList<SlotsModel>?) :
    RecyclerView.Adapter<SlotsAdapter.BestTechHolder>() {

    inner class BestTechHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val itemSlots = itemView.findViewById<TextView>(R.id.itemSlotTv)
        val slotCancelBtn = itemView.findViewById<ImageView>(R.id.slotCancelBtn)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BestTechHolder {
        return BestTechHolder(
            LayoutInflater.from(requireContext).inflate(R.layout.item_slots, parent, false)
        )
    }

    override fun onBindViewHolder(holder: BestTechHolder, position: Int) {

        holder.itemSlots.text = slotList!![position].slot
        holder.slotCancelBtn.setOnClickListener {

            slotList.removeAt(position)
            notifyItemRemoved(position)

        }
    }

    override fun getItemCount(): Int {
        return slotList!!.size
    }

    fun whenChange(): MutableList<SlotsModel>? {
        return slotList
    }

}