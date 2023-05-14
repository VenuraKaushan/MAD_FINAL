package com.example.hotelcrud

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.media.Image
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.example.hotelcrud.model.HotelModel
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.text.SimpleDateFormat
import java.util.*

class AddHotel : AppCompatActivity() {

    private lateinit var etPlaceName: EditText
    private lateinit var etDescription: EditText
    private lateinit var etAmount: EditText
    private lateinit var btnInsertData: Button
    private lateinit var btnSelectImage : Button
    private lateinit var uploadedImage: ImageView
    private lateinit var ImageUri : Uri
    private lateinit var storageReference: StorageReference

    private lateinit var dbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_hotel)

        etPlaceName = findViewById(R.id.etPlaceName)
        etDescription = findViewById(R.id.etDescription)
        etAmount = findViewById(R.id.etAmount)
        btnInsertData = findViewById(R.id.btnInsertData)
        btnSelectImage =findViewById(R.id.btnSelect)
        uploadedImage = findViewById(R.id.uploadedImage)

        dbRef = FirebaseDatabase.getInstance().getReference("Hotel")

        btnInsertData.setOnClickListener {


            saveHotelData()
            uploadImage()

        }

        btnSelectImage.setOnClickListener {

            selectImage()
        }

    }

    private fun uploadImage() {
        val progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Uploading Image...")
        progressDialog.setCancelable(false)
        progressDialog.show()

        val formatter = SimpleDateFormat("yyyy_MM_dd_HH_mm_ss", Locale.getDefault())
        val now = Date()
        val fileName = formatter.format(now)
        storageReference = FirebaseStorage.getInstance().getReference("HotelImages/$fileName")

        storageReference.putFile(ImageUri).addOnSuccessListener {

            uploadedImage.setImageURI(null)
            Toast.makeText(this@AddHotel, "Successfully Uploaded", Toast.LENGTH_SHORT).show()

            if (progressDialog.isShowing) progressDialog.dismiss()

        }.addOnFailureListener {
            if (progressDialog.isShowing) progressDialog.dismiss()
            Toast.makeText(this@AddHotel, "Successfully Uploaded", Toast.LENGTH_SHORT).show()


        }


    }

    private fun selectImage() {

        val intent = Intent()

        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT

        getContent.launch(intent)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.registerForActivityResult(requestCode, resultCode, data)

        if(requestCode == 100 && resultCode == RESULT_OK){

            ImageUri = data?.data!!
            uploadedImage.setImageURI(ImageUri)

        }
    }

    private val getContent = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data: Intent? = result.data
            if (data != null) {
                ImageUri = data?.data!!
                uploadedImage.setImageURI(ImageUri)
            }
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


