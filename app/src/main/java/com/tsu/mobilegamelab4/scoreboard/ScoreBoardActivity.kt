package com.tsu.mobilegamelab4.scoreboard

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import com.tsu.mobilegamelab4.database.User
import com.tsu.mobilegamelab4.databinding.ActivityScoreBoardBinding
import com.tsu.mobilegamelab4.databinding.ActivitySignUpBinding

class ScoreBoardActivity : AppCompatActivity() {

    private lateinit var binding: ActivityScoreBoardBinding

    private val database = Firebase.database
    private lateinit var myRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScoreBoardBinding.inflate(layoutInflater)
        setContentView(binding.root)


        myRef = database.getReference("users")

        getDataFromDatabase()
    }

    private fun getDataFromDatabase() {
        // Read from the database

        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                val ds = dataSnapshot
                val userList: MutableList<User> = mutableListOf()
                for (ds in dataSnapshot.children) {
                    val user = ds.getValue<User>()
                    if (user != null)
                        userList.add(user)
                    Log.d("TAG", "Value is: $user")
                }
                loadTopFive(userList)
                //Log.d("TAG", "Value is: $value")
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                Log.w("TAG", "Failed to read value.", error.toException())
            }
        })
    }

    @SuppressLint("SetTextI18n")
    private fun loadTopFive(userList: List<User>) {
        // Read from the database
        val sortedList = userList.sortedByDescending {
            it.score
        }

        if (sortedList.size > 0) {
            binding.scoreBoardFirst.text = "1. ${sortedList[0].nickname} ${sortedList[0].score}"
        }
        if (sortedList.size > 1) {
            binding.scoreBoardSecond.text = "2. ${sortedList[1].nickname}  ${sortedList[1].score}"
        }
        if (sortedList.size > 2) {
            binding.scoreBoardThird.text = "3. ${sortedList[2].nickname}  ${sortedList[2].score}"
        }
        if (sortedList.size > 3) {
            binding.scoreBoardForth.text = "4. ${sortedList[3].nickname}  ${sortedList[3].score}"
        }
        if (sortedList.size > 4) {
            binding.scoreBoardFifth.text = "5. ${sortedList[4].nickname}  ${sortedList[4].score}"
        }


    }
}