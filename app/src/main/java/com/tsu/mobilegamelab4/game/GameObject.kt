package com.tsu.mobilegamelab4.game

import com.tsu.mobilegamelab4.game.interfaces.IDrawableUpdatable
import com.tsu.mobilegamelab4.game.entity.player.guns.Bullet

abstract class GameObject(var pos: Point): IDrawableUpdatable {

    protected var velocity: Vector = Vector(0.0, 0.0)
    var direction = Vector(1.0, 0.0)
    lateinit var hitbox: Hitbox
    var toDestroy: Boolean = false

}