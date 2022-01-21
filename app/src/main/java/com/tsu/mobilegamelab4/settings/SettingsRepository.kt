package com.tsu.mobilegamelab4.settings

import android.app.Application
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.tsu.mobilegamelab4.SharedPreference
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class SettingsRepository(app: Application) {
    private val sharedPreferences = SharedPreference(app)

    val isJoystick: Flow<Boolean> = flow {
        while (true) {
            val latestInfo = sharedPreferences.getValueBoolean(SharedPreference.CONTROLS_KEY, true)
            emit(latestInfo)
            delay(1000)
        }
    }

    val showPerformance: Flow<Boolean> = flow {
        while (true) {
            val latestInfo =
                sharedPreferences.getValueBoolean(SharedPreference.PERFORMANCE_KEY, false)
            emit(latestInfo)
            delay(1000)
        }
    }

    fun setControls(isJoystick: Boolean) {
        sharedPreferences.save(SharedPreference.CONTROLS_KEY, isJoystick)
    }

    fun setPerformance(showPerformance: Boolean) {
        sharedPreferences.save(SharedPreference.PERFORMANCE_KEY, showPerformance)
    }

    fun signOut() {
        Firebase.auth.signOut()
    }
}