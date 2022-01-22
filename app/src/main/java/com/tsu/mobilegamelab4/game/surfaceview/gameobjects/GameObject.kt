package com.tsu.mobilegamelab4.game.surfaceview.gameobjects

import com.tsu.mobilegamelab4.game.surfaceview.Hitbox
import com.tsu.mobilegamelab4.game.surfaceview.Point
import com.tsu.mobilegamelab4.game.surfaceview.Vector
import com.tsu.mobilegamelab4.game.surfaceview.interfaces.IDrawableUpdatable

abstract class GameObject(var pos: Point): IDrawableUpdatable {

    protected var velocity: Vector = Vector(0.0, 0.0)
    var direction = Vector(1.0, 0.0)
    lateinit var hitbox: Hitbox
    var toDestroy: Boolean = false

}