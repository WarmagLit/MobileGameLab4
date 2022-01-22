package com.tsu.mobilegamelab4.game.surfaceview

import android.app.Application
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import com.tsu.mobilegamelab4.SharedPreference
import com.tsu.mobilegamelab4.database.User
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GameRepository(app: Application) {

    private val sharedPreferences = SharedPreference(app)
    private val auth: FirebaseAuth = Firebase.auth
    private val database = Firebase.database
    private val myRef: DatabaseReference = database.getReference("users").child(getCurrentUserId())
    private var currentUser: User? = null

    fun getControls() =
        sharedPreferences.getValueBoolean(SharedPreference.CONTROLS_KEY, true)

    fun getPerformance() =
        sharedPreferences.getValueBoolean(SharedPreference.PERFORMANCE_KEY, false)


    val currentUserInfo: Flow<User> = flow {
        while (true) {
            currentUser?.let {
                val latest = it
                emit(latest)
            }
            delay(500)
        }
    }

    init {
        getCurrentUserFromDatabase()
    }

    private fun getCurrentUserFromDatabase() {
        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                val value = dataSnapshot.getValue<User>()
                if (value != null) {
                    currentUser = value
                }
                Log.d("TAG", "Value is: $value")
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                Log.w("TAG", "Failed to read value.", error.toException())
            }
        })
    }

    fun updateUser(user: User) {
        val key = myRef.push().key
        if (key == null) {
            Log.w("TAG", "Couldn't get push key for posts")
            return
        }

        val postValues = user.toMap()
        val childUpdates = hashMapOf<String, Any>(
            "/users/${user.uid}" to postValues
        )

        database.reference.updateChildren(childUpdates)
    }

    private fun getCurrentUserId(): String = auth.currentUser?.uid.toString()
}