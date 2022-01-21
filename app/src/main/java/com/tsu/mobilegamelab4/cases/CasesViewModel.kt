package com.tsu.mobilegamelab4.cases

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tsu.mobilegamelab4.database.User
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class CasesViewModel : ViewModel() {
    companion object {
        const val RED = "red"
        const val BLUE = "blue"
        const val GREEN = "green"
        const val YELLOW = "yellow"
        const val SOUND_OPEN_ID = 1
        const val SOUND_CHANGE_CASES_ID = 2
    }

    val cases = mutableListOf(
        RED,
        GREEN,
        BLUE,
        YELLOW
    )

    private val repository = CasesRepository()

    private var currentUser: User? = null

    private val _keys = MutableLiveData<List<String>>()
    val keys: LiveData<List<String>>
        get() = _keys

    private val _score = MutableLiveData<Int>()
    val score: LiveData<Int>
        get() = _score

    init {
        viewModelScope.launch {
            repository.currentUserInfo.collect {
                currentUser = it
                calculateKeys(it)
                _score.value = it.score
            }
        }
    }

    fun reduceKeyCount(key: String) {
        currentUser?.let {
            when (key) {
                RED -> it.redKeys--
                BLUE -> it.blueKeys--
                GREEN -> it.greenKeys--
                YELLOW -> it.yellowKeys--
                else -> {}
            }

            repository.updateUser(it)
        }
    }

    fun increaseScore(drop: Int) {
        currentUser?.let {
            it.score += drop
            repository.updateUser(it)
        }
    }

    private fun calculateKeys(currentUser: User) {
        val updatedKeys = mutableListOf<String>()
        for (i in 1..currentUser.redKeys) updatedKeys.add(RED)
        for (i in 1..currentUser.greenKeys) updatedKeys.add(GREEN)
        for (i in 1..currentUser.blueKeys) updatedKeys.add(BLUE)
        for (i in 1..currentUser.yellowKeys) updatedKeys.add(YELLOW)
        _keys.value = updatedKeys
    }
}