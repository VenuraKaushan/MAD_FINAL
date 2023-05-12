package com.example.hotelcrud

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.hotelcrud.model.HotelModel
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class AddHotel : AppCompatActivity() {

    private lateinit var etPlaceName: EditText
    private lateinit var etDescription: EditText
    private lateinit var etAmount: EditText
    private lateinit var btnInsertData: Button

    private lateinit var dbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_hotel)

        etPlaceName = findViewById(R.id.etPlaceName)
        etDescription = findViewById(R.id.etDescription)
        etAmount = findViewById(R.id.etAmount)
        btnInsertData = findViewById(R.id.btnInsertData)

        dbRef = FirebaseDatabase.getInstance().getReference("Hotel")

        btnInsertData.setOnClickListener {


            saveHotelData()

        }

    }

    private fun saveHotelData() {

        //adding values
        val placeName = etPlaceName.text.toString()
        val description = etDescription.text.toString()
        val amount = etAmount.text.toString()

        //check text field is empty
        var errorCount: Int = 0;

        //validation for empty text box
        if (placeName.isEmpty()) {
            errorCount++
            etPlaceName.error = "Please enter name"
        }
        if (description.isEmpty()) {
            errorCount++
            etDescription.error = "Please enter description"
        }
        if (amount.isEmpty()) {
            errorCount++
            etAmount.error = "Please enter Amount"
        }


        if (errorCount == 0) {
            //generate unique ID
            val hotelId = dbRef.push().key!!

            //create object
            val hotel = HotelModel(hotelId, placeName, description, amount)

            dbRef.child(hotelId).setValue(hotel)
                .addOnCompleteListener {
                    Toast.makeText(this, "Data inserted Success", Toast.LENGTH_SHORT).show()

                    //passed to the next page
                    val intent = Intent(this, AdminAddEditDelete::class.java)
                    startActivity(intent)

                    //clear data after insert
                    etPlaceName.text.clear()
                    etDescription.text.clear()
                    etAmount.text.clear()
                }.addOnFailureListener { err ->
                    Toast.makeText(this, "Error ${err.message}", Toast.LENGTH_SHORT).show()
                }
        }

    }
}