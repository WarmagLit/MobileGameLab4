package com.tsu.mobilegamelab4.game.surfaceview.gameobjects

import android.graphics.Canvas
import android.graphics.Rect
import com.tsu.mobilegamelab4.game.surfaceview.GameDisplay
import com.tsu.mobilegamelab4.game.surfaceview.Hitbox
import com.tsu.mobilegamelab4.game.surfaceview.Point
import com.tsu.mobilegamelab4.game.surfaceview.gameobjects.entity.player.Player
import com.tsu.mobilegamelab4.game.surfaceview.graphics.SpriteSheet
import com.tsu.mobilegamelab4.game.surfaceview.interfaces.IUsable
import com.tsu.mobilegamelab4.game.surfaceview.items.Keys
import com.tsu.mobilegamelab4.game.surfaceview.map.firstlocation.FirstLocationMap

class Door(
    spriteSheet: SpriteSheet,
    pos: Point,
    private val keys: List<Keys>
) :
    StaticGameObject(spriteSheet, pos),
    IUsable {

    override var sprite = spriteSheet.getSpriteByIndex(Rect(1, 5, 3, 8)).also {
        it.size = android.graphics.Point(it.size.x * 2, it.size.y * 3)
    }

    override fun draw(canvas: Canvas, display: GameDisplay?) {
        // sprite.draw(canvas, pos.X.toInt(), (pos.Y - sprite.size.y).toInt())
        display?.let {
            displayCoordinates = it.gameToDisplayCoordinates(pos)
            sprite.draw(
                canvas,
                displayCoordinates.X.toInt(),
                (displayCoordinates.Y - sprite.size.y/3).toInt()
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
    }

    override fun use(player: Player) {
        var suitableKeyCount = 0
        keys.forEach { doorKey ->
            if (player.keys.find { it.name == doorKey.name } != null) {
                suitableKeyCount++
            }
        }

        if (suitableKeyCount == keys.size) {
            sprite = spriteSheet.getSpriteByIndex(Rect(4, 5, 6, 8)).also {
                it.size = android.graphics.Point(it.size.x * 2, it.size.y * 3)
            }
            hitbox.disable()
        }
    }

    override fun getCenter() = Point(pos.X + sprite.size.x / 2, pos.Y + sprite.size.y * 2 / 3)

    override fun isUsed() = hitbox.isDisabled()
}