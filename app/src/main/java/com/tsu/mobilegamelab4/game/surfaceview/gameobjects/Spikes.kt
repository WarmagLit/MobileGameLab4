package com.tsu.mobilegamelab4.game.surfaceview.gameobjects

import android.graphics.Canvas
import android.graphics.Rect
import com.tsu.mobilegamelab4.game.surfaceview.GameDisplay
import com.tsu.mobilegamelab4.game.surfaceview.Hitbox
import com.tsu.mobilegamelab4.game.surfaceview.Point
import com.tsu.mobilegamelab4.game.surfaceview.gameobjects.entity.player.Player
import com.tsu.mobilegamelab4.game.surfaceview.graphics.FirstLocationSpriteSheet
import com.tsu.mobilegamelab4.game.surfaceview.graphics.SpriteSheet
import com.tsu.mobilegamelab4.game.surfaceview.map.firstlocation.FirstLocationMap

class Spikes(spriteSheet: SpriteSheet, pos: Point) : GameObject(pos) {

    private var spriteList = listOf(
        spriteSheet.getSpriteByIndex(Rect(10, 0, 11, 1)),
        spriteSheet.getSpriteByIndex(Rect(11, 0, 12, 1)),
        spriteSheet.getSpriteByIndex(Rect(12, 0, 13, 1)),
        spriteSheet.getSpriteByIndex(Rect(13, 0, 14, 1))
    )

    private var updatesToNextSprite = 25
    private var afterHitCooldown = 60
    private var currentSpriteIndex = 0
    private var displayCoordinates = Point(0.0, 0.0)
    var player: Player? = null

    override fun draw(canvas: Canvas, display: GameDisplay?) {
        // sprite.draw(canvas, pos.X.toInt(), (pos.Y - sprite.size.y).toInt())
        display?.let {
            displayCoordinates = it.gameToDisplayCoordinates(pos)
            spriteList[currentSpriteIndex].draw(
                canvas,
                displayCoordinates.X.toInt(),
                displayCoordinates.Y.toInt()
            )
            // hitbox.draw(canvas, display)
        }
    }

    override fun update() {
        hitbox.updateCoordinates(displayCoordinates)

        updatesToNextSprite--
        if (updatesToNextSprite == 0) {
            updatesToNextSprite += 25
            currentSpriteIndex = (currentSpriteIndex + 1) % spriteList.size
            when (currentSpriteIndex) {
                0 -> {
                    hitbox.isObstacle = false
                    hitbox.disable()
                }
                2 -> hitbox.enable()
                3 -> hitbox.isObstacle = true
            }
        }

        player?.let {
            if (!hitbox.isDisabled() && hitbox.isCollide(it.hitbox) && afterHitCooldown <= 0) {
                it.receiveDamage(20)
                afterHitCooldown = 60
            }
            afterHitCooldown--
        }
    }

    init {
        hitbox = Hitbox(
            this,
            FirstLocationMap.CELL_WIDTH_PIXELS * 4 / 5,
            FirstLocationMap.CELL_HEIGHT_PIXELS * 4 / 5
        )
        hitbox.offset = android.graphics.Point(
            FirstLocationMap.CELL_WIDTH_PIXELS / 10,
            FirstLocationMap.CELL_HEIGHT_PIXELS / 10
        )
        hitbox.disable()
        hitbox.isObstacle = false
    }

}