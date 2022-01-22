package com.tsu.mobilegamelab4.game.surfaceview

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.tsu.mobilegamelab4.database.User
import com.tsu.mobilegamelab4.game.surfaceview.items.Keys
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class GameViewModel(app: Application) : AndroidViewModel(app) {

    private val repository = GameRepository(app)
    private var currentUser: User? = null

    private val _isJoystick = MutableLiveData(repository.getControls())
    val isJoystick: LiveData<Boolean>
        get() = _isJoystick

    private val _showPerformance = MutableLiveData(repository.getPerformance())
    val showPerformance: LiveData<Boolean>
        get() = _showPerformance

    private val _levelsCompleted = MutableLiveData<Int>()
    val levelsCompleted: LiveData<Int>
        get() = _levelsCompleted

    init {
        viewModelScope.launch {
            repository.currentUserInfo.collect {
                currentUser = it
                _levelsCompleted.value = it.levelsCompleted
            }
        }
    }

    fun updateCompletedLevels(levelsCompleted: Int) {
        currentUser?.let {
            it.levelsCompleted = levelsCompleted
            repository.updateUser(it)
        }
    }

    fun sendKeys(keys: List<Keys>) {
        currentUser?.let {
            keys.forEach { key ->
                when (key) {
                    Keys.BLUE -> it.blueKeys++
                    Keys.GREEN -> it.greenKeys++
                    Keys.RED -> it.redKeys++
                    Keys.YELLOW -> it.yellowKeys++
                    else -> {}
                }
            }
            repository.updateUser(it)
        }
    }
}