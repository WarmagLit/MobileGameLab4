package com.tsu.mobilegamelab4.game.gameobjects

import android.graphics.Canvas
import android.graphics.Rect
import com.tsu.mobilegamelab4.game.GameDisplay
import com.tsu.mobilegamelab4.game.Hitbox
import com.tsu.mobilegamelab4.game.Point
import com.tsu.mobilegamelab4.game.gameobjects.entity.player.Player
import com.tsu.mobilegamelab4.game.graphics.FirstLocationSpriteSheet
import com.tsu.mobilegamelab4.game.interfaces.IUsable
import com.tsu.mobilegamelab4.game.items.Keys
import com.tsu.mobilegamelab4.game.map.firstlocation.FirstLocationMap

class Steps(
    private val spriteSheet: FirstLocationSpriteSheet,
    pos: Point
) : GameObject(pos), IUsable {

    var sprite = spriteSheet.getSpriteByIndex(Rect(0, 3, 2, 5)).also {
        it.size.x *= 2
        it.size.y *= 2
    }
    private var displayCoordinates = Point(0.0, 0.0)
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

    override fun update() {
        hitbox.updateCoordinates(displayCoordinates)
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