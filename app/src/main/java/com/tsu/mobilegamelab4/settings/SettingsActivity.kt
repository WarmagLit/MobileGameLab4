package com.tsu.mobilegamelab4.settings

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.tsu.mobilegamelab4.R
import com.tsu.mobilegamelab4.SharedPreference
import com.tsu.mobilegamelab4.authentication.SignInActivity
import com.tsu.mobilegamelab4.databinding.ActivitySettingsBinding

class SettingsActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingsBinding

    private var isJoystick = true

    private var showPerformance = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val sharedPreferences = SharedPreference(this)

        isJoystick = sharedPreferences.getValueBoolean("control", true)
        showPerformance = sharedPreferences.getValueBoolean("performance", false)

        checkControls()

        binding.settingsGyroscopeButton.setOnClickListener {
            sharedPreferences.save("control", false)
            isJoystick = false
            checkControls()
        }

        binding.settingsJoystickButton.setOnClickListener {
            sharedPreferences.save("control", true)
            isJoystick = true
            checkControls()
        }

        binding.settingsPerformanceSwitch.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                sharedPreferences.save("performance", true)
            } else {
                sharedPreferences.save("performance", false)
            }
        }

        binding.settingsBackButton.setOnClickListener {
            finish()
        }

        binding.settingsSignOut.setOnClickListener {
            Firebase.auth.signOut()
            val intent = Intent(this, SignInActivity::class.java)
            startActivity(intent)
        }

    }

    private fun checkControls() {
        if (isJoystick) {
            binding.settingsJoystickButton.setBackgroundResource(R.drawable.bg_menu_button_selected)
            binding.settingsGyroscopeButton.setBackgroundResource(R.drawable.bg_menu_button)
        } else {
            binding.settingsGyroscopeButton.setBackgroundResource(R.drawable.bg_menu_button_selected)
            binding.settingsJoystickButton.setBackgroundResource(R.drawable.bg_menu_button)
        }

        if (showPerformance) {
            binding.settingsPerformanceSwitch.isChecked = true
        }
    }
}