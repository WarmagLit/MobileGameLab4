package com.tsu.mobilegamelab4.cases.inventory

import android.util.Log
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class Inventory {
    // Write a message to the database
    val database = FirebaseDatabase.getInstance()
    val myRef = database.getReference("message")

    //myRef.setValue("Hello, World!")
    init {
        myRef.setValue("Hello, Database!!!").addOnSuccessListener {
            Log.d("Tag", "SUCCESSSSS")
        }.addOnFailureListener {
            Log.d("Tag", "Failuere")
        }
        Log.d("Tag", "Message sent")
    }
}