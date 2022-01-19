package com.tsu.mobilegamelab4.game.gameobjects.entity.player.guns

import android.graphics.*
import com.tsu.mobilegamelab4.game.*
import com.tsu.mobilegamelab4.game.Point
import com.tsu.mobilegamelab4.game.gameobjects.GameObject

abstract class Bullet(position: Point): GameObject(position) {

    protected lateinit var mPaint: Paint

    protected val DESTROY_TIME = 50
    protected var destroyCounter = 0

    var displayCoordinates: Point = Point(0.0, 0.0)
}