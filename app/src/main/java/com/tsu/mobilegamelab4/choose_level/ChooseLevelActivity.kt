package com.tsu.mobilegamelab4.choose_level

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.tsu.mobilegamelab4.databinding.ActivityChooseLevelBinding
import com.tsu.mobilegamelab4.game.GameActivity
import com.tsu.mobilegamelab4.googlemap.GoogleMapActivity

class ChooseLevelActivity : AppCompatActivity() {

    private lateinit var binding: ActivityChooseLevelBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChooseLevelBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setOnClickListeners()
    }

    private fun setOnClickListeners() {
        binding.firstLevel.setOnClickListener {
            val intent = Intent(this, GameActivity::class.java)
            intent.putExtra("level", 1)
            startActivity(intent)
        }

        binding.bonusLevel.setOnClickListener {
            val intent = Intent(this, GoogleMapActivity::class.java)
            startActivity(intent)
        }
    }
}