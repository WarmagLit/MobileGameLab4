package com.tsu.mobilegamelab4.game.game

import android.graphics.Canvas
import com.tsu.mobilegamelab4.game.Point
import com.tsu.mobilegamelab4.game.Vector

abstract class GameObject(var pos: Point) {

    protected var velocity: Vector = Vector(0.0, 0.0)
    var direction = Vector(1.0, 0.0)

    abstract fun draw(canvas: Canvas, centeredGameDisplay: CenteredGameDisplay?)
    abstract fun update()
}
