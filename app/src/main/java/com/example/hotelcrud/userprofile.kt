package com.example.hotelcrud

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class userprofile : AppCompatActivity() {

//    get input box
    private lateinit var name : TextView
    private lateinit var email : TextView
    private lateinit var phone : TextView

//    create database variables
    private lateinit var dbRef : DatabaseReference
    private lateinit var auth : FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_userprofile)

//        initialized the text boxes
        name = findViewById(R.id.name_value)
        email  = findViewById(R.id.email_value)
        phone = findViewById(R.id.phone_value)


//        get user details to profile
        getUserdetails()

    }

    private fun getUserdetails(){
        dbRef = FirebaseDatabase.getInstance().getReference("user")
        auth = FirebaseAuth.getInstance()

//        get current loggedin user id
        val uid = auth.uid.toString()

        dbRef.child(uid).addValueEventListener(object:ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {

                val user = snapshot.getValue(TouristModel::class.java)!!

                name.setText(user.empName)
                email.setText(user.mail)
                phone.setText(user.Num)

            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@userprofile,"something went wrong while fetching profile data",Toast.LENGTH_SHORT).show()
            }

        })

    }
}