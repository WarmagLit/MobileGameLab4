package com.tsu.mobilegamelab4.game.game

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import com.tsu.mobilegamelab4.R
import com.tsu.mobilegamelab4.game.controls.Joystick
import com.tsu.mobilegamelab4.game.Point
import com.tsu.mobilegamelab4.game.Utils.getDistanceBetweenPoints
import kotlin.math.roundToInt

class Circle(
    context: Context,
    color: Int,
    pos: Point,
    mainJoystick: Joystick
) :
    GameObject(pos) {
    private val textPaint = Paint()
    private val skin: Bitmap
    private var joystick: Joystick = mainJoystick
    private val SPEED_PIXELS_PER_SECOND = 400.0
    private val MAX_SPEED = SPEED_PIXELS_PER_SECOND / GameLoop.MAX_UPS

    init {
        // Get drawable from resource
        val drawable: Drawable? = ContextCompat.getDrawable(context, R.drawable.hero)

        skin = drawable!!.toBitmap()
        textPaint.color = color
        textPaint.textSize = 50f
    }

    override fun draw(canvas: Canvas, centeredGameDisplay: CenteredGameDisplay?) {
        if (centeredGameDisplay != null) {
            /*
            canvas.drawCircle(
                centeredGameDisplay.gameToDisplayCoordinatesX(pos.X).toFloat(),
                centeredGameDisplay.gameToDisplayCoordinatesY(pos.Y).toFloat(),
                radius.toFloat(),
                paint
            )*/
            canvas.drawText("Pos: X:${pos.X.roundToInt()} Y:${pos.Y.roundToInt()}", 100f, 300f, textPaint)
            canvas.drawBitmap(
                skin,
                centeredGameDisplay.gameToDisplayCoordinatesX(pos.X).toFloat(),
                centeredGameDisplay.gameToDisplayCoordinatesY(pos.Y).toFloat(),
                null
            )
        }
    }

    override fun update() {
        // Update velocity based on actuator of joystick
        velocity.X = joystick.actuator.X * MAX_SPEED
        velocity.Y = joystick.actuator.Y * MAX_SPEED

        // Update position
        pos.X += velocity.X
        pos.Y += velocity.Y

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