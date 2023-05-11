package com.example.hotelcrud

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class AdminAddEditDelete : AppCompatActivity() {

    private lateinit var btnInssertData: Button
    private lateinit var btnViewHotel: Button


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_add_edit_delete)

        btnInssertData = findViewById(R.id.btnInssertData)
        btnViewHotel = findViewById(R.id.btnViewHotel)

        btnInssertData.setOnClickListener{

            //passed to the next page
            val intent = Intent(this, AddHotel::class.java)
            startActivity(intent)
        }

        btnViewHotel.setOnClickListener{

            //passed to the next page
            val intent = Intent(this, EditHotel::class.java)
            startActivity(intent)
        }





    }
}