package com.tsu.mobilegamelab4.settings

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.tsu.mobilegamelab4.R
import com.tsu.mobilegamelab4.authentication.SignInActivity
import com.tsu.mobilegamelab4.databinding.ActivitySettingsBinding

class SettingsActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingsBinding
    private val viewModel by viewModels<SettingsViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setObservers()
        setOnClickListeners()
    }

    private fun setObservers() {
        viewModel.isJoystick.observe(this) {
            if (it) {
                binding.settingsJoystickButton.setBackgroundResource(R.drawable.bg_menu_button_selected)
                binding.settingsGyroscopeButton.setBackgroundResource(R.drawable.bg_menu_button)
            } else {
                binding.settingsGyroscopeButton.setBackgroundResource(R.drawable.bg_menu_button_selected)
                binding.settingsJoystickButton.setBackgroundResource(R.drawable.bg_menu_button)
            }
        }

        viewModel.showPerformance.observe(this) {
            binding.settingsPerformanceSwitch.isChecked = it
        }
    }

    private fun setOnClickListeners() {

        binding.settingsGyroscopeButton.setOnClickListener {
            viewModel.setControls(false)
        }

        binding.settingsJoystickButton.setOnClickListener {
            viewModel.setControls(true)
        }

        binding.settingsPerformanceSwitch.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                viewModel.setPerformance(true)
            } else {
                viewModel.setPerformance(false)
            }
        }

        binding.settingsBackButton.setOnClickListener {
            finish()
        }

        binding.settingsSignOutButton.setOnClickListener {
            viewModel.signOut()
            val intent = Intent(this, SignInActivity::class.java)
            startActivity(intent)
        }
    }
}