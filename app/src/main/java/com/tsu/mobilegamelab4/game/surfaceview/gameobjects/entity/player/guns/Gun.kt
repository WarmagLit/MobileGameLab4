package com.tsu.mobilegamelab4.game.surfaceview.gameobjects.entity.player.guns

import com.tsu.mobilegamelab4.game.surfaceview.Vector
import com.tsu.mobilegamelab4.game.surfaceview.gameobjects.GameObject
import com.tsu.mobilegamelab4.game.surfaceview.gameobjects.entity.Entity
import com.tsu.mobilegamelab4.game.surfaceview.graphics.Sprite

abstract class Gun(
    protected val owner: Entity,
    protected val sprite: Sprite,
    protected val gameObjects: MutableList<GameObject>
) : GameObject(owner.displayCoordinates) {

    protected val bullets: MutableList<Bullet> = mutableListOf()
    protected var bulletSpeed = 20.0

    abstract fun fire(velocity: Vector)
}