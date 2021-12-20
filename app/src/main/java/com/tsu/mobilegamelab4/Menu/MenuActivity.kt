package com.tsu.mobilegamelab4.Menu

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.tsu.mobilegamelab4.Cases.CasesActivity
import com.tsu.mobilegamelab4.Game.GameActivity
import com.tsu.mobilegamelab4.R
import com.tsu.mobilegamelab4.Settings.SettingsActivity
import com.tsu.mobilegamelab4.databinding.ActivityMenuBinding

class MenuActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMenuBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.menuPlayButton.setOnClickListener {
            val intent = Intent(this, GameActivity::class.java)
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
    }
}