package com.tsu.mobilegamelab4.scoreboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tsu.mobilegamelab4.database.User
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class ScoreBoardViewModel : ViewModel() {

    private val repository = ScoreBoardRepository()

    private val _scoreBoardFirst = MutableLiveData<String>()
    val scoreBoardFirst: LiveData<String>
        get() = _scoreBoardFirst

    private val _scoreBoardSecond = MutableLiveData<String>()
    val scoreBoardSecond: LiveData<String>
        get() = _scoreBoardSecond

    private val _scoreBoardThird = MutableLiveData<String>()
    val scoreBoardThird: LiveData<String>
        get() = _scoreBoardThird

    private val _scoreBoardFourth = MutableLiveData<String>()
    val scoreBoardFourth: LiveData<String>
        get() = _scoreBoardFourth

    private val _scoreBoardFifth = MutableLiveData<String>()
    val scoreBoardFifth: LiveData<String>
        get() = _scoreBoardFifth

    private val _scoreBoardUser = MutableLiveData<String>()
    val scoreBoardUser: LiveData<String>
        get() = _scoreBoardUser

    fun loadTopFive() {
        viewModelScope.launch {
            repository.latestUserList.collect { list ->
                showTopFive(list)
            }
        }
    }

    private fun showTopFive(userList: List<User>) {

        // Read from the database
        val sortedList = userList.sortedByDescending {
            it.score
        }

        if (sortedList.isNotEmpty()) {
            _scoreBoardFirst.value = "1. ${sortedList[0].nickname} ${sortedList[0].score}"
        }
        if (sortedList.size > 1) {
            _scoreBoardSecond.value = "2. ${sortedList[1].nickname}  ${sortedList[1].score}"
        }
        if (sortedList.size > 2) {
            _scoreBoardThird.value = "3. ${sortedList[2].nickname}  ${sortedList[2].score}"
        }
        if (sortedList.size > 3) {
            _scoreBoardFourth.value = "4. ${sortedList[3].nickname}  ${sortedList[3].score}"
        }
        if (sortedList.size > 4) {
            _scoreBoardFirst.value = "5. ${sortedList[4].nickname}  ${sortedList[4].score}"
        }

        val userUid = repository.getCurrentUserId()
        val userIndex = sortedList.indexOfFirst { it.uid == userUid }
        if (userIndex > -1) {
            _scoreBoardUser.value =
                "${userIndex + 1}. ${sortedList[userIndex].nickname} ${sortedList[userIndex].score}"
        }
    }
}