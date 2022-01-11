package com.tsu.mobilegamelab4.game

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.tsu.mobilegamelab4.databinding.ActivityGameBinding
import com.tsu.mobilegamelab4.game.Game

class GameActivity : AppCompatActivity() {

    private lateinit var binding: ActivityGameBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameBinding.inflate(layoutInflater)
        //setContentView(binding.root)
        setContentView(Game(this))
    }
}