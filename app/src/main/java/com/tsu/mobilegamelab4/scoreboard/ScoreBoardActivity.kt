package com.tsu.mobilegamelab4.scoreboard

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.tsu.mobilegamelab4.databinding.ActivityScoreBoardBinding

class ScoreBoardActivity : AppCompatActivity() {

    private lateinit var binding: ActivityScoreBoardBinding
    private val viewModel by viewModels<ScoreBoardViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScoreBoardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setObservers()
        viewModel.loadTopFive()
    }

    private fun setObservers() {
        viewModel.scoreBoardFirst.observe(this) {
            binding.scoreBoardFirst.text = it
        }

        viewModel.scoreBoardSecond.observe(this) {
            binding.scoreBoardSecond.text = it
        }

        viewModel.scoreBoardThird.observe(this) {
            binding.scoreBoardThird.text = it
        }

        viewModel.scoreBoardFourth.observe(this) {
            binding.scoreBoardFourth.text = it
        }

        viewModel.scoreBoardFifth.observe(this) {
            binding.scoreBoardFifth.text = it
        }

        viewModel.scoreBoardUser.observe(this) {
            binding.scoreBoardUser.text = it
        }
    }
}