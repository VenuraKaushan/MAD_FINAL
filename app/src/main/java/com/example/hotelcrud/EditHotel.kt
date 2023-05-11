package com.example.hotelcrud

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.hotelcrud.adapters.HotelAdapter
import com.example.hotelcrud.databinding.ActivityEditHotelBinding
import com.example.hotelcrud.model.HotelModel
import com.google.firebase.database.*

class EditHotel : AppCompatActivity() {

    private lateinit var hotelRecyclerView: RecyclerView
    private lateinit var hotelList :  ArrayList<HotelModel>

    private lateinit var dbRef: DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_hotel)


        hotelRecyclerView = findViewById(R.id.recyclerView)
        hotelRecyclerView.layoutManager = LinearLayoutManager(this)
        hotelRecyclerView.setHasFixedSize(true)

        hotelList = arrayListOf<HotelModel>()

        getHotelData()
    }

    private fun getHotelData() {

        hotelRecyclerView.visibility = View.GONE

        dbRef = FirebaseDatabase.getInstance().getReference("Hotel")

        dbRef.addValueEventListener(object : ValueEventListener{

            override fun onDataChange(snapshot: DataSnapshot) {
                hotelList.clear()

                if(snapshot.exists()){

                    for(hotelSnap in snapshot.children){
                        val hotelData = hotelSnap.getValue(HotelModel::class.java)
                        hotelList.add(hotelData!!)
                    }

                    val mAdapter = HotelAdapter(hotelList)
                    hotelRecyclerView.adapter = mAdapter

                    mAdapter.setOnItemClickListener(object: HotelAdapter.onItemClickListener{
                        override fun onItemClick(position: Int) {
                            val intent = Intent(this@EditHotel,HotelDetails::class.java)

                            //put extras data
                            intent.putExtra("hotelId",hotelList[position].hotelId)
                            intent.putExtra("hotelName",hotelList[position].hotelName)
                            intent.putExtra("hotelDescription",hotelList[position].hotelDescription)
                            intent.putExtra("hotelAmount",hotelList[position].hotelAmount)

                            startActivity(intent)


                        }


                    })

                    hotelRecyclerView.visibility = View.VISIBLE
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })

    }
}