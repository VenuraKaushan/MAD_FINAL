package com.example.hotelcrud.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.hotelcrud.R
import com.example.hotelcrud.model.HotelModel

class HotelAdapter(private val hotelList: ArrayList<HotelModel>) :
    RecyclerView.Adapter<HotelAdapter.ViewHolder>() {

    private lateinit var mListener: onItemClickListener

    interface  onItemClickListener{
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(clickListener: onItemClickListener){

        mListener = clickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, ViewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.hotel_list, parent,false)
        return ViewHolder(itemView, mListener)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val currentHotel = hotelList[position]
        holder.tvHotelName.text = currentHotel.hotelName

    }

    override fun getItemCount(): Int {

        return hotelList.size

    }


    class ViewHolder(itemView: View, clickListener: onItemClickListener) : RecyclerView.ViewHolder(itemView) {

        val tvHotelName :  TextView = itemView.findViewById(R.id.tvHotelName)

        init {
            itemView.setOnClickListener{
                clickListener.onItemClick(adapterPosition)
            }
        }

    }


}