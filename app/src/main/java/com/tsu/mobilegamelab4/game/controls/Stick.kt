package com.tsu.mobilegamelab4.game.controls

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import com.tsu.mobilegamelab4.game.GameDisplay
import com.tsu.mobilegamelab4.game.Point
import com.tsu.mobilegamelab4.game.Vector
import com.tsu.mobilegamelab4.game.interfaces.IDrawableUpdatable
import com.tsu.mobilegamelab4.game.gameobjects.entity.player.Player
import kotlin.math.pow
import kotlin.math.sqrt

abstract class Stick(
    var player: Player,
    private val outerCircleCenterPosition: Point,
    private val outerCircleRadius: Int,
    private val innerCircleRadius: Int
) : IDrawableUpdatable {

    var pointerId = -1
    private var innerCircleCenterPosition: Point =
        Point(outerCircleCenterPosition.X, outerCircleCenterPosition.Y)
    protected val innerCirclePaint: Paint = Paint()
    private val outerCirclePaint: Paint = Paint()
    var isPressed = false
    private var stickCenterToTouchDistance = 0.0
    var actuator = Vector(0.0, 0.0)

    override fun draw(canvas: Canvas, display: GameDisplay?) {
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
        stickCenterToTouchDistance = sqrt(
            (outerCircleCenterPosition.X - touchPosition.X).pow(2.0) +
                    (outerCircleCenterPosition.Y - touchPosition.Y).pow(2.0)
        )
        return stickCenterToTouchDistance < outerCircleRadius
    }

    fun resetActuator() {
        actuator.X = 0.0
        actuator.Y = 0.0
    }

    init {
        // Paint of circles
        outerCirclePaint.color = Color.parseColor("#8fbebeb6")
        outerCirclePaint.style = Paint.Style.FILL_AND_STROKE

        innerCirclePaint.style = Paint.Style.FILL_AND_STROKE
    }
}