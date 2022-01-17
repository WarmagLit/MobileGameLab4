package com.tsu.mobilegamelab4.game

import android.R.attr
import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.util.DisplayMetrics
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import com.tsu.mobilegamelab4.R
import java.security.AccessController.getContext
import kotlin.math.cos
import kotlin.math.pow
import kotlin.math.sin
import kotlin.math.sqrt
import android.R.attr.y

import android.R.attr.x
import android.R.attr.y

import android.R.attr.x








object Utils {

    var performanceColor: Int = 0
    var playerSkin: Bitmap? = null


    lateinit var displayCenter: Point

    init {
        performanceColor = Color.parseColor("#BB86FC")
    }

    fun setDisplayMetrics(context: Context) {
        val displayMetrics = DisplayMetrics()
        (context as Activity).windowManager.defaultDisplay.getMetrics(displayMetrics)
        displayCenter = Point(displayMetrics.widthPixels / 2.0, displayMetrics.heightPixels / 2.0)
    }

    fun setPlayerSkin(context: Context) {
        // Get drawable from resource
        val drawable: Drawable? = ContextCompat.getDrawable(context, R.drawable.hero)
        playerSkin = drawable!!.toBitmap()
    }

    fun getDistanceBetweenPoints(p1: Point, p2: Point): Double {
        return sqrt((p1.X - p2.X).pow(2.0) + (p1.Y - p2.Y).pow(2.0))
    }

    // Sinus in degrees
    fun sinus(angle: Double): Double {
        return sin(angle*Math.PI/180)
    }

    // Cosinus in degrees
    fun cosinus(angle: Double): Double {
        return cos(angle*Math.PI/180)
    }

    fun getDirectionalVector(p1: Point, p2: Point): Vector {
        return normalizeVector(Vector(p2.X - p1.X,p2.Y - p1.Y))
    }

    fun normalizeVector(vec: Vector): Vector {
        val len = vec.length()
        if (len > 0) {
            vec.X /= len.toInt()
            vec.Y /= len.toInt()
        }
        return vec
    }
}