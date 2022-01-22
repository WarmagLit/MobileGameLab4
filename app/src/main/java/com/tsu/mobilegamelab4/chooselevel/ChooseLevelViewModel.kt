package com.tsu.mobilegamelab4.chooselevel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class ChooseLevelViewModel : ViewModel() {
    companion object {
        const val LEVEL_KEY = "level"
    }

    private val repository = ChooseLevelRepository()

    private val _levelsCompleted = MutableLiveData<Int>()
    val levelsCompleted: LiveData<Int>
        get() = _levelsCompleted

    init {
        viewModelScope.launch {
            repository.availableLevelsInfo.collect {
                _levelsCompleted.value = it
            }
        }
    }
}