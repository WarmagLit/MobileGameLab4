package com.tsu.mobilegamelab4.video

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tsu.mobilegamelab4.R

class VideoViewModel : ViewModel() {

    private val _videoUri =
        MutableLiveData(Uri.parse("android.resource://com.tsu.mobilegamelab4/${R.raw.testvideo}"))
    val videoUri: LiveData<Uri>
        get() = _videoUri
}