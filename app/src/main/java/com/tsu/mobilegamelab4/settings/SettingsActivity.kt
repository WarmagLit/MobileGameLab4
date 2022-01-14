package com.tsu.mobilegamelab4.settings

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.tsu.mobilegamelab4.R
import com.tsu.mobilegamelab4.SharedPreference
import com.tsu.mobilegamelab4.databinding.ActivitySettingsBinding

class SettingsActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingsBinding

    private var isJoystick = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val sharedPreferences = SharedPreference(this)

        isJoystick = sharedPreferences.getValueBoolean("control", true)

        checkControls()

        binding.settingsGyposcopeButton.setOnClickListener {
            sharedPreferences.save("control", false)
            isJoystick = false
            checkControls()
        }

        binding.settingsJoystickButton.setOnClickListener {
            sharedPreferences.save("control", true)
            isJoystick = true
            checkControls()
        }

        binding.settingsBackButton.setOnClickListener {
            finish()
        }
    }

    private fun checkControls() {
        if (isJoystick) {
            binding.settingsJoystickButton.setBackgroundResource(R.drawable.bg_menu_button_selected)
            binding.settingsGyposcopeButton.setBackgroundResource(R.drawable.bg_menu_button)
        } else {
            binding.settingsGyposcopeButton.setBackgroundResource(R.drawable.bg_menu_button_selected)
            binding.settingsJoystickButton.setBackgroundResource(R.drawable.bg_menu_button)
        }
    }
}