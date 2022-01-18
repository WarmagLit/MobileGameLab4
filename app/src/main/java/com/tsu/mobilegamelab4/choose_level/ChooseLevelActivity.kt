package com.tsu.mobilegamelab4.choose_level

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.tsu.mobilegamelab4.databinding.ActivityChooseLevelBinding
import com.tsu.mobilegamelab4.game.GameActivity

class ChooseLevelActivity : AppCompatActivity() {

    private lateinit var binding: ActivityChooseLevelBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChooseLevelBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.firstLevel.setOnClickListener {
            val intent = Intent(this, GameActivity::class.java)
            intent.putExtra("level", 1)
            startActivity(intent)
        }
    }
}