package com.tsu.mobilegamelab4.settings

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class SettingsViewModel(app: Application) : AndroidViewModel(app) {

    private val repository = SettingsRepository(app)

    private val _isJoystick = MutableLiveData<Boolean>()
    val isJoystick: LiveData<Boolean>
        get() = _isJoystick

    private val _showPerformance = MutableLiveData<Boolean>()
    val showPerformance: LiveData<Boolean>
        get() = _showPerformance

    init {
        trackSettingsParameters()
    }

    private fun trackSettingsParameters() {
        viewModelScope.launch {
            repository.isJoystick.collect {
                _isJoystick.value = it
            }
        }

        viewModelScope.launch {
            repository.showPerformance.collect {
                _showPerformance.value = it
            }
        }
    }

    fun setControls(isJoystick: Boolean) {
        repository.setControls(isJoystick)
    }

    fun setPerformance(showPerformance: Boolean) {
        repository.setPerformance(showPerformance)
    }

    fun signOut() {
        repository.signOut()
    }
}