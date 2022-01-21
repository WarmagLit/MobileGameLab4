package com.tsu.mobilegamelab4.menu

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.tsu.mobilegamelab4.cases.CasesActivity
import com.tsu.mobilegamelab4.chooselevel.ChooseLevelActivity
import com.tsu.mobilegamelab4.settings.SettingsActivity
import com.tsu.mobilegamelab4.databinding.ActivityMenuBinding
import com.tsu.mobilegamelab4.video.VideoActivity
import com.tsu.mobilegamelab4.scoreboard.ScoreBoardActivity

class MenuActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMenuBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setOnClickListeners()
    }

    private fun setOnClickListeners() {
        binding.menuPlayButton.setOnClickListener {
            val intent = Intent(this, ChooseLevelActivity::class.java)
            startActivity(intent)
        }

        binding.menuOpenCaseButton.setOnClickListener {
            val intent = Intent(this, CasesActivity::class.java)
            startActivity(intent)
        }

        binding.menuSettingsButton.setOnClickListener {
            val intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent)
        }

        binding.menuScoreBoardButton.setOnClickListener {
            val intent = Intent(this, ScoreBoardActivity::class.java)
            startActivity(intent)
        }

        binding.menuTitleTextView.setOnClickListener {
            val intent = Intent(this, VideoActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onBackPressed() {
        finishAffinity()
    }
}