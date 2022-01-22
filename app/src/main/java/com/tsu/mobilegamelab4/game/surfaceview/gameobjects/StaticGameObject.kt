package com.tsu.mobilegamelab4.game.surfaceview.gameobjects

import com.tsu.mobilegamelab4.game.surfaceview.Point
import com.tsu.mobilegamelab4.game.surfaceview.graphics.FirstLocationSpriteSheet
import com.tsu.mobilegamelab4.game.surfaceview.graphics.Sprite

abstract class StaticGameObject(protected val spriteSheet: FirstLocationSpriteSheet, pos: Point) :
    GameObject(pos) {
    abstract var sprite: Sprite
    protected var displayCoordinates = Point(0.0, 0.0)

    override fun update() {
        hitbox.updateCoordinates(displayCoordinates)
    }
}