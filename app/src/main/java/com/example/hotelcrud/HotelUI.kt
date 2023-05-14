package com.example.hotelcrud

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class HotelUI : AppCompatActivity() {

    private lateinit var btnPlaceName1: Button
    private lateinit var btnPlaceName2: Button
    private lateinit var btnPlaceName3: Button
    private lateinit var btnPlaceName4: Button

    private lateinit var dbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hotel_ui)

//        btnPlaceName1 = findViewById(R.id.btnPlaceName1)
//        btnPlaceName2 = findViewById(R.id.btnPlaceName2)
//        btnPlaceName3 = findViewById(R.id.btnPlaceName3)
//        btnPlaceName4 = findViewById(R.id.btnPlaceName4)
//
//
//        dbRef = FirebaseDatabase.getInstance().getReference("Hotel")
//
//        dbRef.child("Hotel").child("hotelId").child("hotelName").get().addOnSuccessListener { dataSnapshot ->
//            val hotelName = dataSnapshot.value.toString()
//            // Set the text of your button to the placeName value
//            btnPlaceName1.text = hotelName
//        }.addOnFailureListener { exception ->
//            Toast.makeText(this,"Error ${exception.message}", Toast.LENGTH_SHORT).show()
//
//        }


    }
}