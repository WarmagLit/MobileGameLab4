package com.tsu.mobilegamelab4.game.entity

import android.graphics.Paint
import com.tsu.mobilegamelab4.game.*
import com.tsu.mobilegamelab4.game.interfaces.ICollideable
import com.tsu.mobilegamelab4.game.map.firstlocation.FirstLocationMapLayout

abstract class Entity(
    pos: Point,
    private val mapLayout: FirstLocationMapLayout,
    private val gameObjects: MutableList<GameObject>
) :
    GameObject(pos),
    ICollideable {

    companion object {
        private const val SPEED_PIXELS_PER_SECOND = 400.0
        private const val MAX_SPEED = SPEED_PIXELS_PER_SECOND / GameLoop.MAX_UPS
    }

    private var act = Point(0.0, 0.0)
    protected var HP = 100

    private val textPaint = Paint()

    var displayCoordinates = Point(0.0, 0.0)

    abstract fun changeVelocity(actuator: Vector)

    abstract fun collideCheck(): Boolean

    fun die() {
        toDestroy = true
    }
}