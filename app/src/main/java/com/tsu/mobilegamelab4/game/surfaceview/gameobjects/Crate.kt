package com.tsu.mobilegamelab4.game.surfaceview.gameobjects

import android.graphics.Canvas
import android.graphics.Rect
import com.tsu.mobilegamelab4.game.surfaceview.GameDisplay
import com.tsu.mobilegamelab4.game.surfaceview.Hitbox
import com.tsu.mobilegamelab4.game.surfaceview.Point
import com.tsu.mobilegamelab4.game.surfaceview.graphics.SpriteSheet
import com.tsu.mobilegamelab4.game.surfaceview.map.firstlocation.FirstLocationMap

class Crate(spriteSheet: SpriteSheet, pos: Point) : StaticGameObject(spriteSheet, pos) {

    override var sprite = spriteSheet.getSpriteByIndex(Rect(0, 8, 1, 10)).also {
        it.size = android.graphics.Point(
            it.size.x,
            it.size.y * 2
        )
    }

    override fun draw(canvas: Canvas, display: GameDisplay?) {
        // sprite.draw(canvas, pos.X.toInt(), (pos.Y - sprite.size.y).toInt())
        display?.let {
            displayCoordinates = it.gameToDisplayCoordinates(pos)
            sprite.draw(
                canvas,
                displayCoordinates.X.toInt(),
                (displayCoordinates.Y - sprite.size.y / 2).toInt()
            )
            //hitbox.draw(canvas, display)
        }
    }

    init {
        hitbox = Hitbox(
            this,
            FirstLocationMap.CELL_WIDTH_PIXELS,
            FirstLocationMap.CELL_HEIGHT_PIXELS
        )
    }
}