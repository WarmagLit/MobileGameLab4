package com.tsu.mobilegamelab4.authentication.signin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.tsu.mobilegamelab4.databinding.ActivitySignInBinding
import com.tsu.mobilegamelab4.menu.MenuActivity
import com.tsu.mobilegamelab4.settings.SettingsActivity

class SignInActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignInBinding

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize Firebase Auth
        auth = Firebase.auth


        binding.btnSignUp.setOnClickListener {
            val email = binding.editTextEmail.text.toString()
            val password = binding.editTextTextPassword.text.toString()
            auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("TAG", "signInWithCustomToken:success")
                    val user = auth.currentUser
                    //updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("TAG", "signInWithCustomToken:failure", task.exception)
                    Toast.makeText(baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
                    //updateUI(null)
                }
            }
        }

        //binding.btnLogin.


    }

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        if (currentUser != null) {
            Toast.makeText(this, "Authorized", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, MenuActivity::class.java)
            startActivity(intent)
        } else{
            Toast.makeText(this, "User null", Toast.LENGTH_LONG).show()
        }
        //updateUI(currentUser)
    }
}