package com.tsu.mobilegamelab4.cases

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import android.widget.TextView
import androidx.viewpager2.widget.ViewPager2
import com.google.android.flexbox.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import com.tsu.mobilegamelab4.R
import com.tsu.mobilegamelab4.cases.dragndrop.adapter.KeysAdapter
import com.tsu.mobilegamelab4.cases.dragndrop.callback.DropListener
import com.tsu.mobilegamelab4.database.User
import com.tsu.mobilegamelab4.databinding.ActivityCasesBinding
import kotlin.math.abs

class CasesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCasesBinding

    var myViewPager2: ViewPager2? = null
    var MyAdapter: MyAdapter? = null

    // values of the draggable views (usually this should come from a data source)
    private val words = mutableListOf(
        "1",
        "2",
        "3",
        "4",
        "5",
        "6",
        "7",
        "8"
    )

    // last selected word
    private var selectedWord = ""

    private var currentScore = 0

    private lateinit var auth: FirebaseAuth
    private val database = Firebase.database
    private lateinit var myRef: DatabaseReference
    lateinit var currentUser: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCasesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initDragAndDropKeys()

        // Initialize Firebase Auth
        auth = Firebase.auth
        val userUid = auth.currentUser?.uid.toString()
        myRef = database.getReference("users").child(userUid)

        getDataFromDatabase()

        binding.casesGoBackButton.setOnClickListener {
            finish()
        }

        myViewPager2 = binding.viewpager

        MyAdapter = MyAdapter(this)
        binding.viewpager.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        binding.viewpager.adapter = MyAdapter
        binding.viewpager.offscreenPageLimit = 5

        val pageMargin = resources.getDimensionPixelOffset(R.dimen.pageMargin).toFloat()
        val pageOffset = resources.getDimensionPixelOffset(R.dimen.offset).toFloat()

        binding.viewpager.setPageTransformer(ViewPager2.PageTransformer { page: View, position: Float ->
            val myOffset = position * -(2 * pageOffset + pageMargin)
            if (position < -1) {
                page.translationX = -myOffset
            } else if (position <= 1) {
                val scaleFactor =
                    0.7f.coerceAtLeast(1 - abs(position))
                page.translationX = myOffset
                page.scaleY = scaleFactor
                page.alpha = scaleFactor
            } else {
                page.alpha = 0.5f
                page.translationX = myOffset
            }
        })

    }

    private fun sendNewScore() {
        // Create new post at /user-posts/$userid/$postid and at
        // /posts/$postid simultaneously
        val key = myRef.push().key
        if (key == null) {
            Log.w("TAG", "Couldn't get push key for posts")
            return
        }
        val user = User(
            currentUser.uid,
            currentUser.nickname,
            currentUser.pass,
            currentScore,
            currentUser.redKeys,
            currentUser.greenKeys,
            currentUser.blueKeys,
            currentUser.yellowKeys,
            currentUser.levelsCompleted
        )
        val postValues = user.toMap()

        val childUpdates = hashMapOf<String, Any>(
            "/users/${currentUser.uid}" to postValues
        )

        database.reference.updateChildren(childUpdates)
    }

    private fun showPrizeDialog() {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(
            Window.FEATURE_NO_TITLE
        )

        dialog.setContentView(R.layout.dialog_opencase_layout)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        dialog.setCanceledOnTouchOutside(true)

        val textAmount = dialog.findViewById<TextView>(R.id.dialogAmounttextView)
        val rnds = (100..1000).random()
        currentScore += rnds
        sendNewScore()
        textAmount.text = rnds.toString()

        dialog.show()
    }

    private fun getDataFromDatabase() {
        // Read from the database
        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                val value = dataSnapshot.getValue<User>()
                if (value != null) {
                    currentUser = value
                    currentScore = value.score.toInt()
                    binding.casesScoreTextView.text = value.score.toString()
                }
                Log.d("TAG", "Value is: $value")
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                Log.w("TAG", "Failed to read value.", error.toException())
            }
        })
    }

    private fun initDragAndDropKeys() {
        val wordsAdapter = KeysAdapter {
            selectedWord = it
        }.apply {
            submitList(words)
        }


        binding.viewpager.setOnDragListener(
            DropListener {
                wordsAdapter.removeItem(selectedWord)
                showPrizeDialog()
                Log.d("LoGGG", "Key dropped")
            }
        )


        binding.recyclerView.layoutManager =
            FlexboxLayoutManager(this, FlexDirection.COLUMN, FlexWrap.WRAP).apply {
                justifyContent = JustifyContent.SPACE_EVENLY
                alignItems = AlignItems.CENTER
            }

        binding.recyclerView.adapter = wordsAdapter
    }
}