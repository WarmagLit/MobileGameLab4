package com.tsu.mobilegamelab4.game

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import androidx.core.content.ContextCompat
import com.tsu.mobilegamelab4.R
import kotlin.math.roundToInt

class Performance(private val context: Context, private val gameLoop: GameLoop) {
    fun draw(canvas: Canvas) {
        drawUPS(canvas)
        drawFPS(canvas)
    }

    private fun drawUPS(canvas: Canvas) {
        val averageUPS = gameLoop.averageUPS.roundToInt().toString()
        val paint = Paint()
        val color: Int = ContextCompat.getColor(context, R.color.purple_200)
        paint.color = color
        paint.textSize = 50f
        canvas.drawText("UPS: $averageUPS", 100f, 100f, paint)
    }

    private fun drawFPS(canvas: Canvas) {
        val averageFPS = gameLoop.averageFPS.roundToInt().toString()
        val paint = Paint()
        val color: Int = ContextCompat.getColor(context, R.color.purple_200)
        paint.color = color
        paint.textSize = 50f
        canvas.drawText("FPS: $averageFPS", 100f, 200f, paint)
    }
}
