package com.tsu.mobilegamelab4.Cases

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.tsu.mobilegamelab4.databinding.ActivityCasesBinding
import com.tsu.mobilegamelab4.databinding.ActivityMenuBinding

class CasesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCasesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCasesBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}