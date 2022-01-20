package com.tsu.mobilegamelab4.authentication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
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

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize Firebase Auth
        auth = Firebase.auth

        val database = Firebase.database
        val myRef = database.getReference("users")

        binding.btnSignUp.setOnClickListener {
            val email = binding.editTextEmail.text.toString()
            val password = binding.editTextTextPassword.text.toString()
            val nickname = binding.editTextTextNickname.text.toString()
            if (!isEmailValid(email)) {
                Toast.makeText(
                    baseContext, "Incorrect email",
                    Toast.LENGTH_SHORT
                ).show()
            } else if (!isPassValid(password)) {
                Toast.makeText(
                    baseContext, "Password should be at least 6 characters.",
                    Toast.LENGTH_SHORT
                ).show()
            } else if (!isNicknameValid(nickname)) {
                Toast.makeText(
                    baseContext, "Nickname should be at least 4 characters.",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("TAG", "signInWithCustomToken:success")
                            val userUid = auth.currentUser?.uid.toString()
                            val user = User(userUid, nickname, password, 0, 0, 0, 0, 0, 0)
                            myRef.child(userUid).setValue(user)
                            val intent = Intent(this, MenuActivity::class.java)
                            startActivity(intent)
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("TAG", "signInWithCustomToken:failure", task.exception)
                            Toast.makeText(
                                baseContext, "Authentication failed.",
                                Toast.LENGTH_SHORT
                            ).show()
                            //updateUI(null)
                        }
                    }
            }
        }


        binding.btnLogin.setOnClickListener {
            val email = binding.editTextEmail.text.toString()
            val password = binding.editTextTextPassword.text.toString()
            auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("TAG", "signInWithCustomToken:success")
                    val user = auth.currentUser
                    Toast.makeText(this, "Authorized", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, MenuActivity::class.java)
                    startActivity(intent)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("TAG", "signInWithCustomToken:failure", task.exception)
                    Toast.makeText(
                        baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }


    }

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        if (currentUser != null) {
            Toast.makeText(this, "Authorized", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, MenuActivity::class.java)
            startActivity(intent)
        } else {
            Toast.makeText(this, "User is not authorized", Toast.LENGTH_SHORT).show()
        }
    }

    private fun isEmailValid(email: String): Boolean {
        return Pattern.compile(
            "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]|[\\w-]{2,}))@"
                    + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                    + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                    + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                    + "[0-9]{1,2}|25[0-5]|2[0-4][0-9]))|"
                    + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$"
        ).matcher(email).matches()
    }

    private fun isPassValid(pass: String): Boolean {
        if (pass.length < 6) return false
        return true
    }

    private fun isNicknameValid(name: String): Boolean {
        if (name.length < 4 && name.length < 12) return false
        return true
    }
}