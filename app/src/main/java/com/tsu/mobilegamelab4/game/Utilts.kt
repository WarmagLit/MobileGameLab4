package com.tsu.mobilegamelab4.game

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import com.tsu.mobilegamelab4.R
import kotlin.math.pow
import kotlin.math.sqrt


object Utils {

    var performanceColor: Int = 0
    var playerSkin: Bitmap? = null

    init {
        performanceColor = Color.parseColor("#BB86FC")
    }

    fun setPlayerSkin(context: Context) {
        // Get drawable from resource
        val drawable: Drawable? = ContextCompat.getDrawable(context, R.drawable.hero)
        playerSkin = drawable!!.toBitmap()
    }

    fun getDistanceBetweenPoints(p1: Point, p2: Point): Double {
        return sqrt((p1.X - p2.X).pow(2.0) + (p1.Y - p2.Y).pow(2.0))
    }
}