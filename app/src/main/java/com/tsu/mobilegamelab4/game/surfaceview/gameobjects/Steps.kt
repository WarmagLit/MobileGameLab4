package com.tsu.mobilegamelab4.game.surfaceview.gameobjects

import android.graphics.Canvas
import android.graphics.Rect
import com.tsu.mobilegamelab4.game.surfaceview.GameDisplay
import com.tsu.mobilegamelab4.game.surfaceview.Hitbox
import com.tsu.mobilegamelab4.game.surfaceview.Point
import com.tsu.mobilegamelab4.game.surfaceview.gameobjects.entity.player.Player
import com.tsu.mobilegamelab4.game.surfaceview.graphics.SpriteSheet
import com.tsu.mobilegamelab4.game.surfaceview.interfaces.IUsable
import com.tsu.mobilegamelab4.game.surfaceview.map.firstlocation.FirstLocationMap

class Steps(
    spriteSheet: SpriteSheet,
    pos: Point
) : StaticGameObject(spriteSheet, pos), IUsable {

    override var sprite = spriteSheet.getSpriteByIndex(Rect(0, 3, 2, 5)).also {
        it.size.x *= 2
        it.size.y *= 2
    }
    var levelCompleted: ()->Unit = {}

    override fun draw(canvas: Canvas, display: GameDisplay?) {
        // sprite.draw(canvas, pos.X.toInt(), (pos.Y - sprite.size.y).toInt())
        display?.let {
            displayCoordinates = it.gameToDisplayCoordinates(pos)
            sprite.draw(
                canvas,
                displayCoordinates.X.toInt(),
                displayCoordinates.Y.toInt()
            )
            //hitbox.draw(canvas, display)
        }
    }

    init {
        hitbox = Hitbox(
            this,
            FirstLocationMap.CELL_WIDTH_PIXELS * 2,
            FirstLocationMap.CELL_HEIGHT_PIXELS * 2
        )
        hitbox.disable()
    }

    override fun use(player: Player) {
        levelCompleted.invoke()
    }

    override fun getCenter() = Point(pos.X + sprite.size.x / 2, pos.Y + sprite.size.y / 2)

    override fun isUsed() = false
}