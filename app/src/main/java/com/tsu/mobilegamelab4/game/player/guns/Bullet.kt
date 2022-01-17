package com.tsu.mobilegamelab4.game.player.guns

import android.graphics.*
import com.tsu.mobilegamelab4.game.*
import com.tsu.mobilegamelab4.game.Point
import com.tsu.mobilegamelab4.game.graphics.Sprite
import kotlin.math.PI
import kotlin.math.atan2

class Bullet(position: Point, veloc: Vector): GameObject(position) {


    private val mPaint: Paint

    private val DESTROY_TIME = 50
    private var destroyCounter = 0

    init {
        velocity = veloc
        mPaint = Paint()
        mPaint.color = Color.BLUE
        mPaint.strokeWidth = 20f
    }

    override fun hit(bullet: Bullet) {
        // Do nothing
    }

    override fun draw(canvas: Canvas, display: GameDisplay?) {
        //sprite.draw(canvas, pos.X.toInt(), pos.Y.toInt())
        canvas.drawLine(
            pos.X.toFloat(),
            pos.Y.toFloat(),
            pos.X.toFloat() + velocity.X.toFloat(), pos.Y.toFloat()+velocity.Y.toFloat(), mPaint)
    }


    override fun update() {
        // Update position
        pos.X += velocity.X
        pos.Y += velocity.Y

        // Update direction
        if (velocity.X != 0.0 || velocity.Y != 0.0) {
            // Normalize velocity to get direction (unit vector of velocity)
            val distance: Double =
                Utils.getDistanceBetweenPoints(Point(0.0, 0.0), Point(velocity.X, velocity.Y))
            direction.X = velocity.X / distance
            direction.Y = velocity.Y / distance
        }

        destroyCounter++
        if (destroyCounter >= DESTROY_TIME) {
            toDestroy = true
        }
    }


}