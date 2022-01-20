package com.tsu.mobilegamelab4.game.gameobjects.entity

import android.graphics.Paint
import android.graphics.Rect
import com.tsu.mobilegamelab4.game.*
import com.tsu.mobilegamelab4.game.gameobjects.GameObject
import com.tsu.mobilegamelab4.game.interfaces.ICollideable
import com.tsu.mobilegamelab4.game.map.firstlocation.FirstLocationCollisionLayout

abstract class Entity(
    pos: Point,
    private val collisionLayout: FirstLocationCollisionLayout,
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

    fun collideCheck(): Rect? {
        for (obj in gameObjects) {
            if (obj != this && obj.hitbox.isCollide(hitbox))
                return obj.hitbox.rect
        }
        return null
    }

    fun die() {
        hitbox.rect = Rect(0,0,0,0)
        toDestroy = true
    }
}