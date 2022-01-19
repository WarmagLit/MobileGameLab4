package com.tsu.mobilegamelab4.game.gameobjects.entity.player.guns

import android.graphics.Canvas
import com.tsu.mobilegamelab4.game.*
import com.tsu.mobilegamelab4.game.Vector
import com.tsu.mobilegamelab4.game.gameobjects.GameObject
import com.tsu.mobilegamelab4.game.gameobjects.entity.Entity
import com.tsu.mobilegamelab4.game.graphics.Sprite
import com.tsu.mobilegamelab4.game.gameobjects.entity.player.Player

abstract class Gun(
    protected val owner: Entity,
    protected val sprite: Sprite,
    protected val gameObjects: MutableList<GameObject>
) : GameObject(owner.displayCoordinates) {

    protected val bullets: MutableList<Bullet> = mutableListOf()

    abstract fun fire(velocity: Vector)
}