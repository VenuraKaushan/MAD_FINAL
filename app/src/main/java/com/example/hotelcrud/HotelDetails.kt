package com.example.hotelcrud

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.hotelcrud.model.HotelModel
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class HotelDetails : AppCompatActivity() {

    private lateinit var stHotelId: TextView
    private lateinit var stHotelName: TextView
    private lateinit var stDescription: TextView
    private lateinit var stAmount: TextView

    private lateinit var btnUpdate: Button
    private lateinit var btnDelete: Button

    private lateinit var dbRef: DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hotel_details)

        initView()
        setValuesToViews()

        btnUpdate.setOnClickListener {
            openUpdateDialog(
                intent.getStringExtra("hotelId").toString(),
                intent.getStringExtra("hotelName").toString()
            )
        }

        btnDelete.setOnClickListener {
            deleteRecord(
                intent.getStringExtra("hotelId").toString()
            )
        }
    }

    private fun deleteRecord(id: String) {

        dbRef = FirebaseDatabase.getInstance().getReference("Hotel").child(id)
        val mTask = dbRef.removeValue()

        mTask.addOnSuccessListener {
            Toast.makeText(this, "Hotel data deleted", Toast.LENGTH_LONG).show()

            val intent = Intent(this, EditHotel::class.java)
            finish()
            startActivity(intent)
        }.addOnFailureListener { error ->
            Toast.makeText(this, "Deleting Error ${error.message}", Toast.LENGTH_LONG).show()
        }
    }


    private fun initView() {

        stHotelId = findViewById(R.id.stHotelId)
        stHotelName = findViewById(R.id.stHotelName)
        stDescription = findViewById(R.id.stDescription)
        stAmount = findViewById(R.id.stAmount)

        btnUpdate = findViewById(R.id.btnUpdate)
        btnDelete = findViewById(R.id.btnDelete)
    }

    private fun setValuesToViews() {
        stHotelId.text = intent.getStringExtra("hotelId")
        stHotelName.text = intent.getStringExtra("hotelName")
        stDescription.text = intent.getStringExtra("hotelDescription")
        stAmount.text = intent.getStringExtra("hotelAmount")

    }

    private fun openUpdateDialog(hotelId: String, hotelName: String) {

        val mDialog = AlertDialog.Builder(this)
        val inflater = layoutInflater
        val mDialogView = inflater.inflate(R.layout.hotel_update_dialog,null)

        mDialog.setView(mDialogView)
        val dltHotelName =  mDialogView.findViewById<EditText>(R.id.dltHotelName)
        val dltHotelDescription =  mDialogView.findViewById<EditText>(R.id.dltHotelDescription)
        val dltHotelAmount =  mDialogView.findViewById<EditText>(R.id.dltHotelAmount)
        val btnUpdateData =  mDialogView.findViewById<Button>(R.id.btnUpdateData)

        dltHotelName.setText(intent.getStringExtra("hotelName").toString())
        dltHotelDescription.setText(intent.getStringExtra("hotelDescription").toString())
        dltHotelAmount.setText(intent.getStringExtra("hotelAmount").toString())

        mDialog.setTitle("Updating $hotelName Record")

        val alertDialog = mDialog.create()
        alertDialog.show()


        btnUpdateData.setOnClickListener{
            updateHotelData(
                hotelId,
                dltHotelName.text.toString(),
                dltHotelDescription.text.toString(),
                dltHotelAmount.text.toString()
            )

            Toast.makeText(applicationContext, "Hotel Data Updated", Toast.LENGTH_LONG).show()

            stHotelName.text = dltHotelName.text.toString()
            stDescription.text = dltHotelDescription.text.toString()
            stAmount.text = dltHotelAmount.text.toString()

            alertDialog.dismiss()
        }

    }

    private fun updateHotelData(id: String, name: String, description: String, amount: String) {

        dbRef = FirebaseDatabase.getInstance().getReference("Hotel").child(id)
        val hotelInfo = HotelModel(id, name, description , amount)
        dbRef.setValue(hotelInfo)
    }


}