package com.example.hotelcrud

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class mainui : AppCompatActivity() {

    private lateinit var profile: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mainui)

        profile = findViewById(R.id.profile)

        profile.setOnClickListener{
            val intent = Intent(this, userprofile::class.java)
            startActivity(intent)
        }

    }
}