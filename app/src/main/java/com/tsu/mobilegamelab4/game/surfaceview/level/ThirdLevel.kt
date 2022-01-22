package com.tsu.mobilegamelab4.game.surfaceview.level

import android.graphics.Canvas
import com.tsu.mobilegamelab4.game.surfaceview.GameDisplay
import com.tsu.mobilegamelab4.game.surfaceview.Point
import com.tsu.mobilegamelab4.game.surfaceview.gameobjects.GameObject
import com.tsu.mobilegamelab4.game.surfaceview.gameobjects.Spikes
import com.tsu.mobilegamelab4.game.surfaceview.gameobjects.Steps
import com.tsu.mobilegamelab4.game.surfaceview.gameobjects.entity.player.Player
import com.tsu.mobilegamelab4.game.surfaceview.graphics.*
import com.tsu.mobilegamelab4.game.surfaceview.map.firstlocation.FirstLocationMap
import com.tsu.mobilegamelab4.game.surfaceview.map.thirdlocation.ThirdLocationMap

class ThirdLevel(
    private val enemySpriteSheet: EnemySpriteSheet,
    private val bossSpriteSheet: BossSpriteSheet,
    private val locationSpriteSheet: ThirdLocationSpriteSheet,
    private val keySpriteSheet: KeySpriteSheet
) :
    Level(enemySpriteSheet, keySpriteSheet) {

    private val map = ThirdLocationMap(locationSpriteSheet)
    private lateinit var player: Player

    init {
        level = 2

        gameObjects.addAll(
            arrayOf(
                Steps(
                    locationSpriteSheet,
                    Point(
                        29.0 * FirstLocationMap.CELL_WIDTH_PIXELS,
                        3.0 * FirstLocationMap.CELL_HEIGHT_PIXELS
                    )
                )
            )
        )
    }

    override fun recycleBitmaps() {
        enemySpriteSheet.bitmap.recycle()
        bossSpriteSheet.bitmap.recycle()
        locationSpriteSheet.bitmap.recycle()
        locationSpriteSheet.lowerLevelBitmap.recycle()
        locationSpriteSheet.upperLevelBitmap.recycle()
        keySpriteSheet.bitmap.recycle()
    }

    override fun initializePlayer(heroSpriteSheet: HeroSpriteSheet): Player {
        player = Player(
            Point(
                33.0 * FirstLocationMap.CELL_WIDTH_PIXELS,
                16.0 * FirstLocationMap.CELL_HEIGHT_PIXELS
            ),
            heroSpriteSheet,
            map.collisionLayout,
            gameObjects
        )
        gameObjects.add(player)
        addEnemies(player)
        gameObjects.filterIsInstance<Spikes>().forEach { it.player = player }
        return player
    }

    private fun addEnemies(player: Player) {
        //gameObjects.addAll()
    }

    override fun draw(canvas: Canvas, display: GameDisplay?) {
        map.draw(canvas, display)
        gameObjects.sortWith(
            Comparator { go1: GameObject, go2: GameObject ->
                if (go1 is Spikes || go1.pos.Y < go2.pos.Y) {
                    return@Comparator -1
                }
                if (go2 is Spikes || go1.pos.Y > go2.pos.Y) {
                    return@Comparator 1
                }
                return@Comparator 0
            }
        )
        gameObjects.forEach {
            it.draw(canvas, display)
        }
        map.draw(canvas, display)

        //draw keys
        display?.let {
            var xOffset = 200
            for (key in player.keys) {
                val keySprite = keySpriteSheet.getSpriteByRect(key.keyRect)
                keySprite.size =
                    android.graphics.Point(key.keyRect.width() * 4, key.keyRect.height() * 4)
                keySprite.draw(canvas, display.widthPixels - xOffset, 50)
                xOffset += 150
            }
        }
    }

    override fun update() {
        gameObjects = gameObjects.filter {
            !it.toDestroy
        }.toMutableList()

        gameObjects.forEach {
            it.update()
        }
    }

}