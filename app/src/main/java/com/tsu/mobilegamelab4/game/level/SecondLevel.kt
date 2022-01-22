package com.tsu.mobilegamelab4.game.level

import android.graphics.Canvas
import com.tsu.mobilegamelab4.game.GameDisplay
import com.tsu.mobilegamelab4.game.Point
import com.tsu.mobilegamelab4.game.gameobjects.*
import com.tsu.mobilegamelab4.game.gameobjects.entity.enemy.Boss.Boss
import com.tsu.mobilegamelab4.game.gameobjects.entity.enemy.Landmine.Landmine
import com.tsu.mobilegamelab4.game.gameobjects.entity.enemy.Masker.Masker
import com.tsu.mobilegamelab4.game.gameobjects.entity.enemy.Wizard.Wizard
import com.tsu.mobilegamelab4.game.gameobjects.entity.player.Player
import com.tsu.mobilegamelab4.game.graphics.*
import com.tsu.mobilegamelab4.game.items.Keys
import com.tsu.mobilegamelab4.game.map.firstlocation.FirstLocationMap
import com.tsu.mobilegamelab4.game.map.secondlocation.SecondLocationMap

class SecondLevel(
    private val enemySpriteSheet: EnemySpriteSheet,
    private val bossSpriteSheet: BossSpriteSheet,
    private val locationSpriteSheet: SecondLocationSpriteSheet,
    private val keySpriteSheet: KeySpriteSheet
) :
    Level(enemySpriteSheet, keySpriteSheet) {

    private val map = SecondLocationMap(locationSpriteSheet)
    private lateinit var player: Player

    init {
        level = 2

        gameObjects.addAll(
            arrayOf(
                Steps(
                    locationSpriteSheet,
                    Point(
                        26.0 * FirstLocationMap.CELL_WIDTH_PIXELS,
                        36.0 * FirstLocationMap.CELL_HEIGHT_PIXELS
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
                23.0 * FirstLocationMap.CELL_WIDTH_PIXELS,
                5.0 * FirstLocationMap.CELL_HEIGHT_PIXELS
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