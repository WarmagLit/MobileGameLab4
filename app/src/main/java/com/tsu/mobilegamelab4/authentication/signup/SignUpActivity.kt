package com.tsu.mobilegamelab4.authentication.signup

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.tsu.mobilegamelab4.databinding.ActivitySignUpBinding

class SignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}