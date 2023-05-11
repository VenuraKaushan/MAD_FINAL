package com.example.hotelcrud

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class HotelDetails : AppCompatActivity() {

    private lateinit var stHotelId : TextView
    private lateinit var stHotelName : TextView
    private lateinit var stDescription : TextView
    private lateinit var stAmount : TextView

    private lateinit var btnUpdate : Button
    private lateinit var btnDelete : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hotel_details)

        initView()
        setValuesToViews()
    }

    private fun initView() {

        stHotelId = findViewById(R.id.stHotelId)
        stHotelName = findViewById(R.id.stHotelName)
        stDescription = findViewById(R.id.stDescription)
        stAmount = findViewById(R.id.stAmount)

        btnUpdate = findViewById(R.id.btnUpdate)
        btnDelete = findViewById(R.id.btnDelete)
    }

    private fun setValuesToViews() {
        stHotelId.text = intent.getStringExtra("hotelId")
        stHotelName.text = intent.getStringExtra("hotelName")
        stDescription.text = intent.getStringExtra("hotelDescription")
        stAmount.text = intent.getStringExtra("hotelAmount")

    }
}