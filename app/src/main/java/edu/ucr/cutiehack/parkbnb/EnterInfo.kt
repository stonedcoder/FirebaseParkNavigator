package edu.ucr.cutiehack.parkbnb

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class EnterInfo : AppCompatActivity() {

    private val editTextName: EditText? = null
    private var editTextPrice: EditText? = null
    private var editTextRate: EditText? = null
    private var lat: Double = 0.toDouble()
    private var lng: Double = 0.toDouble()

    private var buttonSubmit: Button? = null

    internal var spotNum = 100
    private var ref: DatabaseReference? = null
    //ref = FirebaseDatabase.getInstance().getReference();

    private var spotListener: ValueEventListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_enter_info)

        val i = intent
        val b = i.extras
        if (b != null) {
            lat = b.get("lat") as Double
            lng = b.get("lng") as Double
        }
        editTextPrice = findViewById(R.id.priceET) as EditText
        editTextRate = findViewById(R.id.rateET) as EditText
        buttonSubmit = findViewById(R.id.submitButton) as Button

        buttonSubmit!!.setOnClickListener { onSubmit() }
    }

    override fun onStart() {
        super.onStart()
        /*
        ref = FirebaseDatabase.getInstance().getReference("spot");

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {

                    String rate = editTextRate.getText().toString().trim();
                    String price = editTextPrice.getText().toString().trim();
                    String strSpotNum = String.valueOf(spotNum);
                    //ParkingSpot currSpot = new ParkingSpot(spotNum, price);
                    ref.child(strSpotNum).child("price").setValue(price);
                    ref.child(strSpotNum).child("rate").setValue(rate);
                    ref.child(strSpotNum).child("taken").setValue(false);
                    //databaseReference.child()

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
      */
    }


    fun onSubmit() {
        Log.i(TAG, "onsubmit clicked : ")
        ref = FirebaseDatabase.getInstance().getReference("spot")

        spotListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {

                    val rate_s = editTextRate!!.text.toString().trim { it <= ' ' }
                    val rate = java.lang.Double.parseDouble(rate_s)
                    val price_s = editTextPrice!!.text.toString().trim { it <= ' ' }
                    val price = java.lang.Double.parseDouble(price_s)
                    val strSpotNum = spotNum.toString()
                    //ParkingSpot currSpot = new ParkingSpot(spotNum, price);
                    ref!!.child(strSpotNum).child("price").setValue(price)
                    ref!!.child(strSpotNum).child("rate").setValue(rate)
                    ref!!.child(strSpotNum).child("taken").setValue(false)
                    ref!!.child(strSpotNum).child("id").setValue(spotNum)
                    ref!!.child(strSpotNum).child("lat").setValue(lat)
                    ref!!.child(strSpotNum).child("lng").setValue(lng)

                    //databaseReference.child()
                    //                    Toast.makeText(getApplicationContext(), "Your entry has been saved!", Toast.LENGTH_SHORT).show();

                    Log.i(TAG, "Bootom execution not done:  ")
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {

            }

        }
        ref!!.addValueEventListener(spotListener)
    }

    override fun onBackPressed() {
        ref!!.removeEventListener(spotListener!!)
        Log.w(TAG, "removed event listener")
        finish()
    }

    public override fun onPause() {
        ref!!.removeEventListener(spotListener!!)
        Log.w(TAG, "removed event listener")
        super.onPause()
    }

    companion object {

        private val TAG = "EnterInfo"
    }

}

