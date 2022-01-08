package com.tsu.mobilegamelab4.game

import android.graphics.Canvas
import android.graphics.Paint
import kotlin.math.roundToInt

class Circle(
    color: Int,
    pos: Point,
    mainJoystick: Joystick,
    var radius: Double
) :
    GameObject(pos) {
    var paint: Paint
    private val textPaint = Paint()
    private var joystick: Joystick = mainJoystick
    private val SPEED_PIXELS_PER_SECOND = 400.0
    private val MAX_SPEED = SPEED_PIXELS_PER_SECOND / GameLoop.MAX_UPS

    init {
        // Set colors of circle
        paint = Paint()
        paint.color = color

        textPaint.color = color
        textPaint.textSize = 50f
    }

    override fun draw(canvas: Canvas?, gameDisplay: GameDisplay?) {
        if (canvas != null) {
            if (gameDisplay != null) {
                canvas.drawCircle(
                    gameDisplay.gameToDisplayCoordinatesX(pos.X).toFloat(),
                    gameDisplay.gameToDisplayCoordinatesY(pos.Y).toFloat(),
                    radius.toFloat(),
                    paint
                )
                canvas.drawText("Pos: X:${pos.X.roundToInt()} Y:${pos.Y.roundToInt()}", 100f, 300f, textPaint)
            }
        }
    }

    override fun update() {
        // Update velocity based on actuator of joystick
        velocityX = joystick.actuatorX * MAX_SPEED
        velocityY = joystick.actuatorY * MAX_SPEED

        // Update position
        pos.X += velocityX
        pos.Y += velocityY

        // Update direction
        if (velocityX != 0.0 || velocityY != 0.0) {
            // Normalize velocity to get direction (unit vector of velocity)
            val distance: Double = Utils.getDistanceBetweenPoints(Point(0.0, 0.0), Point(velocityX, velocityY))
            directionX = velocityX / distance
            directionY = velocityY / distance
        }
    }
}