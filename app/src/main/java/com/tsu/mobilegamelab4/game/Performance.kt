package com.tsu.mobilegamelab4.game

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import androidx.core.content.ContextCompat
import com.tsu.mobilegamelab4.R
import kotlin.math.roundToInt

class Performance(private val gameLoop: GameLoop) {

    var averageUPS: Double = 0.0
    var averageFPS: Double = 0.0

    fun draw(canvas: Canvas) {
        drawUPS(canvas)
        drawFPS(canvas)
    }

    fun update(ups: Double, fps: Double) {
        averageUPS = ups
        averageFPS = fps
    }

    private fun drawUPS(canvas: Canvas) {
        val paint = Paint()
        val color: Int = Utils.performanceColor
        paint.color = color
        paint.textSize = 50f
        canvas.drawText("UPS: ${averageUPS.toInt()}", 100f, 100f, paint)
    }

    private fun drawFPS(canvas: Canvas) {
        val paint = Paint()
        val color: Int = Utils.performanceColor
        paint.color = color
        paint.textSize = 50f
        canvas.drawText("FPS: ${averageFPS.toInt()}", 100f, 200f, paint)
    }
}
