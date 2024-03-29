package com.tsu.mobilegamelab4.game.surfaceview.gameobjects

import android.graphics.Canvas
import android.graphics.Rect
import com.tsu.mobilegamelab4.game.surfaceview.GameDisplay
import com.tsu.mobilegamelab4.game.surfaceview.Hitbox
import com.tsu.mobilegamelab4.game.surfaceview.Point
import com.tsu.mobilegamelab4.game.surfaceview.gameobjects.entity.player.Player
import com.tsu.mobilegamelab4.game.surfaceview.graphics.FirstLocationSpriteSheet
import com.tsu.mobilegamelab4.game.surfaceview.graphics.SpriteSheet
import com.tsu.mobilegamelab4.game.surfaceview.interfaces.IUsable
import com.tsu.mobilegamelab4.game.surfaceview.items.Keys
import com.tsu.mobilegamelab4.game.surfaceview.map.firstlocation.FirstLocationMap

class Chest(
    spriteSheet: SpriteSheet,
    pos: Point,
    private var key: Keys?
) :
    StaticGameObject(spriteSheet, pos),
    IUsable {

    override var sprite = spriteSheet.getSpriteByIndex(Rect(1, 8, 2, 9))
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