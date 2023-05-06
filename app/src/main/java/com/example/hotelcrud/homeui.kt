package com.example.hotelcrud

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class homeui : AppCompatActivity() {

    private lateinit var signup: Button
    private lateinit var signin: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_homeui)

        signup = findViewById(R.id.signup)
        signin = findViewById(R.id.signin)

        signup.setOnClickListener{
            val intent = Intent(this, registration::class.java)
            startActivity(intent)
        }

        signin.setOnClickListener{
            val intent = Intent( this,userlogin::class.java )
            startActivity(intent)
        }
    }
}