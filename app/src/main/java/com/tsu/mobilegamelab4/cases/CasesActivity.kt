package com.tsu.mobilegamelab4.cases

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.media.AudioManager
import android.media.SoundPool
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.google.android.flexbox.*
import com.tsu.mobilegamelab4.R
import com.tsu.mobilegamelab4.cases.dragndrop.adapter.KeysAdapter
import com.tsu.mobilegamelab4.cases.dragndrop.callback.DropListener
import com.tsu.mobilegamelab4.databinding.ActivityCasesBinding
import kotlin.math.abs

class CasesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCasesBinding
    private val viewModel by viewModels<CasesViewModel>()

    private var myViewPager2: ViewPager2? = null
    private var casesAdapter: CasesAdapter? = null
    private var keysAdapter: KeysAdapter? = null

    // Audio
    private var soundPool: SoundPool? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCasesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loadAudio()
        initDragAndDropKeys()
        setOnClickListener()
        configureViewPager()
        setObservers()
    }

    private fun loadAudio() {
        // Load audio
        soundPool = SoundPool(2, AudioManager.STREAM_MUSIC, 0)
        soundPool?.load(baseContext, R.raw.open_case_sound, 1)
        soundPool?.load(baseContext, R.raw.case_change_sound, 2)
    }

    private fun setOnClickListener() {
        binding.casesGoBackButton.setOnClickListener {
            finish()
        }
    }

    private fun configureViewPager() {
        myViewPager2 = binding.viewpager

        casesAdapter = CasesAdapter(this).apply {
            submitList(viewModel.cases)
        }

        binding.viewpager.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        binding.viewpager.adapter = casesAdapter
        binding.viewpager.offscreenPageLimit = 5

        val pageMargin = resources.getDimensionPixelOffset(R.dimen.pageMargin).toFloat()
        val pageOffset = resources.getDimensionPixelOffset(R.dimen.offset).toFloat()

        binding.viewpager.setPageTransformer { page: View, position: Float ->
            val myOffset = position * -(2 * pageOffset + pageMargin)
            when {
                position < -1 -> {
                    page.translationX = -myOffset
                }
                position <= 1 -> {
                    val scaleFactor =
                        0.7f.coerceAtLeast(1 - abs(position))
                    page.translationX = myOffset
                    page.scaleY = scaleFactor
                    page.alpha = scaleFactor
                }
                else -> {
                    page.alpha = 0.5f
                    page.translationX = myOffset
                }
            }
        }
        binding.viewpager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                playSound2()
            }
        })
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
        viewModel.increaseScore(rnds)
        textAmount.text = rnds.toString()

        dialog.show()
    }

    private fun setObservers() {
        viewModel.keys.observe(this) { list ->
            keysAdapter?.submitList(list)
        }

        viewModel.score.observe(this) {
            binding.casesScoreTextView.text = it.toString()
        }
    }

    private fun playSound() {
        soundPool?.play(CasesViewModel.SOUND_OPEN_ID, 1F, 1F, 0, 0, 1F)
    }

    private fun playSound2() {
        soundPool?.play(CasesViewModel.SOUND_CHANGE_CASES_ID, 1F, 1F, 0, 0, 1F)
    }

    private fun initDragAndDropKeys() {
        // last selected word
        var selectedKey = ""

        keysAdapter = KeysAdapter {
            selectedKey = it
        }

        binding.viewpager.setOnDragListener(
            DropListener {
                if (viewModel.cases[binding.viewpager.currentItem] == selectedKey) {
                    viewModel.reduceKeyCount(selectedKey)
                    showPrizeDialog()
                    playSound()
                } else {
                    Toast.makeText(this, R.string.case_key_different_colors, Toast.LENGTH_SHORT)
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