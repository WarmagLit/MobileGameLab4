package com.tsu.mobilegamelab4.game.surfaceview.gameobjects.entity.player.guns

import android.graphics.*
import com.tsu.mobilegamelab4.game.surfaceview.Point
import com.tsu.mobilegamelab4.game.surfaceview.gameobjects.GameObject

abstract class Bullet(position: Point): GameObject(position) {

    protected lateinit var mPaint: Paint

    companion object {
        var DESTROY_TIME = 50
    }
    protected var destroyCounter = 0

    var displayCoordinates: Point = Point(0.0, 0.0)
}