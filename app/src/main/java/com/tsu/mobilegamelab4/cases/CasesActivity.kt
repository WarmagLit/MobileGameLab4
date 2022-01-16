package com.tsu.mobilegamelab4.cases

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.viewpager2.widget.ViewPager2
import com.tsu.mobilegamelab4.R
import com.tsu.mobilegamelab4.databinding.ActivityCasesBinding
import kotlin.math.abs

class CasesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCasesBinding

    var myViewPager2: ViewPager2? = null
    var MyAdapter: MyAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCasesBinding.inflate(layoutInflater)
        setContentView(binding.root)


        myViewPager2 = binding.viewpager

        MyAdapter = MyAdapter(this)
        binding.viewpager.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        binding.viewpager.adapter = MyAdapter
        binding.viewpager.offscreenPageLimit = 3

        val pageMargin = resources.getDimensionPixelOffset(R.dimen.pageMargin).toFloat()
        val pageOffset = resources.getDimensionPixelOffset(R.dimen.offset).toFloat()

        binding.viewpager.setPageTransformer(ViewPager2.PageTransformer { page: View, position: Float ->
            val myOffset = position * -(2 * pageOffset + pageMargin)
            if (position < -1) {
                page.translationX = -myOffset
            } else if (position <= 1) {
                val scaleFactor =
                    0.7f.coerceAtLeast(1 - abs(position - 0.14285715f))
                page.translationX = myOffset
                page.scaleY = scaleFactor
                page.alpha = scaleFactor
            } else {
                page.alpha = 0f
                page.translationX = myOffset
            }
        })
    }

}