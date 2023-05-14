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

    private lateinit var etguideName: EditText
    private lateinit var etguideEmail: EditText
    private lateinit var etPhone: EditText
    private lateinit var btnSaveData: Button
    private lateinit var uploadedImage: ImageView

    private lateinit var dbRef: DatabaseReference

    private var imageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_insertion)

        etguideName = findViewById(R.id.etguideName)
        etguideEmail = findViewById(R.id.etguideEmail)
        etPhone = findViewById(R.id.etPhone)
        btnSaveData = findViewById(R.id.btnSave)
        uploadedImage = findViewById(R.id.imgGuide)

        dbRef = FirebaseDatabase.getInstance().getReference("Guide")

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
        val guideName = etguideName.text.toString()
        val guideEmail = etguideEmail.text.toString()
        val Phone = etPhone.text.toString()

        if (guideName.isEmpty()) {
            etguideName.error = "Please enter Guide name"
        }
        if (guideEmail.isEmpty()) {
            etguideEmail.error = "Please enter Guide Email"
        }
        if (Phone.isEmpty()) {
            etPhone.error = "Please enter Phone no"
        }

        val guideId = dbRef.push().key!!

        // define the imageData variable here
        val imageData = "image_data_here"

        val employee = EmployeeModel(guideId, guideName, guideEmail, Phone, imageData)

        dbRef.child(guideId).setValue(employee)
            .addOnCompleteListener {
                Toast.makeText(this, "Data inserted successfully", Toast.LENGTH_LONG).show()

                etguideName.text.clear()
                etguideEmail.text.clear()
                etPhone.text.clear()
               // uploadedImage.text.clear()


            }.addOnFailureListener { err ->
                Toast.makeText(this, "Error ${err.message}", Toast.LENGTH_LONG).show()
            }

    }



}

