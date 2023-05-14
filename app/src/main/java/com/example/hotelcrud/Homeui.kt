package com.example.hotelcrud

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.google.android.material.floatingactionbutton.FloatingActionButton

class Homeui : AppCompatActivity() {

    private lateinit var signup: Button
    private lateinit var signin: Button
    private lateinit var adminLogin : FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_homeui)

        signup = findViewById(R.id.signup)
        signin = findViewById(R.id.signin)
        adminLogin = findViewById(R.id.adminLoginBtn)

        signup.setOnClickListener{
            val intent = Intent(this, Registration::class.java)
            startActivity(intent)
        }

        signin.setOnClickListener{
            val intent = Intent( this,Userlogin::class.java )
            startActivity(intent)
        }

        adminLogin.setOnClickListener {
            val intent = Intent( this,AdminLogin::class.java )
            startActivity(intent)
        }
    }
}