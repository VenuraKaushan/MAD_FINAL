package com.example.hotelcrud

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class registration : AppCompatActivity() {

    private lateinit var Name: EditText
    private lateinit var Email: EditText
    private lateinit var Phone: EditText
    private lateinit var password: EditText
    private lateinit var Repassword: EditText
    private lateinit var btnReg: Button

    private lateinit var dbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        Name = findViewById(R.id.Name)
        Email = findViewById(R.id.Email)
        Phone = findViewById(R.id.Phone)
        password = findViewById(R.id.password)
        Repassword = findViewById(R.id.Repassword)
        btnReg = findViewById(R.id.btnReg)

        dbRef = FirebaseDatabase.getInstance().getReference("Tourists")

        btnReg.setOnClickListener {
            saveTouristsData()
        }
    }

    private fun saveTouristsData() {
        //getting values

        val empName = Name.text.toString()
        val mail = Email.text.toString()
        val Num = Phone.text.toString()
        val pw = password.text.toString()
        val nPw = Repassword.text.toString()

        if (empName.isEmpty()) {
            Name.error = "please Enter Tourists Name"
        }

        if (mail.isEmpty()) {
            Email.error = "please Enter Tourists Email"
        }

        if (Num.isEmpty()) {
            Phone.error = "please Enter Tourists Phone Number"
        }

        if (pw.isEmpty()) {
            password.error = "please Enter Tourists Password"
        }

        if (nPw.isEmpty()) {
            Repassword.error = "please Enter Tourists New Password"
        }

        val touristID = dbRef.push().key!!

        val tourist = TouristModel(touristID, empName, mail, Num, pw, nPw)

        dbRef.child(touristID).setValue(tourist)
            .addOnCompleteListener {
                Toast.makeText(this, "Data inserted Successfully", Toast.LENGTH_LONG).show()

                //passed to the next page
                val intent = Intent(this, userprofile::class.java)
                startActivity(intent)


            }.addOnFailureListener { err ->
                Toast.makeText(this, "Error ${err.message}", Toast.LENGTH_LONG).show()
            }
    }
}