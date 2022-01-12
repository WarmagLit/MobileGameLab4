package com.tsu.mobilegamelab4.game

import android.graphics.Canvas
import android.graphics.Paint
import com.tsu.mobilegamelab4.game.interfaces.IDrawable

class Performance: IDrawable {

    private var averageUPS: Double = 0.0
    private var averageFPS: Double = 0.0
    private val paint = Paint()

    init {
        val color: Int = Utils.performanceColor
        paint.color = color
        paint.textSize = 50f
    }

    override fun draw(canvas: Canvas) {
        drawUPS(canvas)
        drawFPS(canvas)
    }

    fun setPerformance(ups: Double, fps: Double) {
        averageUPS = ups
        averageFPS = fps
    }

    private fun drawUPS(canvas: Canvas) {
        canvas.drawText("UPS: ${averageUPS.toInt()}", 100f, 100f, paint)
    }

    private fun drawFPS(canvas: Canvas) {
        canvas.drawText("FPS: ${averageFPS.toInt()}", 100f, 200f, paint)
    }
}