package com.tsu.mobilegamelab4.game.gameobjects.entity.player

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Point
import com.tsu.mobilegamelab4.game.GameDisplay
import com.tsu.mobilegamelab4.game.gameobjects.entity.Entity
import com.tsu.mobilegamelab4.game.gameobjects.entity.HealthBar

class PlayerHealthBar(maxHealth: Int, val owner: Player): HealthBar(maxHealth, owner) {
    init {
        healthPaint.color = Color.RED
        healthPaint.strokeWidth = 65f

        healthLineLength = 500
        offset = Point(850, 470)
    }

    override fun draw(canvas: Canvas, display: GameDisplay?) {
        val x = owner.displayCoordinates.X.toFloat() - offset.x
        val y = owner.displayCoordinates.Y.toFloat() - offset.y
        owner.animator.drawHP(canvas, Point(x.toInt(),y.toInt()),HP, maxHealth, 10)
    }
}