package com.tsu.mobilegamelab4.chooselevel

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.tsu.mobilegamelab4.R
import com.tsu.mobilegamelab4.databinding.ActivityChooseLevelBinding
import com.tsu.mobilegamelab4.game.GameActivity
import com.tsu.mobilegamelab4.googlemap.GoogleMapActivity
import com.tsu.mobilegamelab4.menu.MenuActivity

class ChooseLevelActivity : AppCompatActivity() {

    private lateinit var binding: ActivityChooseLevelBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChooseLevelBinding.inflate(layoutInflater)
        setContentView(binding.root)

        configureLevelPanels()
        setOnClickListeners()
    }

    private fun configureLevelPanels() = with(binding.chooseLevelBonusLevelPanelInclude) {
        includeLevelSubTitleTextView.text = resources.getString(R.string.bonus)
        includeLevelPreviewImageView.setImageResource(R.drawable.top_secret)
        includeLevelNameTextView.text = resources.getString(R.string.question_marks)
    }

    private fun setOnClickListeners() {
        binding.chooseLevelFirstLevelPanelInclude.root.setOnClickListener {
            val intent = Intent(this, GameActivity::class.java)
            intent.putExtra(ChooseLevelViewModel.LEVEL_KEY, 1)
            startActivity(intent)
        }

        binding.chooseLevelSecondLevelPanelInclude.root.setOnClickListener {
            val intent = Intent(this, GameActivity::class.java)
            intent.putExtra(ChooseLevelViewModel.LEVEL_KEY, 2)
            startActivity(intent)
        }

        binding.chooseLevelBonusLevelPanelInclude.root.setOnClickListener {
            val intent = Intent(this, GoogleMapActivity::class.java)
            startActivity(intent)
        }

        binding.chooseLevelBackButton.setOnClickListener {
            val intent = Intent(this, MenuActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onBackPressed() {

    }
}