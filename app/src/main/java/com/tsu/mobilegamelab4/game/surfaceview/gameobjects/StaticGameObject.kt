package com.tsu.mobilegamelab4.game.surfaceview.gameobjects

import com.tsu.mobilegamelab4.game.surfaceview.Point
import com.tsu.mobilegamelab4.game.surfaceview.graphics.Sprite
import com.tsu.mobilegamelab4.game.surfaceview.graphics.SpriteSheet

abstract class StaticGameObject(protected val spriteSheet: SpriteSheet, pos: Point) :
    GameObject(pos) {
    abstract var sprite: Sprite
    protected var displayCoordinates = Point(0.0, 0.0)

    override fun update() {
        hitbox.updateCoordinates(displayCoordinates)
    }
}