package com.example.hotelcrud

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.database.DatabaseReference

class AddGuide : AppCompatActivity() {

    private lateinit var etGuideName: EditText
    private lateinit var etGuideEmail: EditText
    private lateinit var etContactNumber: EditText
    private lateinit var ADD: Button

    private lateinit var dbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_guide)

        etGuideName = findViewById(R.id.tvGuideName)
        etGuideEmail = findViewById(R.id.TvGuideEmail)
        etContactNumber = findViewById(R.id.tvContactNumber)
        ADD = findViewById(R.id.Edit)

        ADD.setOnClickListener{
            saveGuideData()
        }

    }
    private fun saveGuideData(){

        //get values
        val guideName = etGuideName.text.toString()
        val guideEmail = etGuideEmail.text.toString()
        val contactNumber = etContactNumber.text.toString()

        if (guideName.isEmpty()){
            etGuideName.error = "Please enter name"
        }

        if (guideEmail.isEmpty()){
            etGuideEmail.error = "Please enter email"
        }

        if (contactNumber.isEmpty()){
            etContactNumber.error = "Please enter number"
        }

        val guideID = dbRef.push().key!!

        val Guide = GuideModel(guideID,guideName,guideEmail,contactNumber)

        dbRef.child(guideID).setValue(Guide)
            .addOnCanceledListener {
                Toast.makeText(this, "Data inserted successfully", Toast.LENGTH_SHORT).show()

                etGuideName.text.clear()
                etGuideEmail.text.clear()
                etContactNumber.text.clear()

            }.addOnFailureListener{ err ->
                Toast.makeText(this, "Error ${err.message}", Toast.LENGTH_SHORT).show()
            }

    }
}