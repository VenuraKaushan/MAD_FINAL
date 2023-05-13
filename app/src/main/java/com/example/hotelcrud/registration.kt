package com.example.hotelcrud

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract.Data
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.hotelcrud.databinding.ActivityRegistrationBinding
import com.example.hotelcrud.databinding.ActivityUserloginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class registration : AppCompatActivity() {

    private lateinit var binding: ActivityRegistrationBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var dbRef : DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRegistrationBinding.inflate(layoutInflater)

        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()



        binding.btnReg.setOnClickListener{

            val name = binding.Name.text.toString()
            val email = binding.Email.text.toString()
            val phone = binding.Phone.text.toString()
            val password = binding.password.text.toString()
            val repassword = binding.Repassword.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty() && repassword.isNotEmpty()){
                if(password == repassword)
                {
                        firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener{
                            if(it.isSuccessful)
                            {

//                                    store user other detail in the firebase realtime
                                    val user = TouristModel(it.result.user?.uid.toString(),name,email,phone)

//                                create database refference
                                dbRef = FirebaseDatabase.getInstance().getReference("user")

                                dbRef.child(it.result.user?.uid.toString()).setValue(user).addOnSuccessListener {
                                    Toast.makeText(this,"Registered successfully!",Toast.LENGTH_SHORT).show()
                                }.addOnFailureListener {
                                    Toast.makeText(this,"Something went Wrong!",Toast.LENGTH_SHORT).show()
                                }

                                    val intent  = Intent(this,homeui::class.java)
                                    startActivity(intent)
                            }
                            else{
                                Toast.makeText(this,it.exception.toString(),Toast.LENGTH_SHORT).show()
                            }
                        }

                }else{
                    Toast.makeText(this,"Password is Not Matching",Toast.LENGTH_SHORT).show()
                }

            }else{
                Toast.makeText(this,"Empty Fields Are not Allowed!!",Toast.LENGTH_SHORT).show()
            }

        }


    }


}