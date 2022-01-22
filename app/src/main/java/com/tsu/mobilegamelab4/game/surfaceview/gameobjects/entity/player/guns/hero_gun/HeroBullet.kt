package com.tsu.mobilegamelab4.game.surfaceview.gameobjects.entity.player.guns.hero_gun

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import com.tsu.mobilegamelab4.game.surfaceview.GameDisplay
import com.tsu.mobilegamelab4.game.surfaceview.Point
import com.tsu.mobilegamelab4.game.surfaceview.Utils
import com.tsu.mobilegamelab4.game.surfaceview.Vector
import com.tsu.mobilegamelab4.game.surfaceview.gameobjects.entity.player.guns.Bullet

class HeroBullet (position: Point, velocity: Vector): Bullet(position) {

    init {
        this.velocity = velocity
        mPaint = Paint()
        mPaint.color = Color.BLUE
        mPaint.strokeWidth = 20f
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