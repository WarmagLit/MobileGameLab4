package com.tsu.mobilegamelab4.chooselevel

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.tsu.mobilegamelab4.R
import com.tsu.mobilegamelab4.databinding.ActivityChooseLevelBinding
import com.tsu.mobilegamelab4.game.GameActivity
import com.tsu.mobilegamelab4.googlemap.GoogleMapActivity
import com.tsu.mobilegamelab4.menu.MenuActivity

class ChooseLevelActivity : AppCompatActivity() {

    private lateinit var binding: ActivityChooseLevelBinding
    private val viewModel by viewModels<ChooseLevelViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChooseLevelBinding.inflate(layoutInflater)
        setContentView(binding.root)

        configureLevelPanels()
        setOnClickListeners()
        setObserver()
    }

    private fun setObserver() {
        viewModel.levelsCompleted.observe(this) { completedLevels ->
            if (completedLevels >= 1) with(binding.chooseLevelSecondLevelPanelInclude.root) {
                setBackgroundResource(R.drawable.level_holder)
                isEnabled = true
            }

            if (completedLevels >= 2) with(binding.chooseLevelThirdLevelPanelInclude.root) {
                setBackgroundResource(R.drawable.level_holder)
                isEnabled = true
            }

            if (completedLevels >= 3) with(binding.chooseLevelBonusLevelPanelInclude.root) {
                setBackgroundResource(R.drawable.level_holder)
                isEnabled = true
            }
        }
    }

    private fun configureLevelPanels() {
        with(binding.chooseLevelFirstLevelPanelInclude) {
            root.setBackgroundResource(R.drawable.level_holder)
        }

        with(binding.chooseLevelSecondLevelPanelInclude) {
            includeLevelSubTitleTextView.text = resources.getString(R.string.level_2)
            includeLevelPreviewImageView.setImageResource(R.drawable.top_secret)
            includeLevelNameTextView.text = resources.getString(R.string.continues)
            root.isEnabled = false
        }

        with(binding.chooseLevelThirdLevelPanelInclude) {
            includeLevelSubTitleTextView.text = resources.getString(R.string.level_3)
            includeLevelPreviewImageView.setImageResource(R.drawable.top_secret)
            includeLevelNameTextView.text = resources.getString(R.string.end)
            root.isEnabled = false
        }

        with(binding.chooseLevelBonusLevelPanelInclude) {
            includeLevelSubTitleTextView.text = resources.getString(R.string.bonus)
            includeLevelPreviewImageView.setImageResource(R.drawable.top_secret)
            includeLevelNameTextView.text = resources.getString(R.string.question_marks)
            root.isEnabled = false
        }
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

        binding.chooseLevelThirdLevelPanelInclude.root.setOnClickListener {
            val intent = Intent(this, GameActivity::class.java)
            intent.putExtra(ChooseLevelViewModel.LEVEL_KEY, 3)
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