package com.example.hotelcrud

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class userprofile : AppCompatActivity() {

    //    get input box
    private lateinit var name: TextView
    private lateinit var email: TextView
    private lateinit var phone: TextView
    private lateinit var btnUpdate: Button
    private lateinit var btnDelete: Button

    //    create database variables
    private lateinit var dbRef: DatabaseReference
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_userprofile)


//        initialized the text boxes
        name = findViewById(R.id.name_value)
        email = findViewById(R.id.email_value)
        phone = findViewById(R.id.phone_value)
        btnUpdate = findViewById(R.id.btnUpdate)
        btnDelete = findViewById(R.id.btnDelete)

        //        get user details to profile
        getUserdetails()

        btnUpdate.setOnClickListener {
            openUpdateDialog(
                name.text.toString(),
                email.text.toString(),
                phone.text.toString()
            )
        }

        btnDelete.setOnClickListener {
            deleteRecord()
        }

    }

    private fun deleteRecord() {
        val dbRef = FirebaseDatabase.getInstance().getReference("user").child(auth.currentUser?.uid.toString())
        val mTask = dbRef.removeValue()

        auth.currentUser!!.delete().addOnCompleteListener {
            mTask.addOnSuccessListener {
                Toast.makeText(this, "Employee Data Deleted", Toast.LENGTH_LONG).show()
                val intent = Intent(this, homeui::class.java)
                startActivity(intent)
                finish()
            }.addOnFailureListener { error ->
                Toast.makeText(this, "Delete Error ${error.message}", Toast.LENGTH_LONG).show()
            }
        }.addOnFailureListener {
            Toast.makeText(this,"cannot delete user",Toast.LENGTH_SHORT).show()
        }

    }

    private fun getUserdetails() {
        dbRef = FirebaseDatabase.getInstance().getReference("user")
        auth = FirebaseAuth.getInstance()

//        get current loggedin user id
        val uid = auth.uid.toString()

        dbRef.child(uid).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                val user = snapshot.getValue(TouristModel::class.java)!!

                name.setText(user.empName)
                email.setText(user.mail)
                phone.setText(user.Num)

            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(
                    this@userprofile,
                    "something went wrong while fetching profile data",
                    Toast.LENGTH_SHORT
                ).show()
            }

        })

    }

    private fun openUpdateDialog(
        empName: String,
        mail: String,
        Num: String

    ) {
        val mDialog = AlertDialog.Builder(this)
        val inflater = layoutInflater
        val mDialogView = inflater.inflate(R.layout.activity_update_dialog, null)

        mDialog.setView(mDialogView)

        val EmpName = mDialogView.findViewById<EditText>(R.id.EmpName)
        val empEmail = mDialogView.findViewById<EditText>(R.id.empEmail)
        val empPhone = mDialogView.findViewById<EditText>(R.id.empPhone)
        val btnUpdateData = mDialogView.findViewById<TextView>(R.id.btnUpdateData)

        EmpName.setText(empName)
        empEmail.setText(mail)
        empPhone.setText(Num)

        mDialog.setTitle("Updating $empName Record")

        val alertDialog = mDialog.create()
        alertDialog.show()

        btnUpdateData.setOnClickListener {
            editUserDetails(EmpName, empEmail, empPhone, alertDialog)
        }

    }

    private fun editUserDetails(
        EmpName: EditText,
        empEmail: EditText,
        empPhone: EditText,
        alertBox: AlertDialog
    ) {
        dbRef = FirebaseDatabase.getInstance().getReference("user")

        val uname = EmpName!!.text.toString()
        val uEmail = empEmail!!.text.toString()
        val uPhone = empPhone!!.text.toString()


        val updateUser = mapOf(
            "touristID" to auth.currentUser?.uid.toString(),
            "empName" to uname,
            "mail" to uEmail,
            "num" to uPhone
        )

        dbRef.child(auth.currentUser?.uid.toString()).updateChildren(updateUser)
            .addOnCompleteListener {

                if (it.isSuccessful) {
                    Toast.makeText(this, "User updated successfully!", Toast.LENGTH_SHORT).show()

                    //close the alertbox
                    alertBox.dismiss()
                }
            }.addOnFailureListener {
                Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show()
            }

    }


}