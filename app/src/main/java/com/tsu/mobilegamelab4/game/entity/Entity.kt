package com.tsu.mobilegamelab4.game.entity

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.Log
import com.tsu.mobilegamelab4.game.*
import com.tsu.mobilegamelab4.game.entity.player.HeroAnimator
import com.tsu.mobilegamelab4.game.entity.player.guns.Bullet
import com.tsu.mobilegamelab4.game.entity.player.guns.Gun
import com.tsu.mobilegamelab4.game.graphics.HeroSpriteSheet
import com.tsu.mobilegamelab4.game.interfaces.ICollideable
import com.tsu.mobilegamelab4.game.map.MapLayout
import com.tsu.mobilegamelab4.game.map.TileType

abstract class Entity(
    pos: Point,
    private val mapLayout: MapLayout,
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