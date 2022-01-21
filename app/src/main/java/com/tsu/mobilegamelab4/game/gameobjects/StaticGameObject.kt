package com.tsu.mobilegamelab4.game.gameobjects

import com.tsu.mobilegamelab4.game.Point
import com.tsu.mobilegamelab4.game.graphics.FirstLocationSpriteSheet
import com.tsu.mobilegamelab4.game.graphics.Sprite

abstract class StaticGameObject(protected val spriteSheet: FirstLocationSpriteSheet, pos: Point) :
    GameObject(pos) {
    abstract var sprite: Sprite
    protected var displayCoordinates = Point(0.0, 0.0)

    override fun update() {
        hitbox.updateCoordinates(displayCoordinates)
    }
}