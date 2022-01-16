package com.tsu.mobilegamelab4.cases

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.google.android.flexbox.*
import com.tsu.mobilegamelab4.R
import com.tsu.mobilegamelab4.cases.dragndrop.adapter.WordsAdapter
import com.tsu.mobilegamelab4.cases.dragndrop.callback.DropListener
import com.tsu.mobilegamelab4.cases.dragndrop.adapter.SentenceAdapter
import com.tsu.mobilegamelab4.databinding.ActivityCasesBinding
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

    private fun initDragAndDropKeys() {
        val sentenceAdapter = SentenceAdapter()
        val wordsAdapter = WordsAdapter {
            selectedWord = it
        }.apply {
            submitList(words)
        }


        //binding.recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        //binding.recyclerView.adapter = sentenceAdapter


        binding.viewpager.setOnDragListener(
            DropListener {
                wordsAdapter.removeItem(selectedWord)
                sentenceAdapter.addItem(selectedWord)
                Log.d("LoGGG", "Key droppped")
            }
        )


        binding.recyclerView.layoutManager = FlexboxLayoutManager(this, FlexDirection.COLUMN, FlexWrap.WRAP).apply {
            justifyContent = JustifyContent.SPACE_EVENLY
            alignItems = AlignItems.CENTER
        }

        binding.recyclerView.adapter = wordsAdapter
    }
}