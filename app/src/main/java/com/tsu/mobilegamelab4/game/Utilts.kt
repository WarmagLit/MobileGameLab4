package com.tsu.mobilegamelab4.game

import android.graphics.Color
import kotlin.math.pow
import kotlin.math.sqrt


object Utils {

    var performanceColor: Int = 0

    init {
        performanceColor = Color.parseColor("#BB86FC")
    }

    fun getDistanceBetweenPoints(p1: Point, p2: Point): Double {
        return sqrt((p1.X - p2.X).pow(2.0) + (p1.Y - p2.Y).pow(2.0))
    }
}