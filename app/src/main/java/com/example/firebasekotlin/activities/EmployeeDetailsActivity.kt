package com.example.firebasekotlin.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.firebasekotlin.R
import com.example.firebasekotlin.models.EmployeeModel
import com.google.firebase.database.FirebaseDatabase

class EmployeeDetailsActivity : AppCompatActivity() {
    private lateinit var tvGuideId: TextView
    private lateinit var tvGuideName: TextView
    private lateinit var tvGuideEmail: TextView
    private lateinit var tvPhone: TextView
    private lateinit var btnUpdate: Button
    private lateinit var btnDelete: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_employee_details)

        initView()
        setValuesToViews()

        btnUpdate.setOnClickListener {
            openUpdateDialog(
                intent.getStringExtra("guideId").toString(),
                intent.getStringExtra("guideName").toString()
            )
        }

        btnDelete.setOnClickListener {
            deleteRecord(
                intent.getStringExtra("guideId").toString()
            )
        }

    }

    private fun deleteRecord(
        id: String
    ){
        val dbRef = FirebaseDatabase.getInstance().getReference("Guide").child(id)
        val mTask = dbRef.removeValue()

        mTask.addOnSuccessListener {
            Toast.makeText(this, "Guide data deleted", Toast.LENGTH_LONG).show()

            val intent = Intent(this, FetchingActivity::class.java)
            finish()
            startActivity(intent)
        }.addOnFailureListener{ error ->
            Toast.makeText(this, "Deleting Err ${error.message}", Toast.LENGTH_LONG).show()
        }
    }

    private fun initView() {
        tvGuideId = findViewById(R.id.tvGuideId)
        tvGuideName = findViewById(R.id.tvGuideName)
        tvGuideEmail = findViewById(R.id.tvGuideEmail)
        tvPhone = findViewById(R.id.tvPhone)

        btnUpdate = findViewById(R.id.btnUpdate)
        btnDelete = findViewById(R.id.btnDelete)
    }

    private fun setValuesToViews() {
        tvGuideId.text = intent.getStringExtra("guideId")
        tvGuideName.text = intent.getStringExtra("guideName")
        tvGuideEmail.text = intent.getStringExtra("guideEmail")
        tvPhone.text = intent.getStringExtra("Phone")

    }

    private fun openUpdateDialog(
        guideId: String,
        guideName: String
    ){
        val mDialog = AlertDialog.Builder(this)
        val inflater = layoutInflater
        val mDialogView = inflater.inflate(R.layout.update_dialog,null)

        mDialog.setView(mDialogView)
        val etguideName =  mDialogView.findViewById<EditText>(R.id.etguideName)
        val etguideEmail =  mDialogView.findViewById<EditText>(R.id.etguideEmail)
        val etPhone =  mDialogView.findViewById<EditText>(R.id.etPhone)
        val btnUpdateData =  mDialogView.findViewById<Button>(R.id.btnUpdateData)

        etguideName.setText(intent.getStringExtra("guideName").toString())
        etguideEmail.setText(intent.getStringExtra("guideEmail").toString())
        etPhone.setText(intent.getStringExtra("Phone").toString())

        mDialog.setTitle("Updating $guideName Record")

        val alertDialog = mDialog.create()
        alertDialog.show()

        btnUpdateData.setOnClickListener{
            updateEmpData(
                guideId,
                etguideName.text.toString(),
                etguideEmail.text.toString(),
                etPhone.text.toString()
            )

            Toast.makeText(applicationContext, "Guide Data Updated", Toast.LENGTH_LONG).show()

            tvGuideName.text = etguideName.text.toString()
            tvGuideEmail.text = etguideEmail.text.toString()
            tvPhone.text = etPhone.text.toString()

            alertDialog.dismiss()
        }
    }

    private fun updateEmpData(
        id:String,
        name:String,
        age:String,
        salary:String
    ){
        val dbRef = FirebaseDatabase.getInstance().getReference("Guide").child(id)
        val empInfo = EmployeeModel(id, name, age, salary)
        dbRef.setValue(empInfo)
    }

}