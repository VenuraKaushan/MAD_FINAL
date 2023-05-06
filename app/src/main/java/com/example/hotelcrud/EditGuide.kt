//package com.example.hotelcrud
//
//import android.content.Intent
//import androidx.appcompat.app.AppCompatActivity
//import android.os.Bundle
//import android.provider.ContactsContract.CommonDataKinds.Email
//import android.widget.Button
//import android.widget.EditText
//import android.widget.TextView
//import android.widget.Toast
//import androidx.appcompat.app.AlertDialog
//import com.google.firebase.database.FirebaseDatabase
//
//class EditGuide : AppCompatActivity() {
//    private lateinit var tvGuideId: TextView
//    private lateinit var tvGuideName: TextView
//    private lateinit var tvGuideEmail: TextView
//    private lateinit var tvContactNumber: TextView
//    private lateinit var Edit: Button
//
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.edit_guide)
//
//        initView()
//        setValuesToViews()
//
//        Edit.setOnClickListener {
//            openUpdateDialog(
//                intent.getStringExtra("empId").toString(),
//                intent.getStringExtra("empName").toString()
//            )
//        }
//    }
//
//
//    private fun initView() {
//        tvGuideId = findViewById(R.id.tvGuideName)
//        tvGuideName = findViewById(R.id.tvGuideName)
//        tvGuideEmail = findViewById(R.id.TvGuideEmail)
//        tvContactNumber = findViewById(R.id.tvContactNumber)
//
//        Edit = findViewById(R.id.Edit)
//    }
//
//    private fun setValuesToViews() {
//        tvGuideId.text = intent.getStringExtra("guideId")
//        tvGuideName.text = intent.getStringExtra("guideName")
//        tvGuideEmail.text = intent.getStringExtra("guideEmail")
//        tvContactNumber.text = intent.getStringExtra("number")
//
//    }
//
//    private fun openUpdateDialog(
//        guideId: String,
//        guideName: String
//    ){
//        val mDialog = AlertDialog.Builder(this)
//        val inflater = layoutInflater
//        val mDialogView = inflater.inflate(R.id.Edit)
//
//        mDialog.setView(mDialogView)
//        val etGuideName =  mDialogView.findViewById<EditText>(R.id.etGuideName)
//        val etGuideEmail =  mDialogView.findViewById<EditText>(R.id.etGuideEmail)
//        val etContactNumber =  mDialogView.findViewById<EditText>(R.id.etContactNumber)
//        val Edit =  mDialogView.findViewById<Button>(R.id.Edit)
//
//        etGuideName.setText(intent.getStringExtra("guideName").toString())
//        etGuideEmail.setText(intent.getStringExtra("guideEmail").toString())
//        etContactNumber.setText(intent.getStringExtra("cantactNumber").toString())
//
//        mDialog.setTitle("Updating $guideName Record")
//
//        val alertDialog = mDialog.create()
//        alertDialog.show()
//
//        Edit.setOnClickListener{
//            updateEmpData(
//                guideId,
//                etGuideName.text.toString(),
//                etGuideEmail.text.toString(),
//                etContactNumber.text.toString()
//            )
//
//            Toast.makeText(applicationContext, "Guide Data Edit", Toast.LENGTH_LONG).show()
//
//            tvGuideName.text = etGuideName.text.toString()
//            tvGuideEmail.text = etGuideEmail.text.toString()
//            tvContactNumber.text = etContactNumber.text.toString()
//
//            alertDialog.dismiss()
//        }
//    }
//
//
//
//}
package com.example.hotelcrud

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class EditGuide : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_guide)
    }
}