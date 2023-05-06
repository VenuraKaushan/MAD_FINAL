package com.example.hotelcrud

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class AdminHome : AppCompatActivity() {
    private lateinit var btnHotelManage: Button
    private lateinit var btnPlaceManage: Button
    private lateinit var btnGuidAndTransportManage: Button

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_home)

        btnHotelManage = findViewById(R.id.btnHotelManage)
        btnPlaceManage = findViewById(R.id.btnPlaceManage)
        btnGuidAndTransportManage = findViewById(R.id.btnGuidAndTransportManage)


        btnHotelManage.setOnClickListener{

            //passed to the next page
            val intent = Intent(this, AdminAddEditDelete::class.java)
            startActivity(intent)
        }
    }
}