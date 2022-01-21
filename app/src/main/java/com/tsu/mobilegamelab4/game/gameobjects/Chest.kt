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

class Chest(
    private val spriteSheet: FirstLocationSpriteSheet,
    pos: Point,
    private var key: Keys?
) :
    GameObject(pos),
    IUsable {

    var sprite = spriteSheet.getSpriteByIndex(Rect(1, 8, 2, 9))
    private var displayCoordinates = Point(0.0, 0.0)
    var showDialog: (loot: Keys) -> Unit = {}

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
            FirstLocationMap.CELL_WIDTH_PIXELS,
            FirstLocationMap.CELL_HEIGHT_PIXELS * 4 / 5
        )
        hitbox.offset = android.graphics.Point(0, FirstLocationMap.CELL_HEIGHT_PIXELS / 5)
    }

    override fun use(player: Player) {
        if (key != Keys.EMPTY) {
            sprite = spriteSheet.getSpriteByIndex(Rect(3, 8, 4, 9))
            key?.let {
                player.keys.add(it)
            }
        } else {
            sprite = spriteSheet.getSpriteByIndex(Rect(2, 8, 3, 9))
        }
        showDialog.invoke(key ?: Keys.EMPTY)
        key = null
    }

    override fun getCenter() = Point(pos.X + sprite.size.x / 2, pos.Y + sprite.size.y / 2)

    override fun isUsed() = key == null
}