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
    private lateinit var tvplaceId: TextView
    private lateinit var tvplaceName: TextView
    private lateinit var tvdescription: TextView
    private lateinit var tvimportance: TextView
    private lateinit var btnUpdate: Button
    private lateinit var btnDelete: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_employee_details)

        initView()
        setValuesToViews()

        btnUpdate.setOnClickListener {
            openUpdateDialog(
                intent.getStringExtra("placeId").toString(),
                intent.getStringExtra("placeName").toString()
            )
        }

        btnDelete.setOnClickListener {
            deleteRecord(
                intent.getStringExtra("placeId").toString()
            )
        }

    }

    private fun deleteRecord(
        id: String
    ){
        val dbRef = FirebaseDatabase.getInstance().getReference("Places").child(id)
        val mTask = dbRef.removeValue()

        mTask.addOnSuccessListener {
            Toast.makeText(this, "Place data deleted", Toast.LENGTH_LONG).show()

            val intent = Intent(this, FetchingActivity::class.java)
            finish()
            startActivity(intent)
        }.addOnFailureListener{ error ->
            Toast.makeText(this, "Deleting Err ${error.message}", Toast.LENGTH_LONG).show()
        }
    }

    private fun initView() {
        tvplaceId = findViewById(R.id.tvplaceId)
        tvplaceName = findViewById(R.id.tvplaceName)
        tvdescription = findViewById(R.id.tvdescription)
        tvimportance = findViewById(R.id.tvimportance)

        btnUpdate = findViewById(R.id.btnUpdate)
        btnDelete = findViewById(R.id.btnDelete)
    }

    private fun setValuesToViews() {
        tvplaceId.text = intent.getStringExtra("placeId")
        tvplaceName.text = intent.getStringExtra("placeName")
        tvdescription.text = intent.getStringExtra("description")
        tvimportance.text = intent.getStringExtra("importance")

    }

    private fun openUpdateDialog(
        placeId: String,
        placeName: String
    ){
        val mDialog = AlertDialog.Builder(this)
        val inflater = layoutInflater
        val mDialogView = inflater.inflate(R.layout.update_dialog,null)

        mDialog.setView(mDialogView)
        val etplaceName =  mDialogView.findViewById<EditText>(R.id.etplaceName)
        val etdescription =  mDialogView.findViewById<EditText>(R.id.etdescription)
        val etimportance =  mDialogView.findViewById<EditText>(R.id.etimportance)
        val btnUpdateData =  mDialogView.findViewById<Button>(R.id.btnUpdateData)

        etplaceName.setText(intent.getStringExtra("placeName").toString())
        etdescription.setText(intent.getStringExtra("description").toString())
        etimportance.setText(intent.getStringExtra("importance").toString())

        mDialog.setTitle("Updating $placeName Record")

        val alertDialog = mDialog.create()
        alertDialog.show()

        btnUpdateData.setOnClickListener{
            updateEmpData(
                placeId,
                etplaceName.text.toString(),
                etdescription.text.toString(),
                etimportance.text.toString()
            )

            Toast.makeText(applicationContext, "Employee Data Updated", Toast.LENGTH_LONG).show()

            tvplaceName.text = etplaceName.text.toString()
            tvdescription.text = etdescription.text.toString()
            tvimportance.text = etimportance.text.toString()

            alertDialog.dismiss()
        }
    }

    private fun updateEmpData(
        id:String,
        name:String,
        age:String,
        salary:String
    ){
        val dbRef = FirebaseDatabase.getInstance().getReference("Employees").child(id)
        val empInfo = EmployeeModel(id, name, age, salary)
        dbRef.setValue(empInfo)
    }

}