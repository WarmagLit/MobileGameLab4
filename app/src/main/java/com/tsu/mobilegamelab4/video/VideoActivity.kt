package com.tsu.mobilegamelab4.video

import android.net.Uri
import android.os.Bundle
import android.widget.MediaController
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.tsu.mobilegamelab4.R
import com.tsu.mobilegamelab4.databinding.ActivityVideoBinding

class VideoActivity : AppCompatActivity() {

    private val viewModel by viewModels<VideoViewModel>()
    private lateinit var binding: ActivityVideoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVideoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setObserver()
        configureVideoView()
    }

    private fun configureVideoView() {
        val mediaController = MediaController(this)
        mediaController.setAnchorView(binding.videoView)

        binding.videoView.setMediaController(mediaController)
        binding.videoView.requestFocus()
        binding.videoView.start()
        binding.videoView.setOnCompletionListener {
            finish()
        }
    }

    private fun setObserver() {
        viewModel.videoUri.observe(this) {
            binding.videoView.setVideoURI(it)
        }
    }
}