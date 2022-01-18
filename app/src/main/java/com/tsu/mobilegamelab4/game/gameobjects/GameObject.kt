package com.tsu.mobilegamelab4.game.gameobjects

import com.tsu.mobilegamelab4.game.Hitbox
import com.tsu.mobilegamelab4.game.Point
import com.tsu.mobilegamelab4.game.Vector
import com.tsu.mobilegamelab4.game.interfaces.IDrawableUpdatable

abstract class GameObject(var pos: Point): IDrawableUpdatable {

    protected var velocity: Vector = Vector(0.0, 0.0)
    var direction = Vector(1.0, 0.0)
    lateinit var hitbox: Hitbox
    var toDestroy: Boolean = false

}