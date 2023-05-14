package com.example.hotelcrud

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class Mainui : AppCompatActivity() {

    private lateinit var btnHotel : Button
    private lateinit var profile: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mainui)

        profile = findViewById(R.id.profile)
        btnHotel = findViewById(R.id.btnHotel)

        profile.setOnClickListener{
            val intent = Intent(this, Userprofile::class.java)
            startActivity(intent)
        }

        btnHotel.setOnClickListener{
            val intent = Intent(this,HotelUI::class.java)
            startActivity(intent)
        }

    }
}