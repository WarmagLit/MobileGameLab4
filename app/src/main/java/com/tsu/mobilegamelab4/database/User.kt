package com.tsu.mobilegamelab4.database

import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class User (
    val uid: String? = null,
    var score: Int = 0,
    var redKeys: Int = 0,
    var greenKeys: Int = 0,
    var blueKeys: Int = 0,
    var yellowKeys: Int = 0,
    var levelsCompleted: Int = 0
){

    @Exclude
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "uid" to uid,
            "score" to score,
            "redKeys" to redKeys,
            "greenKeys" to greenKeys,
            "blueKeys" to blueKeys,
            "yellowKeys" to yellowKeys,
            "levelsCompleted" to levelsCompleted
        )
    }
}