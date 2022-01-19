package com.tsu.mobilegamelab4.cases

import android.app.Dialog
import android.content.Intent
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
import com.google.android.material.button.MaterialButton
import com.google.firebase.FirebaseApp
import com.google.firebase.appcheck.FirebaseAppCheck
import com.google.firebase.appcheck.debug.DebugAppCheckProviderFactory
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import com.tsu.mobilegamelab4.R
import com.tsu.mobilegamelab4.cases.dragndrop.adapter.KeysAdapter
import com.tsu.mobilegamelab4.cases.dragndrop.callback.DropListener
import com.tsu.mobilegamelab4.cases.inventory.User
import com.tsu.mobilegamelab4.databinding.ActivityCasesBinding
import com.tsu.mobilegamelab4.menu.MenuActivity
import kotlin.math.abs

class CasesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCasesBinding

    var myViewPager2: ViewPager2? = null
    var MyAdapter: MyAdapter? = null

    // values of the draggable views (usually this should come from a data source)
    private val words = mutableListOf("world", "a", "!", "What", "wonderful", "world", "a", "!", "What","world", "a","erful", "world", "a", "!", "What", "wonderful")
    // last selected word
    private var selectedWord = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCasesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initDragAndDropKeys()

        val database = Firebase.database
        val myRef = database.getReference("message")

//        myRef.child("message").setValue("Hello, World!").addOnFailureListener {
//            Log.d("Tag", "Failure")
//        }.addOnSuccessListener {
//            Log.d("Tag", "Success")
//        }
//
//        // Read from the database
//        myRef.addValueEventListener(object : ValueEventListener {
//            override fun onDataChange(dataSnapshot: DataSnapshot) {
//                // This method is called once with the initial value and again
//                // whenever data at this location is updated.
//                val value = dataSnapshot.getValue<String>()
//                Log.d("TAG", "Value is: $value")
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//                // Failed to read value
//                Log.w("TAG", "Failed to read value.", error.toException())
//            }
//        })

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
                    0.7f.coerceAtLeast(1 - abs(position ))
                page.translationX = myOffset
                page.scaleY = scaleFactor
                page.alpha = scaleFactor
            } else {
                page.alpha = 0.5f
                page.translationX = myOffset
            }
        })

    }

    fun showPrizeDialog() {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(
            Window.FEATURE_NO_TITLE
        )
        //dialog.setCancelable(false)
        dialog.setContentView(R.layout.dialog_opencase_layout)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val textAmount = dialog.findViewById<TextView>(R.id.dialogAmounttextView)
        val rnds = (100..1000).random()
        textAmount.text = rnds.toString()

        dialog.show()
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


        binding.recyclerView.layoutManager = FlexboxLayoutManager(this, FlexDirection.COLUMN, FlexWrap.WRAP).apply {
            justifyContent = JustifyContent.SPACE_EVENLY
            alignItems = AlignItems.CENTER
        }

        binding.recyclerView.adapter = wordsAdapter
    }
}