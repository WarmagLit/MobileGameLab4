package com.tsu.mobilegamelab4.authentication

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tsu.mobilegamelab4.R
import com.tsu.mobilegamelab4.database.User
import java.util.regex.Pattern

class SignInViewModel : ViewModel() {

    private val repository = SignInRepository()

    private val _intentToMenu = MutableLiveData<Boolean>()
    val intentToMenu: LiveData<Boolean>
        get() = _intentToMenu

    fun checkUserAlreadyAuthorized(toast: (stringId: Int) -> Unit) {
        val currentUser = repository.getCurrentUser()
        if (currentUser != null) {
            toast.invoke(R.string.authorized)
            _intentToMenu.value = true
        } else {
            toast.invoke(R.string.user_is_not_authorized)
        }
    }

    fun signUp(email: String, password: String, nickname: String, toast: (stringId: Int) -> Unit) {
        if (!isSignUpValid(email, password, nickname, toast)) {
            return
        }

        repository.createUserWithEmailAndPassword(
            email = email,
            password = password,
            onSuccess = {
                val userUid = repository.getCurrentUserId()
                repository.setValueToUser(
                    userUid,
                    User(
                        userUid,
                        nickname,
                        password,
                        0,
                        0,
                        0,
                        0,
                        0,
                        0
                    )
                )
                _intentToMenu.value = true
            },
            onFailure = {
                toast.invoke(R.string.authentication_failed)
            }
        )
    }

    fun login(email: String, password: String, toast: (stringId: Int) -> Unit) {
        repository.signInWithEmailAndPassword(
            email = email,
            password = password,
            onSuccess = {
                toast.invoke(R.string.authorized)
                _intentToMenu.value = true
            },
            onFailure = {
                toast.invoke(R.string.authentication_failed)
            }
        )
    }

    private fun isSignUpValid(
        email: String,
        password: String,
        nickname: String,
        toast: (stringId: Int) -> Unit
    ): Boolean {

        if (!isEmailValid(email)) {
            toast.invoke(R.string.incorrect_email)
            return false
        } else if (!isPassValid(password)) {
            toast.invoke(R.string.small_password)
            return false
        } else if (!isNicknameValid(nickname)) {
            toast.invoke(R.string.small_nickname)
            return false
        }
        return true
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