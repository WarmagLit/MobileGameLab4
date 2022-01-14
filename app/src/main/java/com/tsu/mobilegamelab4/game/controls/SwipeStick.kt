package com.tsu.mobilegamelab4.game.controls

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import com.tsu.mobilegamelab4.game.Point
import com.tsu.mobilegamelab4.game.Utils
import com.tsu.mobilegamelab4.game.Vector
import com.tsu.mobilegamelab4.game.interfaces.IDrawableUpdatable
import com.tsu.mobilegamelab4.game.player.Player
import kotlin.math.pow
import kotlin.math.sqrt

class SwipeStick (
    private val player: Player,
    private val outerCircleCenterPosition: Point,
    private val outerCircleRadius: Int,
    private val innerCircleRadius: Int
) : IDrawableUpdatable {

    var pointerId = 0
    private var innerCircleCenterPosition: Point =
        Point(outerCircleCenterPosition.X, outerCircleCenterPosition.Y)
    private val innerCirclePaint: Paint = Paint()
    private val outerCirclePaint: Paint = Paint()
    var isPressed = false
    private var joystickCenterToTouchDistance = 0.0
    var actuator = Vector(0.0, 0.0)

    override fun draw(canvas: Canvas) {
        // Draw outer circle
        canvas.drawCircle(
            outerCircleCenterPosition.X.toFloat(),
            outerCircleCenterPosition.Y.toFloat(),
            outerCircleRadius.toFloat(),
            outerCirclePaint
        )
        // Draw inner circle
        canvas.drawCircle(
            innerCircleCenterPosition.X.toFloat(),
            innerCircleCenterPosition.Y.toFloat(),
            innerCircleRadius.toFloat(),
            innerCirclePaint
        )
    }

    override fun update() {
        updateInnerCirclePosition()
    }

    fun action() {
        val direction = Vector(1.0, 0.0)
        // Update direction
        if (actuator.X != 0.0 || actuator.Y != 0.0) {
            // Normalize velocity to get direction (unit vector of velocity)
            val distance: Double =
                Utils.getDistanceBetweenPoints(Point(0.0, 0.0), Point(actuator.X, actuator.Y))
            direction.X = actuator.X / distance
            direction.Y = actuator.Y / distance
        }
        player.attack(direction)
    }

    private fun updateInnerCirclePosition() {
        innerCircleCenterPosition.X =
            (outerCircleCenterPosition.X + actuator.X * outerCircleRadius)
        innerCircleCenterPosition.Y =
            (outerCircleCenterPosition.Y + actuator.Y * outerCircleRadius)
    }

    fun setActuator(touchPosition: Point) {
        val delta = Point(
            touchPosition.X - outerCircleCenterPosition.X,
            touchPosition.Y - outerCircleCenterPosition.Y
        )
        val deltaDistance = sqrt(delta.X.pow(2.0) + delta.Y.pow(2.0))
        if (deltaDistance < outerCircleRadius) {
            actuator.X = delta.X / outerCircleRadius
            actuator.Y = delta.Y / outerCircleRadius
        } else {
            actuator.X = delta.X / deltaDistance
            actuator.Y = delta.Y / deltaDistance
        }
    }

    fun isPressed(touchPosition: Point): Boolean {
        joystickCenterToTouchDistance = sqrt(
            (outerCircleCenterPosition.X - touchPosition.X).pow(2.0) +
                    (outerCircleCenterPosition.Y - touchPosition.Y).pow(2.0)
        )
        return joystickCenterToTouchDistance < outerCircleRadius
    }

    fun resetActuator() {
        actuator.X = 0.0
        actuator.Y = 0.0
    }

    init {
        // Paint of circles
        outerCirclePaint.color = Color.parseColor("#8fbebeb6")
        outerCirclePaint.style = Paint.Style.FILL_AND_STROKE

        innerCirclePaint.color = Color.parseColor("#e20cc2")
        innerCirclePaint.style = Paint.Style.FILL_AND_STROKE
    }
}
