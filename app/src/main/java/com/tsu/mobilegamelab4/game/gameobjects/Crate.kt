package com.tsu.mobilegamelab4.game.gameobjects

import android.graphics.Canvas
import android.graphics.Rect
import com.tsu.mobilegamelab4.game.GameDisplay
import com.tsu.mobilegamelab4.game.Hitbox
import com.tsu.mobilegamelab4.game.Point
import com.tsu.mobilegamelab4.game.graphics.FirstLocationSpriteSheet
import com.tsu.mobilegamelab4.game.map.firstlocation.FirstLocationMap

class Crate(spriteSheet: FirstLocationSpriteSheet, pos: Point) : GameObject(pos) {

    val sprite = spriteSheet.getSpriteByIndex(Rect(0, 8, 1, 10)).also {
        it.size = android.graphics.Point(
            it.size.x,
            it.size.y * 2
        )
    }
    private var displayCoordinates = Point(0.0, 0.0)

    override fun draw(canvas: Canvas, display: GameDisplay?) {
        // sprite.draw(canvas, pos.X.toInt(), (pos.Y - sprite.size.y).toInt())
        display?.let {
            displayCoordinates = it.gameToDisplayCoordinates(pos)
            sprite.draw(
                canvas,
                displayCoordinates.X.toInt(),
                (displayCoordinates.Y - sprite.size.y/2).toInt()
            )
            //hitbox.draw(canvas, display)
        }
    }

    override fun update() {
        hitbox.updateCoordinates(displayCoordinates)
    }

    init {
        hitbox = Hitbox(
            this,
            FirstLocationMap.CELL_WIDTH_PIXELS,
            FirstLocationMap.CELL_HEIGHT_PIXELS
        )
    }
}