package com.tsu.mobilegamelab4.authentication

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.tsu.mobilegamelab4.database.User
import com.tsu.mobilegamelab4.databinding.ActivitySignInBinding
import com.tsu.mobilegamelab4.menu.MenuActivity
import java.util.regex.Pattern

class SignInActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignInBinding
    private val viewModel by viewModels<SignInViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setOnClickListeners()
        setObservers()
    }

    private fun setObservers() {
        viewModel.intentToMenu.observe(this) {
            if (it) {
                val intent = Intent(this, MenuActivity::class.java)
                startActivity(intent)
            }
        }
    }

    private fun setOnClickListeners() {

        binding.signInSignUpButton.setOnClickListener {

            viewModel.signUp(
                email = binding.signInEmailEditText.text.toString(),
                password = binding.signInPasswordEditText.text.toString(),
                nickname = binding.signInNicknameEditText.text.toString()
            ) { resId ->
                Toast.makeText(this, resources.getString(resId), Toast.LENGTH_SHORT).show()
            }
        }


        binding.signInLoginButton.setOnClickListener {
            viewModel.login(
                email = binding.signInEmailEditText.text.toString(),
                password = binding.signInPasswordEditText.text.toString()
            ) { resId ->
                Toast.makeText(this, resources.getString(resId), Toast.LENGTH_SHORT).show()
            }
        }
    }

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        viewModel.checkUserAlreadyAuthorized { resId ->
            Toast.makeText(this, resources.getString(resId), Toast.LENGTH_SHORT).show()
        }
    }
}