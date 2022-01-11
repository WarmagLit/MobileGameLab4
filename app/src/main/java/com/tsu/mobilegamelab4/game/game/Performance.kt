package com.tsu.mobilegamelab4.game.game

import android.graphics.Canvas
import android.graphics.Paint
import com.tsu.mobilegamelab4.game.Utils

class Performance(private val gameLoop: GameLoop) {

    var averageUPS: Double = 0.0
    var averageFPS: Double = 0.0

    fun draw(canvas: Canvas) {
        drawUPS(canvas)
        drawFPS(canvas)
    }

    fun updateUPSFPS(ups: Double, fps: Double) {
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
