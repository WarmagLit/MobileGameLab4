package com.tsu.mobilegamelab4.game

import com.tsu.mobilegamelab4.game.Point
import com.tsu.mobilegamelab4.game.Vector

abstract class GameObject(var pos: Point): IDrawableUpdatable {

    protected var velocity: Vector = Vector(0.0, 0.0)
    var direction = Vector(1.0, 0.0)

}