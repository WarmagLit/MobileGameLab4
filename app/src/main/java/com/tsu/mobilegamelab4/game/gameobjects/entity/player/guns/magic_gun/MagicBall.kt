package com.tsu.mobilegamelab4.game.gameobjects.entity.player.guns.magic_gun

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.Log
import com.tsu.mobilegamelab4.game.*
import com.tsu.mobilegamelab4.game.gameobjects.entity.player.guns.Bullet

class MagicBall (position: Point, veloc: Vector): Bullet(position) {

    companion object {
        const val RADIUS = 20f
    }

    //displayCoordinates: Point = Point(0.0, 0.0)
    var logCounter = 0

    init {
        velocity.X = veloc.X
        velocity.Y = veloc.Y
        mPaint = Paint()
        mPaint.color = Color.BLUE
        mPaint.strokeWidth = 20f
        mPaint.textSize = 50f
    }

    override fun draw(canvas: Canvas, display: GameDisplay?) {
        //sprite.draw(canvas, pos.X.toInt(), pos.Y.toInt())

        display?.let {
            displayCoordinates = it.gameToDisplayCoordinates(pos)
            canvas.drawCircle(
                displayCoordinates.X.toFloat(),
                displayCoordinates.Y.toFloat(),
                RADIUS, mPaint)
            //canvas.drawText("!!:" + velocity.X + " " + velocity.Y, pos.X.toFloat(),
             //   pos.Y.toFloat(), mPaint)
        }

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