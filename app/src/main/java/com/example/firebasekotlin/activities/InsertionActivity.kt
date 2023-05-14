package com.example.firebasekotlin.activities

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.firebasekotlin.models.EmployeeModel
import com.example.firebasekotlin.R
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class InsertionActivity : AppCompatActivity() {

    private lateinit var etplaceName: EditText
    private lateinit var etdescription: EditText
    private lateinit var etimportance: EditText
    private lateinit var btnSaveData: Button
    private lateinit var uploadedImage: ImageView

    private lateinit var dbRef: DatabaseReference

    private var imageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_insertion)

        etplaceName = findViewById(R.id.etplaceName)
        etdescription = findViewById(R.id.etdescription)
        etimportance = findViewById(R.id.etimportance)
        btnSaveData = findViewById(R.id.btnSave)
        uploadedImage = findViewById(R.id.imgGuide)

        dbRef = FirebaseDatabase.getInstance().getReference("Places")

        btnSaveData.setOnClickListener {
            saveEmployeeData()
        }

        uploadedImage.setOnClickListener {
            selectImage()
        }
    }

    private fun selectImage() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        getContent.launch(intent)
    }

    private val getContent = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data: Intent? = result.data
            if (data != null) {
                imageUri = data.data
                uploadedImage.setImageURI(imageUri)
            }
        }
    }

    private fun saveEmployeeData() {

        //getting values
        val placeName = etplaceName.text.toString()
        val description = etdescription.text.toString()
        val importance = etimportance.text.toString()

        if (placeName.isEmpty()) {
            etplaceName.error = "Please enter Place name"
        }
        if (description.isEmpty()) {
            etdescription.error = "Please enter descriptionl"
        }
        if (importance.isEmpty()) {
            etimportance.error = "Please enter importance "
        }

        val placeId = dbRef.push().key!!

        // define the imageData variable here
        val imageData = "image_data_here"

        val employee = EmployeeModel(placeId, placeName, description, importance, imageData)

        dbRef.child(placeId).setValue(employee)
            .addOnCompleteListener {
                Toast.makeText(this, "Data inserted successfully", Toast.LENGTH_LONG).show()

                etplaceName.text.clear()
                etdescription.text.clear()
                etimportance.text.clear()



            }.addOnFailureListener { err ->
                Toast.makeText(this, "Error ${err.message}", Toast.LENGTH_LONG).show()
            }

    }



}