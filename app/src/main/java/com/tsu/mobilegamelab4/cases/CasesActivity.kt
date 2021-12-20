package com.tsu.mobilegamelab4.cases

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.tsu.mobilegamelab4.databinding.ActivityCasesBinding

class CasesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCasesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCasesBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}