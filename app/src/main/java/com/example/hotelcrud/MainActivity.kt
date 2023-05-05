package com.example.hotelcrud

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class MainActivity : AppCompatActivity() {

    private lateinit var etPlaceName: EditText
    private lateinit var etDescription: EditText
    private lateinit var etAmount: EditText
    private lateinit var btnInsertData: Button

    private lateinit var dbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_hotel)

        etPlaceName = findViewById(R.id.etPlaceName)
        etDescription = findViewById(R.id.etDescription)
        etAmount = findViewById(R.id.etAmount)
        btnInsertData = findViewById(R.id.btnInsertData)

        dbRef = FirebaseDatabase.getInstance().getReference("Hotel")

        btnInsertData.setOnClickListener {

            saveHotelData()
            val intent = Intent(this, HotelUI::class.java)
            startActivity(intent)
        }

    }

    private fun saveHotelData(){

        //adding values
        val placeName = etPlaceName.text.toString()
        val description = etDescription.text.toString()
        val amount = etAmount.text.toString()

        //validation for empty text box
        if (placeName.isEmpty()) {
            etPlaceName.error = "Please enter name"
            return
        }
        if (description.isEmpty()) {
            etDescription.error = "Please enter description"
        }
        if (amount.isEmpty()) {
            etAmount.error = "Please enter Amount"
        }

        //genrate unique ID
        val hotelId = dbRef.push().key!!

        val hotel = HotelModel(hotelId, placeName, description, amount)

        dbRef.child(hotelId).setValue(hotel)
            .addOnCompleteListener{
                Toast.makeText(this, "Data inserted Success", Toast.LENGTH_SHORT).show()

                //clear data after insert
                etPlaceName.text.clear()
                etDescription.text.clear()
                etAmount.text.clear()
            }.addOnFailureListener { err ->
                Toast.makeText(this,"Error ${err.message}",Toast.LENGTH_SHORT).show()
            }
    }
}