package com.tsu.mobilegamelab4.game.gameobjects.entity

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Point
import com.tsu.mobilegamelab4.game.GameDisplay
import com.tsu.mobilegamelab4.game.interfaces.IDrawable


open class HealthBar(private val maxHealth: Int, private val owner: Entity): IDrawable {

    protected var HP = maxHealth
    val healthPaint = Paint()
    var offset = Point(70, 120)
    var healthLineLength = 150

    init {
        healthPaint.color = Color.RED
        healthPaint.strokeWidth = 15f
    }

    fun getDamage(damage: Int) {
        HP -= damage
        if (HP <= 0) {
            owner.die()
        }
    }

    override fun draw(canvas: Canvas, display: GameDisplay?) {
        val x = owner.displayCoordinates.X.toFloat() - offset.x
        val y = owner.displayCoordinates.Y.toFloat() - offset.y
        val lineLength = HP.toFloat() / maxHealth * healthLineLength
        canvas.drawLine(x, y, x + lineLength, y, healthPaint)
    }

}