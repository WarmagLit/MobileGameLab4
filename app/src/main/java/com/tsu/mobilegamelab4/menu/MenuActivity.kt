package com.tsu.mobilegamelab4.menu

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import com.tsu.mobilegamelab4.R
import com.tsu.mobilegamelab4.cases.CasesActivity
import com.tsu.mobilegamelab4.choose_level.ChooseLevelActivity
import com.tsu.mobilegamelab4.game.GameActivity
import com.tsu.mobilegamelab4.settings.SettingsActivity
import com.tsu.mobilegamelab4.databinding.ActivityMenuBinding
import com.tsu.mobilegamelab4.video.VideoActivity

class MenuActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMenuBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.menuPlayButton.setOnClickListener {
            val intent = Intent(this, ChooseLevelActivity::class.java)
            startActivity(intent)
        }

        binding.menuOpencaseButton.setOnClickListener {
            val intent = Intent(this, CasesActivity::class.java)
            startActivity(intent)
        }

        binding.menuSettingsButton.setOnClickListener {
            val intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent)
        }

        binding.textView.setOnClickListener {

            val intent = Intent(this, VideoActivity::class.java)
            startActivity(intent)
        }

    }

}