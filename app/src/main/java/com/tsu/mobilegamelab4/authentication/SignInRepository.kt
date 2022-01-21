package com.tsu.mobilegamelab4.authentication

import android.util.Log
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.tsu.mobilegamelab4.database.User

class SignInRepository {

    private val database = Firebase.database
    private val myRef = database.getReference("users")
    private val auth = Firebase.auth

    fun createUserWithEmailAndPassword(
        email: String,
        password: String,
        onSuccess: () -> Unit,
        onFailure: () -> Unit
    ) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d("TAG", "signInWithCustomToken:success")
                    // Sign in success, update UI with the signed-in user's information
                    onSuccess.invoke()
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("TAG", "signInWithCustomToken:failure", task.exception)
                    onFailure.invoke()
                    //updateUI(null)
                }
            }
    }

    fun signInWithEmailAndPassword(
        email: String,
        password: String,
        onSuccess: () -> Unit,
        onFailure: () -> Unit
    ) {
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                // Sign in success, update UI with the signed-in user's information
                Log.d("TAG", "signInWithCustomToken:success")
                onSuccess.invoke()
            } else {
                // If sign in fails, display a message to the user.
                Log.w("TAG", "signInWithCustomToken:failure", task.exception)
                onFailure.invoke()
            }
        }
    }

    fun getCurrentUserId(): String = auth.currentUser?.uid.toString()

    fun getCurrentUser() = auth.currentUser

    fun setValueToUser(userId: String, user: User) {
        myRef.child(userId).setValue(user)
    }
}