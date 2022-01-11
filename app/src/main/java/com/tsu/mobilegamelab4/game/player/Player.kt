package com.tsu.mobilegamelab4.game.player

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import com.tsu.mobilegamelab4.game.*
import com.tsu.mobilegamelab4.game.Utils.getDistanceBetweenPoints
import com.tsu.mobilegamelab4.game.graphics.Sprite
import kotlin.math.roundToInt

class Player(pos: Point, val animator: Animator) : GameObject(pos) {

    lateinit var sprite: List<Sprite>
    private var spriteUpdateCount = 0
    private var spriteIndex = 0
    private val SPEED_PIXELS_PER_SECOND = 400.0
    private val MAX_SPEED = SPEED_PIXELS_PER_SECOND / GameLoop.MAX_UPS

    private var actX = 0.0
    private var actY = 0.0

    private val textPaint = Paint()

    init {
        textPaint.color = Color.CYAN
        textPaint.textSize = 50f
    }

    companion object {
        private const val CHANGE_SPRITE_UPDATES = 10
        private const val SPRITE_AMOUNT = 32

    }

    override fun draw(canvas: Canvas) {
        //sprite[spriteIndex].draw(canvas, pos.X.toInt(), pos.Y.toInt())
        animator.draw(canvas, pos.X.toInt(), pos.Y.toInt())
        //canvas.drawText("Act: X:${actX} Y:${actY}", 100f, 300f, textPaint)
    }

    fun changeVelocity(actuator: Vector) {
        // Update velocity based on actuator of joystick
        velocity.X = actuator.X * MAX_SPEED
        velocity.Y = actuator.Y * MAX_SPEED

        actX = actuator.X
        actY = actuator.Y
    }

    override fun update() {
        // Update position
        pos.X += velocity.X
        pos.Y += velocity.Y

        // Animator update
        animator.changeDirection(velocity)
        animator.update()

        // Update direction
        if (velocity.X != 0.0 || velocity.Y != 0.0) {
            // Normalize velocity to get direction (unit vector of velocity)
            val distance: Double =
                getDistanceBetweenPoints(Point(0.0, 0.0), Point(velocity.X, velocity.Y))
            direction.X = velocity.X / distance
            direction.Y = velocity.Y / distance
        }
    }
}