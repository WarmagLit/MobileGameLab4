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
import android.widget.Toast
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
import com.tsu.mobilegamelab4.game.items.Key
import kotlin.math.abs

class CasesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCasesBinding

    var myViewPager2: ViewPager2? = null
    var casesAdapter: CasesAdapter? = null
    var keysAdapter: KeysAdapter? = null

    // values of the draggable views (usually this should come from a data source)
    private val keys: MutableList<String> = mutableListOf()

    private val cases: MutableList<String> = mutableListOf("red", "green", "blue", "yellow")

    // last selected word
    private var selectedKey: String = "red"

    private var currentScore = 0
    private var redKeysCount = 0
    private var greenKeysCount = 0
    private var blueKeysCount = 0
    private var yellowKeysCount = 0

    private lateinit var auth: FirebaseAuth
    private val database = Firebase.database
    private lateinit var myRef: DatabaseReference
    lateinit var currentUser: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCasesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize Firebase Auth
        auth = Firebase.auth
        val userUid = auth.currentUser?.uid.toString()
        myRef = database.getReference("users").child(userUid)

        getDataFromDatabase()
        initDragAndDropKeys()

        binding.casesGoBackButton.setOnClickListener {
            finish()
        }

        myViewPager2 = binding.viewpager

        casesAdapter = CasesAdapter(this).apply {
            submitList(cases)
        }
        binding.viewpager.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        binding.viewpager.adapter = casesAdapter
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
            redKeysCount,
            greenKeysCount,
            blueKeysCount,
            yellowKeysCount,
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
                    currentScore = value.score
                    redKeysCount = value.redKeys
                    greenKeysCount = value.greenKeys
                    blueKeysCount = value.blueKeys
                    yellowKeysCount = value.yellowKeys

                    keys.clear()
                    for (i in 1..redKeysCount) keys.add("red")
                    for (i in 1..greenKeysCount) keys.add("green")
                    for (i in 1..blueKeysCount) keys.add("blue")
                    for (i in 1..yellowKeysCount) keys.add("yellow")
                    keysAdapter?.submitList(keys)

                    binding.casesScoreTextView.text = value.score.toString()
                }
                //Log.d("TAG", "Value is: $value")
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                //Log.w("TAG", "Failed to read value.", error.toException())
            }
        })
    }

    private fun initDragAndDropKeys() {
        keysAdapter = KeysAdapter {
            selectedKey = it
        }

        binding.viewpager.setOnDragListener(
            DropListener {
                if (cases[binding.viewpager.currentItem] == selectedKey) {
                    keysAdapter?.removeItem(selectedKey)
                    when (selectedKey) {
                        "red" -> redKeysCount--
                        "green" -> greenKeysCount--
                        "yellow" -> yellowKeysCount--
                        "blue" -> blueKeysCount--
                    }
                    showPrizeDialog()
                } else {
                    Toast.makeText(this, "Сase and key in different colors!", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        )


        binding.recyclerView.layoutManager =
            FlexboxLayoutManager(this, FlexDirection.COLUMN, FlexWrap.WRAP).apply {
                justifyContent = JustifyContent.SPACE_EVENLY
                alignItems = AlignItems.CENTER
            }

        binding.recyclerView.adapter = keysAdapter
    }
}