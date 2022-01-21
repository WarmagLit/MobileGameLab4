package com.tsu.mobilegamelab4.scoreboard

import android.util.Log
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import com.tsu.mobilegamelab4.database.User
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ScoreBoardRepository {

    private val database = Firebase.database
    private val myRef = database.getReference("users")
    private var userList: List<User> = listOf()

    val latestUserList: Flow<List<User>> = flow {
        while (true) {
            val latestUsers = userList
            emit(latestUsers)
            delay(500)
        }
    }

    init {
        getUserListFromDatabase()
    }

    private fun getUserListFromDatabase() {

        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val localUserList: MutableList<User> = mutableListOf()
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                for (ds in dataSnapshot.children) {
                    val user = ds.getValue<User>()
                    if (user != null)
                        localUserList.add(user)
                    Log.d("TAG", "Value is: $user")
                }
                userList = localUserList
                //Log.d("TAG", "Value is: $value")
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                Log.w("TAG", "Failed to read value.", error.toException())
            }
        })
    }

    fun getCurrentUserId(): String = Firebase.auth.currentUser?.uid.toString()
}